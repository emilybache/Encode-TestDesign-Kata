package com.bacheconsulting.kata.encode;

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
        this.hasXyzTimer = true;
        this.xyzTimer.set(unit, timerValue);
    }

    public void setPqvl(int value) {
        this.hasPqvl = true;
        this.pqvl = value;
    }

    public void encode(ByteBuffer data) {
        data.append(this.srypId.code());
        data.append(this.sessionId);
        data.append(this.transactionId);
        data.append(this.messageType.code());

        if (this.hasXyzTimer) {
            this.xyzTimer.encode(data);
        }
        if (this.hasPqvl) {
            this.encodePqvl(data);
        }
    }

    private void encodePqvl(ByteBuffer data) {
        data.append((YEW.YEW_ALWAYS_ON_SESSION.code() << 4) | (this.pqvl & 0x01));
    }
}
