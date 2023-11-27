package encodeKata;

public enum MessageType {
    SessionCreation(0),
    SessionModification(1),
    SessionDelete(2);

    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
