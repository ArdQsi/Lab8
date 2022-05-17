package connection;

import auth.User;
import data.Product;

import java.io.Serializable;


public class CommandMsg implements Request, Serializable {
    private String commandName;
    private String commandStringArgument;
    private Product product;
    private User user;
    private Request.Status status;

    public CommandMsg(String commandName, String commandStringArgument, Product product) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.product = product;
        this.user = null;
        this.status = Status.DEFAULT;
    }

    public CommandMsg(String commandName, String commandStringArgument, Product product, User user) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.product = product;
        this.user = user;
        this.status = Status.DEFAULT;
    }

    public CommandMsg() {}
    private byte[] msgBytes;

    public CommandMsg(Status status, byte[] msgBytes) {
        this.status = status;
        this.msgBytes = msgBytes;
    }
    String data;
    public CommandMsg(String s) {
        this.data = s;
    }

    private Request request;
    public CommandMsg(Status status, Request request) {
        this.request = request;
        this.status = status;
    }

    public CommandMsg(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }


    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getStringArg() {
        return commandStringArgument;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}