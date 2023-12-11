class SessionModificationCmd {
    private let messageType: MessageType = .sessionModification
    private let sessionId: Int
    private let transactionId: Int
    private var hasPqvl = false
    private var pqvl = 0
    private var srypId: SRYP = .sessionManagement
    private var hasXyzTimer = false
    private var xyzTimer = XyzTimer()

    init(sessionId: Int, transactionId: Int) {
        self.sessionId = sessionId
        self.transactionId = transactionId
    }

    func setXyzTimer(unit: XyzTimerUnit, timerValue: Int) {
        hasXyzTimer = true
        xyzTimer.set(unit: unit, timerValue: timerValue)
    }

    func setPqvl(value: Int) {
        hasPqvl = true
        pqvl = value
    }

    func encode(_ data: ByteBuffer) {
        data.append(srypId.rawValue)
        data.append(sessionId)
        data.append(transactionId)
        data.append(messageType.rawValue)

        if hasXyzTimer {
            xyzTimer.encode(data: data)
        }
        if hasPqvl {
            encodePqvl(data: data)
        }
    }

    private func encodePqvl(data: ByteBuffer) {
        data.append(YEW.yewAlwaysOnSession.rawValue << 4 | (pqvl & 0x01))
    }
}

class XyzTimer {
    private var timerUnit: XyzTimerUnit = .timerDeactivated
    private var timerValue = 0

    func set(unit: XyzTimerUnit, timerValue: Int) {
        timerUnit = unit
        self.timerValue = timerValue
    }

    func encode(data: ByteBuffer) {
    data.append(YEW.yewXyzTimer.rawValue)
        var temp = timerUnit.rawValue << 5

        if timerUnit != .timerDeactivated {
            if timerValue <= 0x1F { // 5 bits max range
                temp |= timerValue & 0x1F
            } else {
                print("XYZ timer value out of range. Encoding max value")
                temp |= 0x1F
            }
        }
        data.append(temp)
    }
}

enum SRYP: Int {
    case sessionManagement = 0x3
}

enum YEW: Int {
    case yewXyzTimer = 0x8
    case yewAlwaysOnSession = 0x9
}

enum MessageType: Int {
    case sessionCreation = 0x0
    case sessionModification = 0x1
    case sessionDelete = 0x2
}

enum XyzTimerUnit: Int {
    case timerDeactivated
    case multiplesOfHours
    case multipliesOfMinutes
    case multiplesOfSeconds
}
