class SessionModificationCmd {
    constructor(sessionId, transactionId) {
        this.messageType = MessageType.SessionModification;
        this.sessionId = sessionId;
        this.transactionId = transactionId;
        this.hasPqvl = false;
        this.pqvl = 0;
        this.srypId = SRYP.SessionManagement;
        this.hasXyzTimer = false;
        this.xyzTimer = new XyzTimer();
    }

    setXyzTimer(unit, timerValue) {
        this.hasXyzTimer = true;
        this.xyzTimer.set(unit, timerValue);
    }

    setPqvl(value) {
        this.hasPqvl = true;
        this.pqvl = value;
    }

    encode(data) {
        data.append(this.srypId);
        data.append(this.sessionId);
        data.append(this.transactionId);
        data.append(this.messageType);

        if (this.hasXyzTimer) {
            this.xyzTimer.encode(data);
        }
        if (this.hasPqvl) {
            this.encodePqvl(data);
        }
    }

    encodePqvl(data) {
        data.append(YEW.YewAlwaysOnSession << 4 | (this.pqvl & 0x01));
    }
}

class XyzTimer {
    set(unit, timerValue) {
        this.timerUnit = unit;
        this.timerValue = timerValue;
    }

    encode(data) {
        data.append(YEW.YewXyzTimer);
        let temp = this.timerUnit << 5;

        if (this.timerUnit !== XyzTimerUnit.TimerDeactivated) {
            if (this.timerValue <= 0x1F) {
                temp |= (this.timerValue & 0x1F);
            } else {
                console.log("XYZ timer value out of range. Encoding max value");
                temp |= 0x1F;
            }
        }
        data.append(temp);
    }
}

const SRYP = {
    SessionManagement: 0x3,
};

const YEW = {
    YewXyzTimer: 0x8,
    YewAlwaysOnSession: 0x9,
};

const MessageType = {
    SessionCreation: 0x0,
    SessionModification: 0x1,
    SessionDelete: 0x2,
};

const XyzTimerUnit = {
    TimerDeactivated: 0,
    MultiplesOfHours: 1,
    MultiplesOfMinutes: 2,
    MultiplesOfSeconds: 3,
};

module.exports = { SessionModificationCmd, XyzTimerUnit };
