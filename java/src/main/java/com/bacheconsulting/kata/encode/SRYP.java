package com.bacheconsulting.kata.encode;

enum SRYP {
    SESSION_MANAGEMENT(3);

    private final int code;

    SRYP(int code) {
        this.code = code;
    }

    int code() {
        return this.code;
    }
}
