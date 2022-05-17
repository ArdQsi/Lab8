package data;

import auth.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Product implements Collectionable, Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer price; //Поле не может быть null, Значение поля должно быть больше 0
    private float manufactureCost;
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле может быть null
    private String userLogin;

    public Product(String name, Coordinates coordinates, Integer price, float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
    }

    public Product() {
    }

    public static class SortingComparator implements Comparator<Product> {
        public int compare(Product p1, Product p2) {
            int result = Double.compare(p1.getDataCollection().length, p2.getDataCollection().length);
            return result;
        }
    }

    /**
     * Method to output unique owners
     *
     * @return String
     */
    public String getUniqueOwner() {
        String s = "";
        s += owner.getPersonName() + ", ";
        s += owner.getBirthday() + ", ";
        s += owner.getWeight() + ", ";
        s += owner.getPassportID();
        return s;
    }

    public Person getOwner() {
        return owner;
    }

    public String[] getDataCollection() {
        String[] THISPRODUCT = {
                Long.toString(getId()), getName(), Long.toString(coordinates.getX()),
                Integer.toString(coordinates.getY()), getCreationDate().toString(), Integer.toString(getPrice()),
                Float.toString(getManufactureCost()), getUnitOfMeasure().toString(), owner.getPersonName(), owner.getBirthday().toString(),
                Float.toString(owner.getWeight()), owner.getPassportID()};
        return THISPRODUCT;
    }

    /**
     * Method to get id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Method to set id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method to get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get price
     *
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Method to get creationDate
     *
     * @return creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String login) {
        this.userLogin = login;
    }

    public void setUser(User user) {
        this.userLogin = user.getLogin();
    }

    /**
     * Method to get manufactureCost
     *
     * @return manufactureCost
     */
    public float getManufactureCost() {
        return manufactureCost;
    }

    /**
     * Method to get unitOfMeasure
     *
     * @return unitOfMeasure
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setManufactureCost(float manufactureCost) {
        this.manufactureCost = manufactureCost;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Get all data of product in string
     *
     * @return String with all fields of product
     */
    @Override
    public String toString() {
        String s = "";
        s += "{\n";
        s += "id : \"" + Long.toString(id) + "\",\n";
        s += "name : \"" + name + "\",\n";
        s += "coordinates : " + coordinates.toString() + ",\n";
        s += "creationDate : \"" + creationDate + "\",\n";
        s += "price : \"" + Integer.toString(price) + "\",\n";
        s += "manufactureCost : \"" + Float.toString(manufactureCost) + "\",\n";
        s += "unitOfMeasure : \"" + unitOfMeasure.toString() + "\",\n";
        if (owner != null) s += "owner : " + owner.toString() + ",\n";
        if (userLogin != null) s += "userLogin : \"" + userLogin + "\"\n";
        s += "}";
        return s;
    }

    public int compareTo(Collectionable productObj) {
        int res = Long.compare(this.price, productObj.getPrice());
        return res;
    }

    public boolean validate() {
        return (coordinates != null && coordinates.validate() && owner == null || (owner.validate() && owner != null) &&
                price > 0 && id > 0 && creationDate != null && name != null && !name.equals(""));
    }

}
