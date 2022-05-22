package commands;

import client.Client;
import connection.*;
import exceptions.ConnectionException;
import exceptions.FileException;
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

    /*@Override
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
                client.send(msg);
                //res = (AnswerMsg) client.receive();
//                if (res.getStatus() == Response.Status.AUTH_SUCCESS) {
//                    client.setUser(msg.getUser());
//                }
            } catch (ConnectionException e) {
                res.error(e.getMessage());
            }
        }
        print(res);
        return res;
    }*/

    @Override
    public AnswerMsg runCommandUnsafe(Request msg) throws InvalidDataException, ConnectionException, FileException {
        AnswerMsg res = new AnswerMsg();
        if (hasCommand(msg)) {
            res = (AnswerMsg) super.runCommandUnsafe(msg);
            if(res.getStatus() == Response.Status.EXIT){
                res.info("shutting down...");
            }
        } else {
            if(client.getUser()!=null && msg.getUser()==null) msg.setUser(client.getUser());
            else client.setAttemptUser(msg.getUser());
            try {
                client.send(msg);
                try {
                    res = (AnswerMsg) client.receive();
                } catch (InvalidDataException e) {
                    throw new ConnectionException("Connection exception");
                }
            }catch (ConnectionException e){
                res.error(e.getMessage());
            }

            switch (res.getStatus()){
                case FINE:
                    client.getOutputManager().info(res.getMessage());
                    break;
                case ERROR:
                    client.getOutputManager().error(res.getMessage());
                    break;
                case AUTH_SUCCESS:
                    client.setUser(client.getAttemptUser());
                    client.setAuthSuccess(true);
                    break;
            }
            if(res.getStatus()== Response.Status.COLLECTION&&res.getCollectionOperation()!= CollectionOperation.NONE&&res.getCollection()!=null) {
                client.getProductManager().clear();
                client.getProductManager().applyChanges(res);
            }
            else if (res.getCollectionOperation()!=CollectionOperation.NONE&&res.getCollection()!=null){
                client.getProductManager().applyChanges(res);
            }
        }
        print(res.getMessage());
        return res;
    }

}

