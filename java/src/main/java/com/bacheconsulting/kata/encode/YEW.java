package com.bacheconsulting.kata.encode;

enum YEW {
    YEW_XYZ_TIMER(8),
    YEW_ALWAYS_ON_SESSION(9);

    private final int code;

    YEW(int code) {
        this.code = code;
    }

    int code() {
        return this.code;
    }
}
