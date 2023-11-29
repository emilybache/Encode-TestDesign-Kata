package com.bacheconsulting.kata.encode;

public enum SRYP {
    SessionManagement(3);

    private final int code;

    SRYP(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }
}
