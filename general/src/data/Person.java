package data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Person implements Validateable, Serializable {
    private String personName; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private float weight; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 7, Поле не может быть null

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    /**
     * New person
     * @param personName
     * @param birthday
     * @param weight
     * @param passportID
     */
    public Person(String personName, LocalDateTime birthday, float weight, String passportID) {
        this.personName = personName;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
    }

    public Person(){}
    /**
     * Method to get personName
     * @return personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * Method to get birthday
     * @return birthday
     */
    public LocalDateTime getBirthday() {
        return birthday;
    }

    /**
     * Method to get weight
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Method to get passport id
     * @return passportID
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * Get all data of person in string
     * @return String with all fields of city
     */
    @Override
    public String toString() {
        String s ="";
        s += "{";
        if (personName != null) s+= "personName : \"" + getPersonName() +"\",";
        s+= "birthday : \"" + birthday + "\",";
        s+= "weight : \"" + getWeight() + "\",";
        if (passportID != null) s+= "passportID : \"" + getPassportID() + "\"}";
        return s;
    }

    public boolean validate() {
        return (personName!=null || (!personName.equals("")) || birthday!=null || weight > 0 || passportID.length()>7
                || passportID!=null);
    }
}
