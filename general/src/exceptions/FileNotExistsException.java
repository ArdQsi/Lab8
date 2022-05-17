package exceptions;

public class FileNotExistsException extends FileException {
    public FileNotExistsException() {
        super("cannot find file");
    }
}
