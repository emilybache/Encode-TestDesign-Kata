class HexStringEncoder {
    encode(buffer) {
        let str = '';
        while (buffer.getAvailable() > 0) {
            const octet = buffer.read();
            const tmp1 = (octet >> 4) & 0x0F;
            const tmp2 = octet & 0x0F;
            let char1;
            let char2;

            if (tmp1 <= 9) {
                char1 = String.fromCharCode(tmp1 + 48); // ASCII for '0'
            } else {
                char1 = String.fromCharCode(tmp1 - 10 + 97); // ASCII for 'a'
            }

            if (tmp2 <= 9) {
                char2 = String.fromCharCode(tmp2 + 48); // ASCII for '0'
            } else {
                char2 = String.fromCharCode(tmp2 - 10 + 97); // ASCII for 'a'
            }

            str += char1;
            str += char2;
        }

        return str;
    }
}

module.exports = { HexStringEncoder };
