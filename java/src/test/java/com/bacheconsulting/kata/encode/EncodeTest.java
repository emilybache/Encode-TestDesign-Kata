package com.bacheconsulting.kata.encode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncodeTest {

    @Test
    public void MessageTest() {
        var command = new SessionModificationCmd(1, 1);
        var data = new ByteBuffer();
        command.setXyzTimer(XyzTimerUnit.MULTIPLES_OF_HOURS, 23);
        command.setPqvl(1);
        command.encode(data);

        var hex = new HexStringEncoder();
        String hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        Assertions.assertEquals(hexStr, "03010101083791");

        command.setXyzTimer(XyzTimerUnit.MULTIPLIES_OF_MINUTES, 32); // outside range(31), expect 31
        command.encode(data);
        hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        Assertions.assertEquals(hexStr, "03010101085f91");

        command.setXyzTimer(XyzTimerUnit.TIMER_DEACTIVATED, 2); // deactivated, expect value 0
        command.encode(data);
        hexStr = hex.encode(data);
        System.out.println("Hex: " + hexStr + "\n");
        Assertions.assertEquals(hexStr, "03010101080091");
    }

    static class HexStringEncoder {
        public String encode(ByteBuffer buffer) {
            String str = "";
            while (buffer.getAvailable() > 0) {
                int octet = buffer.read();
                int tmp1 = (octet >> 4) & 0x0F;
                int tmp2 = octet & 0x0F;
                char char1;
                char char2;
                if (tmp1 <= 9) {
                    char1 = (char) (tmp1 + '0');
                } else {
                    char1 = (char) (tmp1 - 10 + 'a');
                }

                if (tmp2 <= 9) {
                    char2 = (char) (tmp2 + '0');
                } else {
                    char2 = (char) (tmp2 - 10 + 'a');
                }

                str += char1;
                str += char2;
            }

            return str;
        }
    }
}

