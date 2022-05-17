package Commands;

import auth.User;
import auth.UserManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.Response;
import exceptions.DatabaseException;

import javax.xml.crypto.Data;

public class RegisterCommand extends CommandImplements {
    private final UserManager userManager;

    public RegisterCommand(UserManager userManager) {
        super("register", CommandType.AUTH);
        this.userManager = userManager;
    }

    @Override
    public Response run() throws DatabaseException {
        User user = getArgument().getUser();
        if (user != null && user.getLogin() != null && user.getPassword() != null) {
            if (userManager.isPresent(user.getLogin())) {
                throw new DatabaseException("user " + user.getLogin() + " already exist");
            }
            userManager.add(user);
            return new AnswerMsg().info("user " + user.getLogin() + " successfully registered").setStatus(Response.Status.AUTH_SUCCESS);
        }
        throw new DatabaseException("something went wrong with user");
    }
}
