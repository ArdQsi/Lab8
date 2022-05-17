package exceptions;

/**
 * thrown when the script calls itself
 */
public class RecursiveScriptException extends CommandException{
    public RecursiveScriptException(){
        super("recursive script execute attempt");
    }
}