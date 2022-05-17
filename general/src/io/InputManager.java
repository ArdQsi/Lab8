package io;

import connection.CommandMsg;
import data.Coordinates;
import data.Person;
import data.Product;
import data.UnitOfMeasure;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public interface InputManager {
    /**
     * reads name from input
     * @return
     * @throws EmptyStringException
     * @throws StringException
     */
    public String readName() throws EmptyStringException,StringException;

    /**
     * reads namePerson from input
     * @return
     * @throws EmptyStringException
     * @throws StringException
     */
    public String readNamePerson() throws EmptyStringException, StringException;

    /**
     * reads x from input
     * @return
     * @throws InvalidNumberException
     */
    public long readX() throws InvalidNumberException;

    /**
     * reads y from input
     * @return
     * @throws InvalidNumberException
     */
    public Integer readY() throws InvalidNumberException;

    /**
     * reads coordinates from input
     * @return
     * @throws InvalidNumberException
     */
    public Coordinates readCoordinates() throws InvalidNumberException;

    /**
     * reads price from input
     * @return
     * @throws InvalidNumberException
     */
    public Integer readPrice() throws InvalidNumberException;

    /**
     * reads manufactureCost from input
     * @return
     * @throws InvalidNumberException
     */
    public float readManufactureCost() throws InvalidNumberException;

    /**
     * reads unitOfMeasure from input
     * @return
     * @throws InvalidEnumException
     */
    public UnitOfMeasure readUnitOfMeasure() throws InvalidEnumException;

    /**
     * reads birthday from input
     * @return
     * @throws InvalidDataException
     */
    public LocalDateTime readBirthday() throws InvalidDataException;

    /**
     * reads weight from input
     * @return
     * @throws InvalidNumberException
     */
    public float readWeight() throws InvalidNumberException;

    /**
     * reads passportId from input
     * @return
     * @throws InvalidDataException
     */
    public String readPassportId() throws InvalidDataException;

    /**
     * reads owner from input
     * @return
     * @throws InvalidDataException
     */
    public Person readOwner() throws InvalidDataException;

    /**
     * reads product from input
     * @return
     * @throws InvalidDataException
     */
    public Product readProduct() throws InvalidDataException;

    /**
     * reads command and argument pair from input
     * @return
     */
    public CommandMsg readCommand();

    /**
     * gets input scanner
     * @return
     */
    public Scanner getScanner();
}
