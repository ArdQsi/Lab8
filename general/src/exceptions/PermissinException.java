package exceptions;

public class PermissinException extends AuthException{
    public PermissinException(String user) {
        super("you don't have permission, element was created by " + user);
    }
}
