package io;

/**
 * Operates with console output
 */
public interface OutputManager {
    /**
     * Static method to print message
     * @param msg
     */
    static void print(Object msg) {
        System.out.println(msg.toString());
    }

    /**
     * Static method to print error notification
     * @param msg
     */
    static void printErr(Object msg) {
        System.out.println("Err: " + msg.toString());
    }
}
