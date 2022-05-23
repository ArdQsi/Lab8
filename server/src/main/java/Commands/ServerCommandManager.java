package Commands;

import auth.User;
import auth.UserManager;
import collection.ProductManager;
import commands.Command;
import commands.CommandManager;
import commands.CommandType;
import connection.AnswerMsg;
import connection.Request;
import connection.Response;
import data.Product;
import exceptions.AuthException;
import exceptions.CommandException;
import exceptions.ConnectionException;
import log.Log;
import server.Server;

public class ServerCommandManager extends CommandManager {
    private Server server;
    private final UserManager userManager;

    public ServerCommandManager(Server serv) {
        server = serv;
        ProductManager collectionManager = server.getCollectionManager();
        userManager = server.getUserManager();
        addCommand(new AddCommand(collectionManager));
        addCommand(new HelpCommand());
        addCommand(new LoginCommand(userManager));
        addCommand(new RegisterCommand(userManager));
        addCommand(new ShowCommand(collectionManager));
        addCommand(new InfoCommand(collectionManager));
        addCommand(new RemoveByIdCommand(collectionManager));
        addCommand(new UpdateCommand(collectionManager));
        addCommand(new ClearCommand(collectionManager));
        addCommand(new AddIfMinCommand(collectionManager));
        addCommand(new PrintUniqueOwnerCommand(collectionManager));
        addCommand(new FilterLessThanManufactureCostCommand(collectionManager));
        addCommand(new RemoveLowerCommand(collectionManager));
        addCommand(new RemoveGreaterCommand(collectionManager));
        addCommand(new ExecuteScriptCommand(this));
    }

    public Server getServer() {
        return server;
    }

    @Override
    public Response runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        User user = msg.getUser();
        String cmdName = msg.getCommandName();
        boolean isGeneratedByServer = (msg.getStatus() != Request.Status.RECEIVED_BY_SERVER);
        try {
            Command cmd = getCommand(msg);
            // разрешить выполнять специальные команды без авторизации
            if (cmd.getType() != CommandType.AUTH && cmd.getType() != CommandType.SPECIAL) {
                if (isGeneratedByServer) {
                    user = server.getHostUser();
                    msg.setUser(user);
                }
                if (user == null) throw new AuthException();
                if (!userManager.isValid(user)) throw new AuthException();

                Product product = msg.getProduct();
                if (product != null) product.setUser(user);
            }
            res = (AnswerMsg) super.runCommand(msg);
        } catch (ConnectionException | CommandException e) {
            res.error(e.getMessage());
        }
        String message = "";

        if (user != null) message += " " +user.getLogin() + " ";
        if (cmdName != null) message += " " +cmdName;

        if (res.getMessage().contains("\n")) message+= "\n";
        switch (res.getStatus()) {
            case EXIT:
                Log.logger.fatal(message + "shutting down");
                server.close();
                break;
            case ERROR:
                Log.logger.error(message+ " " + res.getMessage());
                break;
            case AUTH_SUCCESS:
                if (isGeneratedByServer) server.setHostUser(user);
                Log.logger.info(message + res.getMessage());
                break;
            default:
                Log.logger.info(message + " " + res.getMessage());
                break;
        }
        return res;
    }
}
