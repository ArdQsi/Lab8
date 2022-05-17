package io;

import exceptions.FileException;
import file.FileManager;

import java.util.Scanner;

/**
 * Operates input
 */
public class FileInputManager extends InputManagerImplements{
    public FileInputManager(String path1) throws FileException {
        super(new Scanner(new FileManager(path1).read()));
        getScanner().useDelimiter("\n");
    }
}
