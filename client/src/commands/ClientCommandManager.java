package commands;

import client.Client;
import connection.AnswerMsg;
import connection.CommandMsg;
import connection.Request;
import connection.Response;
import exceptions.ConnectionException;
import exceptions.InvalidDataException;

import static io.ConsoleOutputter.print;


public class ClientCommandManager extends CommandManager {
    private final Client client;

    public ClientCommandManager(Client c) {
        client = c;
        addCommand(new ExecuteScriptCommand(this));
        addCommand(new ExitCommand());
    }

    public Client getClient() {
        return client;
    }

    @Override
    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        if (hasCommand(msg)) {
            res = (AnswerMsg) super.runCommand(msg);
            if (res.getStatus() == Response.Status.EXIT) {
                res.info("shutting down");
            }
        } else {
            try {
                if (client.getUser() != null && msg.getUser() == null) msg.setUser(client.getUser());
                client.send((CommandMsg) msg);
                res = (AnswerMsg) client.receive();
                if (res.getStatus() == Response.Status.AUTH_SUCCESS) {
                    client.setUser(msg.getUser());
                }
            } catch (InvalidDataException | ConnectionException e) {
                res.error(e.getMessage());
            }
        }
        print(res);
        return res;
    }

}

