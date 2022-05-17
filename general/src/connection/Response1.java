package connection;

import java.io.Serializable;

public class Response1 extends AnswerMsg implements Response, Serializable {
    AnswerMsg answerMsg;
    byte[] msgBytes;

    public String getMessage() {
        return answerMsg.getMessage();
    }

    public Response1(final String msg) {
        super();
        msgBytes = msg.getBytes();
    }

    public Response1(byte[] response, boolean isBoolean) {
        this.msgBytes = response;
        this.isBoolean = isBoolean;
    }

    private Response response;
    private boolean isBoolean;

    public Response1(Response response, boolean isBoolean) {
        this.response = response;
        this.isBoolean = isBoolean;
    }

    public boolean getIsBoolean() {
        return isBoolean;
    }

    public Response getResponse() {
        return response;
    }

    public byte[] getMsgBytes() {
        return getMsgBytes();
    }
}
