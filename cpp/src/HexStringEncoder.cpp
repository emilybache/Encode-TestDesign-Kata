

#include "HexStringEncoder.h"

std::string HexStringEncoder::encode(ByteBuffer& buffer) {
    std::string str;
    while (buffer.getAvailable() > 0) {
        int octet = buffer.read();
        int tmp1 = (octet >> 4) & 0x0F;
        int tmp2 = octet & 0x0F;
        char    char1;
        char    char2;
        if (tmp1 <= 9)
        {
            char1 = tmp1 + '0';
        }
        else
        {
            char1 = tmp1 - 10 + 'a';
        }
        if (tmp2 <= 9)
        {
            char2 = tmp2 + '0';
        }
        else
        {
            char2 = tmp2 - 10 + 'a';
        }
        std::string str3 = "00";
        str3[0] = char1;
        str3[1] = char2;
        str.append(str3);
    }

    return str;
}
