package com.bacheconsulting.kata.encode;

public enum YEW {
    YewXyzTimer(8),
    YewAlwaysOnSession(9);

    private final int code;

    YEW(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

}
