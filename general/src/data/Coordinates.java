package data;

import java.io.Serializable;

public class Coordinates implements Validateable, Serializable {
    private long x; //Максимальное значение поля: 658
    private Integer y; //Максимальное значение поля: 211, Поле не может быть null

    public void setX(long x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * Constructor
     * @param x
     * @param y
     */
    public Coordinates(long x, Integer y){
        this.x = x;
        this.y = y;
    }
    public Coordinates(){}

    /**
     *
     * @return x coordinate
     */
    public long getX() {
        return this.x;
    }

    /**
     *
     * @return y coordinate
     */
    public Integer getY() {
        return y;
    }

    /**
     * Convert coordinates data to string
     * @return String with coordinates data
     */
    @Override
    public String toString() {
        String s ="";
        s+="{x : \"" + Long.toString(x) + "\",} ";
        s+="{y : \"" + Integer.toString(y) +"\"}";
        return s;
    }

    public boolean validate() {
        return !(y==null || y.intValue()>211 || x>658);
    }
}

