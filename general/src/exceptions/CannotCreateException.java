package exceptions;

public class CannotCreateException extends FileException{
    public CannotCreateException() {
        super("cannot creat file");
    }
}
