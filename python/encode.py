from enum import Enum


class MessageType(Enum):
    SessionCreation = 0
    SessionModification = 1
    SessionDelete = 2


class XyzTimerUnit(Enum):
    TimerDeactivated = 0
    MultiplesOfHours = 1
    MultiplesOfMinutes = 2
    MultiplesOfSeconds = 3


class SRYP(Enum):
    SessionManagement = 3


class YEW(Enum):
    YewXyzTimer = 0x8
    YewAlwaysOnSession = 0x9


class XyzTimer:
    def __init__(self):
        self.timer_unit = XyzTimerUnit.TimerDeactivated
        self.timer_value = 0

    def set(self, unit, amount):
        self.timer_unit = unit
        self.timer_value = amount

    def encode(self, data):
        data.append(YEW.YewXyzTimer.value)
        temp = self.timer_unit.value << 5
        if self.timer_unit != XyzTimerUnit.TimerDeactivated:
            if self.timer_value <= 0x1F: # 5 bits max range
                temp |= (self.timer_value & 0x1F)
            else:
                print("XYZ timer value out of range. Encoding max value")
                temp |= 0x1F
        data.append(temp)


class SessionModificationCommand:
    def __init__(self, session_id, transaction_id):
        self.type = MessageType.SessionModification
        self.session_id = session_id
        self.transaction_id = transaction_id
        self.has_xyz_timer = False
        self.xyz_timer = XyzTimer()
        self.has_pqvl = False
        self.pvql = 0
        self.sryp_id = SRYP.SessionManagement

    def update_xyz_timer(self, unit, amount):
        self.has_xyz_timer = True
        self.xyz_timer.set(unit, amount)

    def update_pqvl(self, value):
        self.has_pqvl = True
        self.pvql = value

    def encode(self, data):
        data.append(self.sryp_id.value)
        data.append(self.session_id)
        data.append(self.transaction_id)
        data.append(self.type.value)
        
        if (self.has_xyz_timer):
            self.xyz_timer.encode(data)
        if (self.has_pqvl):
            self.encode_pqvl(data)

    def encode_pqvl(self, data):
        data.append(YEW.YewAlwaysOnSession.value << 4 | (self.pvql & 0x01))

class ByteBuffer:
    def __init__(self):
        self.buffer = []
        self.current = 0

    def getAvailable(self):
        return len(self.buffer) - self.current

    def read(self):
        value = self.buffer[self.current]
        self.current += 1
        return value

    def append(self, i):
        self.buffer.append(i)