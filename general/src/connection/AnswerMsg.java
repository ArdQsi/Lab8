package connection;

import data.Product;

import java.util.Collection;

public class AnswerMsg implements Response {
    private String msg;
    private Status status;
    private Collection<Product> collection;
    private CollectionOperation collectionOperation;
    private long id;

    public AnswerMsg() {
        msg = "";
        status = Status.FINE;
        collectionOperation = CollectionOperation.NONE;
    }
    public void setStatuss(Status status) {
        this.status = status;
    }

    public AnswerMsg clear() {
        msg = "";
        return this;
    }

    public AnswerMsg info(Object str) {
        msg = str.toString();
        return this;
    }

    public AnswerMsg error(Object str) {
        msg = str.toString();
        setStatus(Status.ERROR);
        return this;
    }

    public AnswerMsg setStatus(Status status) {
        this.status = status;
        return this;
    }

    public AnswerMsg setCollectionOperation(CollectionOperation op) {
        collectionOperation = op;
        return this;
    }

    public void setCollectionOperations(CollectionOperation op) {

    }

    public CollectionOperation getCollectionOperation() {
        return collectionOperation;
    }


    public AnswerMsg setCollection(Collection<Product> c) {
        collection = c;
        return this;
    }
    public AnswerMsg setId(long c) {
        id = c;
        return this;
    }

    public Collection<Product> getCollection(){
        return collection;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public Long getIds() {
        return null;
    }

    public Status getStatus() {
        return status;
    }


    public void setStatus(AnswerMsg status) {

    }

    public Status getStatuss() {
        return status;
    }

    @Override
    public String toString() {
        if (getStatus() == Status.ERROR) {
            return "Err: " + getMessage();
        }
        return getMessage();
    }


}
