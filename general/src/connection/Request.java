package connection;

import auth.User;
import data.Product;

import java.io.Serializable;

public interface Request extends Serializable {
    String getStringArg();

    Product getProduct();

    String getCommandName();

    User getUser();

    Request setUser(User user);

    Status getStatus();

    Request setStatus(Status s);

    enum Status {
        DEFAULT,
        CONNECTION_TEST,
        EXIT,
        HELLO,
        SENT_FROM_CLIENT,
        RECEIVED_BY_SERVER
    }
}