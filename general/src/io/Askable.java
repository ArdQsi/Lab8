package io;

import exceptions.InvalidDataException;

/**
 * user input callback
 */
public interface Askable<T> {
    T ask() throws InvalidDataException;
}