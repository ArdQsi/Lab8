package exceptions;

/**
 * thrown when not enough permission to access the file
 */
public class FileWrongPermissionException extends FileException {
    public FileWrongPermissionException(String s){
        super(s);
    }
}