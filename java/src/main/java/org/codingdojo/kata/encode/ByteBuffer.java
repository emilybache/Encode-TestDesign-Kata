package org.codingdojo.kata.encode;

import java.util.ArrayList;
import java.util.List;

public class ByteBuffer {

    private final List<Byte> buffer = new ArrayList<>();
    private int current;

    public void append(int i) {
        buffer.add((byte) i);
    }

    public int read() {
        return buffer.get(current++);
    }

    public int getAvailable() {
        return buffer.size() - current;
    }
}
