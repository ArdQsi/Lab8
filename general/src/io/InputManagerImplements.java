package io;

import auth.User;
import connection.CommandMsg;
import data.Coordinates;
import data.Person;
import data.Product;
import data.UnitOfMeasure;
import exceptions.*;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.DateConvertor.parseLocalDate;

/**
 * basic implementation of InputManager
 */
public abstract class InputManagerImplements implements InputManager {
    private Scanner scanner;

    public InputManagerImplements(Scanner sc) {
        this.scanner = sc;
        this.scanner.useDelimiter("\n");
    }


    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner sc) {
        scanner = sc;
    }

    public String readName() throws EmptyStringException, StringException {
        String s = scanner.nextLine().trim();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        if (s.equals("")) {
            throw new EmptyStringException();
        }
        if (matcher.find()) throw new StringException();
        return s;
    }

    public String readNamePerson() throws EmptyStringException, StringException {
        String s = scanner.nextLine().trim();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(s);
        if (s.equals("")) {
            throw new EmptyStringException();
        }
        if (matcher.find()) throw new StringException();
        return s;
    }

    public long readX() throws InvalidNumberException {
        Long x;
        try {
            x = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (x > 658) throw new InvalidNumberException("must be less than 658");
        return x;
    }

    public Integer readY() throws InvalidNumberException {
        Integer y;
        try {
            y = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (y > 211) throw new InvalidNumberException("must be less than 211");
        return y;
    }

    public Coordinates readCoordinates() throws InvalidNumberException {
        long x = readX();
        Integer y = readY();
        Coordinates coord = new Coordinates(x, y);
        return coord;
    }

    public Integer readPrice() throws InvalidNumberException {
        Integer s;
        try {
            s = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (s <= 0) throw new InvalidNumberException("must be greater than 0");
        return s;
    }

    public LocalDateTime readBirthday() throws InvalidFormatDateException {
        String buf = scanner.nextLine().trim();
        String s = "";
        if (buf.equals("")) {
            return parseLocalDate(s);
        } else {
            return parseLocalDate(buf);
        }
    }

    public float readManufactureCost() throws InvalidNumberException {
        float cost;
        try {
            cost = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        return cost;
    }

    public UnitOfMeasure readUnitOfMeasure() throws InvalidEnumException {
        String s = scanner.nextLine().trim();
        try {
            return UnitOfMeasure.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException();
        }
    }

    public float readWeight() throws InvalidNumberException {
        float weight;
        try {
            weight = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }
        if (weight < 0) throw new InvalidNumberException("must be greater than 0");
        return weight;
    }

    public String readPassportId() throws InvalidDataException {
        String s = scanner.nextLine().trim();
        if (s.length() < 7) {
            throw new InvalidDataException("string length must be greater than 7");
        }
        return s;
    }

    public Person readOwner() throws InvalidDataException {
        String namePerson = readNamePerson();
        LocalDateTime birthday = readBirthday();
        float weight = readWeight();
        String passportID = readPassportId();
        return new Person(namePerson, birthday, weight, passportID);
    }

    public Product readProduct() throws InvalidDataException {
        Product product = null;
        String name = readName();
        Coordinates coords = readCoordinates();
        Integer price = readPrice();
        float manufactureCost = readManufactureCost();
        UnitOfMeasure unitOfMeasure = readUnitOfMeasure();
        Person owner = readOwner();
        product = new Product(name, coords, price, manufactureCost, unitOfMeasure, owner);

        return product;
    }
    public String readPassword() throws InvalidDataException {
        System.out.println("Введите пароль");
        String s = scanner.nextLine();
        if (s.equals("")) throw new EmptyStringException();
        return s;
    }

    public String readLogin() throws InvalidDataException{
        System.out.println("Введите логин");
        String s = scanner.nextLine();
        if (s.equals("")) throw new EmptyStringException();
        return s;
    }

    public User readUser() throws InvalidDataException {
        return new User(readLogin(), readPassword());
    }

    public CommandMsg readCommand() {
        String cmd = scanner.nextLine();
        String arg = null;
        Product product = null;
        User user = null;
        if (cmd.contains(" ")) { //if command has argument
            String[] arr = cmd.split(" ", 2);
            cmd = arr[0];
            arg = arr[1];
        }
        if (cmd.equals("add") || cmd.equals("add_if_min") || cmd.equals("add_if_max") || cmd.equals("update")) {
            try {
                product = readProduct();
            } catch (InvalidDataException e) {
            }
        } else if (cmd.equals("login") || cmd.equals("register")) {
            try {
                user = readUser();
            } catch (InvalidDataException e) {

            }
            return new CommandMsg(cmd, null, null, user);
        }
        return new CommandMsg(cmd, arg, product);
    }
}

