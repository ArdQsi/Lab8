package connection;

public class AnswerMsg implements Response {
    private String msg;
    private Status status;

    public AnswerMsg() {
        msg = "";
        status = Status.FINE;
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
        setStatus(Status.ERRROR);
        return this;
    }

    public AnswerMsg setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return msg;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        if (getStatus() == Status.ERRROR) {
            return "Err: " + getMessage();
        }
        return getMessage();
    }


}
