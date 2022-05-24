package client;

import auth.User;
import collection.ProductObservableManager;
import commands.ClientCommandManager;
import connection.*;

import exceptions.*;
import io.OutputManager;
import io.OutputterUI;
import tools.ObservableResourceFactory;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;


import static io.ConsoleOutputter.print;
import static io.ConsoleOutputter.printErr;


public class Client extends Thread implements Closeable {
    private SocketAddress address;
    private DatagramSocket socket;
    private ClientCommandManager commandManager;
    boolean running;
    final int INCREMENT = 4096;
    private User user;
    private User attempt;
    private ProductObservableManager collectionManager;
    private ObservableResourceFactory resourceFactory;
    private OutputManager outputManager;
    private DatagramSocket broadcastSocket;
    private InetSocketAddress host;


    private volatile boolean receivedRequest;
    private volatile boolean authSuccess;
    private boolean connected;

    public boolean isReceivedRequest() {
        return receivedRequest;
    }

    public Client(String addr, int p) throws ConnectionException {
        this.init(addr, p);
    }

    private void init(String addr, int p) throws ConnectionException {
        this.connect(addr, p);
        this.running = true;
        connected = false;
        authSuccess = false;
        collectionManager = new ProductObservableManager();
        this.commandManager = new ClientCommandManager(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setAttemptUser(User a) {
        attempt = a;
    }

    public User getAttemptUser() {
        return attempt;
    }

    public void connect(String addr, int p) throws ConnectionException {
        try {
            this.address = new InetSocketAddress(InetAddress.getByName(addr), p);
        } catch (UnknownHostException var5) {
            throw new InvalidAddressException();
        } catch (IllegalArgumentException var6) {
            throw new InvalidPortException();
        }

        try {
            socket = new DatagramSocket();
            broadcastSocket = new DatagramSocket();
            host = new InetSocketAddress(InetAddress.getByName("localhost"), broadcastSocket.getLocalPort());
        } catch (IOException var4) {
            throw new ConnectionException("cannot open socket");
        }
    }

    public void send(Request request) throws ConnectionException {
        try {
            request.setBroadcastAddress(host);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            DatagramPacket requestPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), address);
            socket.send(requestPacket);
            byteArrayOutputStream.close();
        } catch (IOException var10) {
            throw new ConnectionException("something went wrong while sending request");
        }
    }

    public Response receive() throws ConnectionException, InvalidDataException {
        try{
            socket.setSoTimeout(500);
        } catch (SocketException e) {

        }

        ByteBuffer bytes = ByteBuffer.allocate(4096);
        DatagramPacket receivePacket = new DatagramPacket(bytes.array(), bytes.array().length);
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while receiving response");
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            return (Response) objectInputStream.readObject();
        } catch (ClassCastException | ClassNotFoundException var8) {
            throw new InvalidReceivedDataException();
        } catch (IOException var9) {
            throw new ClosedConnectionException();
        }
    }

    private Response receiveBroadcast() throws ConnectionException, InvalidDataException{

        ByteBuffer bytes = ByteBuffer.allocate(4096);
        DatagramPacket receivePacket = new DatagramPacket(bytes.array(), bytes.array().length);
        try {
            broadcastSocket.receive(receivePacket);
        } catch (IOException e) {
            throw new ConnectionException("something went wrong while receiving response");
        }
        connected=true;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes.array()));
            return (Response) objectInputStream.readObject();
        } catch (ClassNotFoundException | ClassCastException | IOException e) {
            throw new InvalidReceivedDataException();
        }
    }

    public void setAuthSuccess(boolean f) {
        authSuccess =f;
    }

    @Override
    public void run() {
        Request hello = new CommandMsg();
        hello.setStatus(Request.Status.HELLO);
        try {
            send(hello);
            Response response = receive();
            if (response.getStatus()==Response.Status.COLLECTION && response.getCollection()!=null &&
            response.getCollectionOperation() == CollectionOperation.ADD) {
                collectionManager.applyChanges(response);
            }
        } catch (ConnectionException|InvalidDataException e) {
            printErr("cannot load collection from server");
        }
        while (running) {
            try {
                receivedRequest = false;
                Response response = receiveBroadcast();
                String msg = response.getMessage();
                switch (response.getStatus()) {
                    case COLLECTION:
                        collectionManager.applyChanges(response);
                        print("loaded!");
                        break;
                    case BROADCAST:
                        print("caught broadcast!");
                        collectionManager.applyChanges(response);
                        break;
                    case AUTH_SUCCESS:
                        user = attempt;
                        authSuccess = true;
                        break;
                    case EXIT:
                        connected = false;
                        outputManager.error("Server Shut Down");
                        break;
                    case FINE:
                        outputManager.info(msg);
                        break;
                    case ERROR:
                        outputManager.error(msg);
                        break;
                    default:
                        print(msg);
                        receivedRequest = true;
                        break;
                }
            } catch (ConnectionException e) {

            } catch (InvalidDataException e) {

            }
        }
    }

    public void connectionTest() {
        connected = false;
        try {
            send(new CommandMsg().setStatus(Request.Status.CONNECTION_TEST));
            Response response = receive();
            connected = (response.getStatus() == Response.Status.FINE);
        } catch (ConnectionException | InvalidDataException e) {

        }
    }

    public void processAuthentication(String login, String password, boolean register) {
        attempt = new User(login, password);
        CommandMsg msg;
        if (register) {
            msg = new CommandMsg("register").setStatus(Request.Status.DEFAULT).setUser(attempt);
        } else {
            msg = new CommandMsg("login").setStatus(Request.Status.DEFAULT).setUser(attempt);
        }
        try {
            send(msg);
            Response answer = receive();
            connected = true;
            authSuccess = (answer.getStatus() == Response.Status.AUTH_SUCCESS);
            if (authSuccess) {
                user = attempt;
            } else {
                outputManager.error("wrong password");

            }
        } catch (ConnectionException | InvalidDataException e) {
            connected = false;
        } catch (NullPointerException e) {
            outputManager.error("неудачная регистрация");
        }
    }

    public void consoleMode() {
        commandManager.consoleMode();
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isAuthSuccess() {
        return authSuccess;
    }

    public ProductObservableManager getProductManager() {
        return collectionManager;
    }



    public ClientCommandManager getCommandManager() {
        return commandManager;
    }

    public OutputManager getOutputManager() {
        return outputManager;
    }

    public void setOutputManager(OutputManager out) {
        outputManager = out;
    }

    public ObservableResourceFactory getResourceFactory() {
        return resourceFactory;
    }

    public void setResourceFactory(ObservableResourceFactory rf) {
        resourceFactory = rf;
    }

    public void close() {
        try {
            send(new CommandMsg().setStatus(Request.Status.EXIT));
        } catch (ConnectionException e) {

        }
        running = false;
        commandManager.close();
        socket.close();
        broadcastSocket.close();
        currentThread().interrupt();
    }


}

