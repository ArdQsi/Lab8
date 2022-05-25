package connection;

import auth.User;
import data.Product;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class RequestWrapper extends CommandMsg implements Request, Serializable {
    CommandMsg commandMsg = new CommandMsg();
    InetSocketAddress address;

    private byte[] msgBytes;

    public RequestWrapper(final String msg) {
        //super();
        //this.requestCode = requestCode;
        msgBytes = msg.getBytes();
    }

    public RequestWrapper(byte[] msg, boolean isBoolean) {
        this.msgBytes = msg;
        this.isBoolean = isBoolean;
    }

    public RequestWrapper() {

    }

    private Request request;
    private boolean isBoolean;

    public RequestWrapper(Request request, boolean isBoolean) {
        this.request = request;
        this.isBoolean = isBoolean;
    }

    public boolean getIsBoolean() {
        return isBoolean;
    }

    public Request getRequest() {
        return request;
    }
    public InetSocketAddress getBroadcastAddress() {
        return address;
    }

    public CommandMsg setBroadcastAddress(InetSocketAddress address) {
        this.address = address;
        return this;
    }







    public String getStringArg() {
        return commandMsg.getStringArg();
    }


    public Product getProduct() {
        return commandMsg.getProduct();
    }


    public String getCommandName() {
        return commandMsg.getCommandName();
    }

    public User getUser() {
        return commandMsg.getUser();
    }

    public CommandMsg setUser(User user) {
        commandMsg.setUser(user);
        return null;
    }


    public Status getStatus() {
        return commandMsg.getStatus();
    }


    public CommandMsg setStatus(Status s) {
        commandMsg.setStatus(s);
        return null;
    }

}
