package com.bacheconsulting.kata.encode;

final class XyzTimer {

    private XyzTimerUnit timerUnit;
    private int timerValue;

    public void set(XyzTimerUnit unit, int timerValue) {
        this.timerUnit = unit;
        this.timerValue = timerValue;
    }

    public void encode(ByteBuffer data) {
        data.append(YEW.YEW_XYZ_TIMER.code());
        int temp;
        temp = (this.timerUnit.ordinal() << 5);

        if (this.timerUnit != XyzTimerUnit.TIMER_DEACTIVATED) {
            if (this.timerValue <= 0x1F) {
                temp |= (this.timerValue & 0x1F);
            } else {
                System.out.print("XYZ timer value out of range. Encoding max value\n");
                temp |= 0x1F;
            }
        }
        data.append(temp);
    }
}