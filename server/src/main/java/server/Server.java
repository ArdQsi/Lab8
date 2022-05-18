package server;

import Commands.ServerCommandManager;
import auth.User;
import auth.UserManager;
import collection.CollectionManager;
import commands.CommandType;
import connection.*;
import data.Product;
import database.DatabaseHandler;
import database.ProductDatabaseManager;
import database.UserDatabaseManager;
import exceptions.*;
import log.Log;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.ClosedChannelException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Server extends Thread {
    private CollectionManager<Product> collectionManager;
    private ServerCommandManager commandManager;
    private DatabaseHandler databaseHandler;
    private UserManager userManager;

    private int port;
    private DatagramChannel channel;

    private Queue<Map.Entry<InetSocketAddress, Request>> requestQueue;
    private Queue<Map.Entry<InetSocketAddress, Response>> responseQueue;
    private Set<InetSocketAddress> activeClients;

    private volatile boolean running;
    private Selector selector;
    private User hostUser;

    public Server(int port, Properties properties) throws ConnectionException, DatabaseException {
        init(port, properties);
    }

    private void init(int port, Properties properties) throws ConnectionException, DatabaseException {
        running = true;
        this.port = port;
        hostUser = null;

        requestQueue = new ConcurrentLinkedQueue<>();
        responseQueue = new ConcurrentLinkedQueue<>();
        activeClients = ConcurrentHashMap.newKeySet();

        databaseHandler = new DatabaseHandler(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
        userManager = new UserDatabaseManager(databaseHandler);
        collectionManager = new ProductDatabaseManager(databaseHandler, userManager);
        commandManager = new ServerCommandManager(this);

        try {
            collectionManager.deserializeCollection();
        } catch (CollectionException e) {
            Log.logger.error(e.getMessage());
        }
        host(port);
        Log.logger.info("starting server");
    }

    public void host(int p) throws ConnectionException {
        try {
            if (channel != null && channel.isOpen()) channel.close();
            this.port = p;
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(port));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        } catch (AlreadyBoundException e) {
            throw new PortAlreadyInUseException();
        } catch (IllegalArgumentException e) {
            throw new InvalidPortException();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during creating a server");
        }
    }

    public void receive() throws ConnectionException, InvalidDataException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        Request request = null;
        InetSocketAddress clientAddress = null;
        try {
            clientAddress = (InetSocketAddress) channel.receive(buffer);
            if (clientAddress == null) return;
            if (!activeClients.contains(clientAddress)) activeClients.add(clientAddress);
            Log.logger.info("received request from " + clientAddress.toString());
        } catch (ClosedChannelException e) {
            throw new ClosedConnectionException();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during receiving request");
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            request = (Request) objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new InvalidReceivedDataException();
        }
        requestQueue.offer(new AbstractMap.SimpleEntry<>(clientAddress, request));
    }

    public void broadcast(Response response) {
        Log.logger.info("broadcasting changes");
        for (InetSocketAddress client : activeClients) {
            responseQueue.offer(new AbstractMap.SimpleEntry<>(client, response));
        }
    }

    public void send(InetSocketAddress clientAddress, Response response) throws ConnectionException {
        if (clientAddress == null) throw new InvalidAddressException("not found client address");
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            this.channel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), clientAddress);
            Log.logger.info("sent response to " + clientAddress);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            throw new ConnectionException("something went wrong during sending response");
        }
    }

    private void handlerRequest(InetSocketAddress address, Request request) {
        AnswerMsg answerMsg = new AnswerMsg();
        try {
            if (request.getStatus() == Request.Status.EXIT) {
                activeClients.remove(address);
                Log.logger.info("client " + address.toString() + " shut down");
                return;
            }
            if (request.getStatus() == Request.Status.HELLO) {
                answerMsg = new AnswerMsg().setStatus(Response.Status.COLLECTION).setCollectionOperation(CollectionOperation.ADD).setCollection(collectionManager.getCollection());
                activeClients.add(address);
                responseQueue.offer(new AbstractMap.SimpleEntry<>(address, answerMsg));
                return;
            }
            if (request.getStatus() == Request.Status.CONNECTION_TEST) {
                answerMsg.setStatus(Response.Status.FINE);
                responseQueue.offer(new AbstractMap.SimpleEntry<>(address, answerMsg));
                return;
            }
            Product product = request.getProduct();
            if (product != null) {
                product.setCreationDate(LocalDate.now());
            }
            request.setStatus(Request.Status.RECEIVED_BY_SERVER);

            if (commandManager.getCommand(request).getType() == CommandType.SERVER_ONLY) {
                throw new ServerOnlyCommandException();
            }
            answerMsg = (AnswerMsg) commandManager.runCommand(request);
            if (answerMsg.getStatus() == Response.Status.EXIT) {
                close();
            }
        } catch (CommandException e) {
            answerMsg.error(e.getMessage());
            Log.logger.error(e.getMessage());
        }
        System.out.println(commandManager.getCommand(request).getOperation().toString());
        if (answerMsg.getCollectionOperation() != CollectionOperation.NONE && answerMsg.getStatus()==answerMsg.getStatus().FINE) {
            answerMsg.setStatus(Response.Status.BROADCAST);
            broadcast(answerMsg);
        } else {
            responseQueue.offer(new AbstractMap.SimpleEntry<>(address, answerMsg));
        }
    }

    public void run() {
        while (running) {
            try {
                selector.select();
            } catch (IOException e) {
                continue;
            }
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isReadable()) {
                    ReentrantLock reentrantLock = new ReentrantLock();
                    new ForkJoinPool().execute(new Receiver(reentrantLock));
                    continue;
                }
                if (key.isWritable() && responseQueue.size() > 0) {
                    ReentrantLock reentrantLock = new ReentrantLock();
                    new ForkJoinPool().execute(new Sender(responseQueue.poll(), reentrantLock));
                }
            }
            if (requestQueue.size() > 0) {
                ReentrantLock reentrantLock = new ReentrantLock();
                new Thread(new RequestHandler(requestQueue.poll(), reentrantLock)).start();
            }
        }
    }

    public void consoleMode() {
        commandManager.consoleMode();
    }

    public void close() {
        try {
            running = false;
            databaseHandler.closeConnection();
            channel.close();
        } catch (IOException e) {
            Log.logger.error("cannot close channel");
        }
    }

    public CollectionManager<Product> getCollectionManager() {
        return collectionManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public User getHostUser() {
        return hostUser;
    }

    public void setHostUser(User user) {
        this.hostUser = user;
    }

    private class Receiver extends RecursiveAction {
        private final ReentrantLock reentrantLock;

        public Receiver(ReentrantLock reentrantLock) {
            this.reentrantLock = reentrantLock;
        }

        @Override
        protected void compute() {
            reentrantLock.lock();
            try {
                receive();
            } catch (ConnectionException | InvalidDataException e) {
                Log.logger.error(e.getMessage());
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    private class Sender extends RecursiveAction {
        private final Response response;
        private final InetSocketAddress address;
        private final ReentrantLock reentrantLock;

        public Sender(Map.Entry<InetSocketAddress, Response> responseEntry, ReentrantLock reentrantLock) {
            this.response = responseEntry.getValue();
            this.address = responseEntry.getKey();
            this.reentrantLock = reentrantLock;
        }

        @Override
        protected void compute() {
            reentrantLock.lock();
            try {
                send(address, response);
            } catch (ConnectionException e) {
                Log.logger.error(e.getMessage());
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    private class RequestHandler implements Runnable {
        private final Request request;
        private final InetSocketAddress address;
        private final ReentrantLock reentrantLock;

        public RequestHandler(Map.Entry<InetSocketAddress, Request> requestEntry, ReentrantLock reentrantLock) {
            this.request = requestEntry.getValue();
            this.address = requestEntry.getKey();
            this.reentrantLock = reentrantLock;
        }

        @Override
        public void run() {
            reentrantLock.lock();
            try {
                handlerRequest(address, request);
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
