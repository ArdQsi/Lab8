package client;

import auth.User;
import collection.ProductObservableManager;
import commands.ClientCommandManager;
import connection.*;

import exceptions.*;
import io.OutputManager;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Observable;


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

    private OutputManager outputManager;
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
        } catch (IOException var4) {
            throw new ConnectionException("cannot open socket");
        }
    }

    public void send(Request request) throws ConnectionException {
        try {
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

    @Override
    public void run() {
        Request hello = new CommandMsg();
        hello.setStatus(Request.Status.HELLO);
        try {
            send(hello);
        } catch (ConnectionException e) {
            printErr("cannot load collection from server");
        }
        while (running) {
            try {
                receivedRequest = false;
                Response response = receive();

                switch (response.getStatus()) {
                    case COLLECTION:
                        collectionManager.applyChanges(response);
                        connected = true;
                        print("loaded!");
                        break;
                    case BROADCAST:
                        print("broadcast!");
                        collectionManager.applyChanges(response);
                        break;
                    case AUTH_SUCCESS:
                        user = attempt;
                        authSuccess = true;
                        break;
                    case ERROR:
                        outputManager.error(response.getMessage());
                    default:
                        print(response.getMessage());
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
        } catch (ConnectionException|InvalidDataException e) {

        }
    }

    public void processAuthentication(String login, String password, boolean register) {
        attempt = new User(login,password);
        CommandMsg msg = new CommandMsg();
        if (register) {
            msg = new CommandMsg("register").setStatus(Request.Status.DEFAULT).setUser(attempt);
        } else  {
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
        } catch (ConnectionException| InvalidDataException e) {
            connected = false;
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
        return  collectionManager;
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


    public void close() {
        try{
            send(new CommandMsg().setStatus(Request.Status.EXIT));
        } catch (ConnectionException e) {

        }
        running = false;
        commandManager.close();
        socket.close();
    }
}

