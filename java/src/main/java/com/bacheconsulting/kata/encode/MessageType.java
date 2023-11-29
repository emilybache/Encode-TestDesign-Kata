package com.bacheconsulting.kata.encode;

public enum MessageType {
    SESSION_CREATION(0),
    SESSION_MODIFICATION(1),
    SESSION_DELETE(2);

    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
