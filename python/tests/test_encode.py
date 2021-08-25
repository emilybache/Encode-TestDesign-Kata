from encode import SessionModificationCommand, ByteBuffer, XyzTimerUnit


class HexStringEncoder:
    def encode(self, buffer):
        s = ""
        while buffer.getAvailable() > 0:
            octet = buffer.read()
            tmp1 = (octet >> 4) & 0x0F
            tmp2 = octet & 0x0F
            if (tmp1 <= 9):
                char1 = str(tmp1) + '0'
            else:
                char1 = str(tmp1 - 10) + 'a'

            if (tmp2 <= 9):
                char2 = str(tmp2) + '0'
            else:
                char2 = str(tmp2 - 10) + 'a'
            str3 = f"{char1}{char2}"
            s += str3
        return s


def test_session_modification_cmd():
    command = SessionModificationCommand(1, 1)
    data = ByteBuffer()
    command.update_xyz_timer(XyzTimerUnit.MultiplesOfHours, 23)
    command.update_pqvl(1)
    command.encode(data)
    hexEncoder = HexStringEncoder()
    hexStr = hexEncoder.encode(data)
    print(f"Hex:{hexStr}")
    assert hexStr == "0000001000100010008030709010"

    command.update_xyz_timer(XyzTimerUnit.MultiplesOfMinutes, 32) # outside range(32), expect 31
    command.encode(data)
    hexStr = hexEncoder.encode(data)
    print(f"Hex:{hexStr}")
    assert hexStr == "00000010001000100080505a9010"

    command.update_xyz_timer(XyzTimerUnit.TimerDeactivated, 2) # deactivated, expect value 0
    command.encode(data)
    hexStr = hexEncoder.encode(data)
    print(f"Hex:{hexStr}")
    assert hexStr == "0000001000100010008000009010"
