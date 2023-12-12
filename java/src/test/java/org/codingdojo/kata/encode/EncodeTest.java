package org.codingdojo.kata.encode;

import org.junit.jupiter.api.Test;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EncodeTest {

    @Test
    void messageTest() {
        var command = new SessionModificationCmd(1, 1);
        var data = new ByteBuffer();
        command.setXyzTimer(XyzTimerUnit.MULTIPLES_OF_HOURS, 23);
        command.setPqvl(1);
        command.encode(data);

        var hex = new HexStringEncoder();
        String hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        assertEquals("03010101083791", hexStr);

        command.setXyzTimer(XyzTimerUnit.MULTIPLES_OF_MINUTES, 32); // outside range(31), expect 31
        command.encode(data);
        hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        assertEquals("03010101085f91", hexStr);

        command.setXyzTimer(XyzTimerUnit.TIMER_DEACTIVATED, 2); // deactivated, expect value 0
        command.encode(data);
        hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        assertEquals("03010101080091", hexStr);
    }

    private static class HexStringEncoder {

        public String encode(ByteBuffer buffer) {
            var hex = new StringBuilder();

            HexFormat format = HexFormat.of();
            while (buffer.getAvailable() > 0) {
                int octet = buffer.read();

                hex.append(format.toHighHexDigit(octet));
                hex.append(format.toLowHexDigit(octet));
            }

            return hex.toString();
        }
    }
}
