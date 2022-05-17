package data;

import java.io.Serializable;

/**
 * interface for storable object
 */
public interface Collectionable extends Comparable<Collectionable>, Validateable {
    /**
     * get element id
     *
     * @return
     */
    public long getId();

    /**
     * setId useful for replacing object in collection or set id for object
     *
     * @param ID
     */
    public void setId(long ID);

    /**
     * get name of element
     *
     * @return
     */
    public String getName();

    /**
     * get price of element
     *
     * @return
     */
    public Integer getPrice();

    /**
     * compare two object
     *
     * @param product
     * @return
     */
    public int compareTo(Collectionable product);

    public boolean validate();

}

