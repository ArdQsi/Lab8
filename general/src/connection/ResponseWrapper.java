package connection;

import java.io.Serializable;

public class ResponseWrapper extends AnswerMsg implements Response, Serializable {
    AnswerMsg answerMsg;
    byte[] msgBytes;
    Status status;
    CollectionOperation collectionOperation;

    public String getMessage() {
        return answerMsg.getMessage();
    }

    public ResponseWrapper(final String msg) {
        super();
        msgBytes = msg.getBytes();
    }

    public ResponseWrapper(byte[] response, boolean isBoolean) {
        this.msgBytes = response;
        this.isBoolean = isBoolean;
    }

    private Response response;
    private boolean isBoolean;

    public ResponseWrapper(Response response, boolean isBoolean) {
        this.response = response;
        this.isBoolean = isBoolean;
    }

    public boolean getIsBoolean() {
        return isBoolean;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public AnswerMsg setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public CollectionOperation getCollectionOperations() {
        return this.collectionOperation;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setCollectionOperations(CollectionOperation collectionOperation){
        this.collectionOperation = collectionOperation;
    }

    public byte[] getMsgBytes() {
        return getMsgBytes();
    }
}
