package encodeKata;

import java.util.ArrayList;
import java.util.List;

public class ByteBuffer {
    private final List<Integer> buffer = new ArrayList<>();
    private int current;

    public void append(int i) {
        buffer.add(i);
    }

    public int read() {
        int value = buffer.get(current);
        current += 1;
        return value;
    }

    public int getAvailable() {
        return buffer.size() - current;
    }

}