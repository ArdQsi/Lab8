package utils;

import exceptions.InvalidNumberException;

import java.io.*;

public class Parser {
    public static int parseId(String s) throws InvalidNumberException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
    }
}
