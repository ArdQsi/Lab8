package exceptions;

public class NoSuchIdException extends CollectionException {
    public NoSuchIdException(long id) {
        super("element #" + id + " not found");
    }
}
