package connection;

import auth.User;
import data.Product;

import java.io.Serializable;

public class Request1 extends CommandMsg implements Request, Serializable {
    CommandMsg commandMsg = new CommandMsg();

    private byte[] msgBytes;

    public Request1(final String msg) {
        //super();
        //this.requestCode = requestCode;
        msgBytes = msg.getBytes();
    }

    public Request1(byte[] msg, boolean isBoolean) {
        this.msgBytes = msg;
        this.isBoolean = isBoolean;
    }

    public Request1() {

    }

    private Request request;
    private boolean isBoolean;

    public Request1(Request request, boolean isBoolean) {
        this.request = request;
        this.isBoolean = isBoolean;
    }

    public boolean getIsBoolean() {
        return isBoolean;
    }

    public Request getRequest() {
        return request;
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

    public void setUser(User user) {
        commandMsg.setUser(user);
    }


    public Status getStatus() {
        return commandMsg.getStatus();
    }


    public void setStatus(Status s) {
        commandMsg.setStatus(s);
    }






}
