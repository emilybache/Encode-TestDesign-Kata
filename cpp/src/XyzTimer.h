

#ifndef ENCODE_REFACTORING_KATA_XYZTIMER_H
#define ENCODE_REFACTORING_KATA_XYZTIMER_H

#include "ByteBuffer.h"

enum XyzTimerUnit {
    TimerDeactivated, MultiplesOfHours, MultipliesOfMinutes, MultiplesOfSeconds
};


class XyzTimer {
public:
    XyzTimer();
    void set(XyzTimerUnit unit, int value);
    void encode(ByteBuffer& data);

private:
    XyzTimerUnit timerUnit_;
    int timerValue_;
};


#endif //ENCODE_REFACTORING_KATA_XYZTIMER_H
