package connection;

import auth.User;
import data.Product;

import java.io.Serializable;
import java.net.InetSocketAddress;


public class CommandMsg implements Request,Serializable {
    private String commandName;
    private String commandStringArgument;
    private Product product;
    private User user;
    private Request.Status status;
    private InetSocketAddress address;

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

    public CommandMsg() {
        commandName = null;
        commandStringArgument =null;
        product = null;
        status = Status.DEFAULT;
    }

    public CommandMsg(String s) {
        commandName = s;
        commandStringArgument = null;
        product = null;
        status = Status.DEFAULT;
    }

    public CommandMsg setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public CommandMsg setUser(User user) {
        this.user = user;
        return this;
    }

    public CommandMsg setProduct(Product p) {
        product = p;
        return this;
    }

    public CommandMsg setArgument(String s) {
        commandStringArgument = s;
        return this;
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

    public InetSocketAddress getBroadcastAddress() {
        return address;
    }

    public CommandMsg setBroadcastAddress(InetSocketAddress address) {
        this.address = address;
        return this;
    }
}