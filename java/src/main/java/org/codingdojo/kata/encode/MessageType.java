package org.codingdojo.kata.encode;

enum MessageType {
    SESSION_CREATION(0),
    SESSION_MODIFICATION(1),
    SESSION_DELETE(2);

    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    int code() {
        return code;
    }
}
