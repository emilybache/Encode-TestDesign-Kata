from encode import SessionModificationCommand, ByteBuffer, XyzTimerUnit


class HexStringEncoder:
    def encode(self, buffer):
        s = ""
        while buffer.getAvailable() > 0:
            octet = buffer.read()
            s += f"{octet:02x}"
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
    assert hexStr == "03010101083791"

    command.update_xyz_timer(XyzTimerUnit.MultiplesOfMinutes, 32) # outside range(32), expect 31
    command.encode(data)
    hexStr = hexEncoder.encode(data)
    print(f"Hex:{hexStr}")
    assert hexStr == "03010101085f91"

    command.update_xyz_timer(XyzTimerUnit.TimerDeactivated, 2) # deactivated, expect value 0
    command.encode(data)
    hexStr = hexEncoder.encode(data)
    print(f"Hex:{hexStr}")
    assert hexStr == "03010101080091"
