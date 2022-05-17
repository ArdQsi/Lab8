package Commands;

import auth.User;
import auth.UserManager;
import commands.CommandImplements;
import commands.CommandType;
import connection.AnswerMsg;
import connection.Response;
import exceptions.AuthException;

public class LoginCommand extends CommandImplements {
    private UserManager userManager;

    public LoginCommand(UserManager userManager) {
        super("login", CommandType.AUTH);
        this.userManager = userManager;
    }

    @Override
    public Response run() throws AuthException {
        User user = getArgument().getUser();
        if (user != null && user.getLogin() != null && user.getPassword() != null) {
            if (userManager.isValid(user)) {
                return new AnswerMsg().info("login successful").setStatus(Response.Status.AUTH_SUCCESS);
            }
        }
        throw new AuthException("login or password is incorrect");
    }
}
