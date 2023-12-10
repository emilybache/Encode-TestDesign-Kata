class HexStringEncoder {

    func encode(_ buffer: ByteBuffer) -> String {
        var str = ""
        while buffer.getAvailable() > 0 {
            if let octet = buffer.read() {
                let tmp1 = (octet >> 4) & 0x0F
                let tmp2 = octet & 0x0F

                let char1: Character
                let char2: Character

                if tmp1 <= 9 {
                    char1 = Character(UnicodeScalar(tmp1 + 48)!) // ASCII for '0'
                } else {
                    char1 = Character(UnicodeScalar(tmp1 - 10 + 97)!) // ASCII for 'a'
                }

                if tmp2 <= 9 {
                    char2 = Character(UnicodeScalar(tmp2 + 48)!) // ASCII for '0'
                } else {
                    char2 = Character(UnicodeScalar(tmp2 - 10 + 97)!) // ASCII for 'a'
                }

                str.append(char1)
                str.append(char2)
            }
        }

        return str
    }
}
