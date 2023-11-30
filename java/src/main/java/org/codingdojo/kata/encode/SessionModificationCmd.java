package org.codingdojo.kata.encode;

public class SessionModificationCmd {

    private final MessageType messageType;
    private final int sessionId;
    private final int transactionId;
    private boolean hasPqvl;
    private int pqvl;
    private final SRYP srypId;
    private boolean hasXyzTimer;
    private final XyzTimer xyzTimer;

    public SessionModificationCmd(int sessionId, int transactionId) {
        this.messageType = MessageType.SESSION_MODIFICATION;
        this.sessionId = sessionId;
        this.transactionId = transactionId;
        this.xyzTimer = new XyzTimer();
        this.srypId = SRYP.SESSION_MANAGEMENT;
    }

    public void setXyzTimer(XyzTimerUnit unit, int timerValue) {
        hasXyzTimer = true;
        xyzTimer.set(unit, timerValue);
    }

    public void setPqvl(int value) {
        hasPqvl = true;
        pqvl = value;
    }

    public void encode(ByteBuffer data) {
        data.append(srypId.code());
        data.append(sessionId);
        data.append(transactionId);
        data.append(messageType.code());

        if (hasXyzTimer) {
            xyzTimer.encode(data);
        }
        if (hasPqvl) {
            encodePqvl(data);
        }
    }

    private void encodePqvl(ByteBuffer data) {
        data.append((YEW.YEW_ALWAYS_ON_SESSION.code() << 4) | (pqvl & 0x01));
    }
}
