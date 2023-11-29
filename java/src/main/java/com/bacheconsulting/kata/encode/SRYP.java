package com.bacheconsulting.kata.encode;

public enum SRYP {
    SESSION_MANAGEMENT(3);

    private final int code;

    SRYP(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
