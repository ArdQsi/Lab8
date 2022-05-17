package io;

import auth.User;
import data.Coordinates;
import data.Person;
import data.Product;
import data.UnitOfMeasure;

import java.time.LocalDateTime;
import java.util.Scanner;

import static io.ConsoleOutputter.print;


/**
 * ConsoleInputManager class
 */
public class ConsoleInputManager extends InputManagerImplements{

    public ConsoleInputManager() {
        super(new Scanner(System.in));
        getScanner().useDelimiter("\n");
    }

    @Override
    public String readName(){
        return new Question<String>("Введите название продукта: ", super::readName).getAnswer();
    }

    @Override
    public long readX() {
        return new Question<Long>("Введите х: ", super::readX).getAnswer();
    }

    @Override
    public Integer readY() {
        return new Question<Integer>("Введите y: ", super::readY).getAnswer();
    }

    @Override
    public Coordinates readCoordinates(){
        print("Введите координаты: ");
        long x = readX();
        Integer y = readY();
        Coordinates coordinates = new Coordinates(x,y);
        return coordinates;
    }

    @Override
    public Integer readPrice() {
        return new Question<Integer>("Введите цену за продукт: ", super::readPrice).getAnswer();
    }

    @Override
    public float readManufactureCost(){
        return new Question<Float>("Введите стоимость производства: ", super::readManufactureCost).getAnswer();
    }

    @Override
    public UnitOfMeasure readUnitOfMeasure() {
        return new Question<UnitOfMeasure>("Введите единицу измерения(KILOGRAMS, METERS, SQUARE_METERS, MILLILITERS): ",
                super::readUnitOfMeasure).getAnswer();
    }

    @Override
    public String readNamePerson() {
        return new Question<String>("Введите имя покупателя: ", super::readNamePerson).getAnswer();
    }

    @Override
    public LocalDateTime readBirthday() {
        return new Question<LocalDateTime>("Введите дату рождения: ", super::readBirthday).getAnswer();
    }

    @Override
    public float readWeight() {
        return new Question<Float>("Введите массу: ", super::readWeight).getAnswer();
    }

    @Override
    public String readPassportId() {
        return new Question<String>("Введите паспорт: ", super::readPassportId).getAnswer();
    }

    @Override
    public Person readOwner() {
        print("Введите данные о покупателя");
        String namePerson = readNamePerson();
        LocalDateTime birthday = readBirthday();
        float weight = readWeight();
        String passportID = readPassportId();
        return new Person(namePerson, birthday, weight, passportID);
    }

    @Override
    public Product readProduct() {
        String name = readName();
        Coordinates coords = readCoordinates();
        Integer price = readPrice();
        float manufactureCost = readManufactureCost();
        UnitOfMeasure unitOfMeasure = readUnitOfMeasure();
        Person owner = readOwner();
        return new Product(name,coords,price,manufactureCost,unitOfMeasure,owner);
    }

    @Override
    public String readLogin() {
        return new Question<String>("Введите логин:", super::readLogin).getAnswer();
    }

    @Override
    public String readPassword() {
        return new Question<String>("Введите пароль:", super::readPassword).getAnswer();
    }

    @Override
    public User readUser() {
        String login = readLogin();
        String password = readPassword();
        return new User(login,password);
    }
}
