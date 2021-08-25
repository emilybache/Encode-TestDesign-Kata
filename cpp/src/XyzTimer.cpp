    

#include <iostream>
#include "XyzTimer.h"
#include "SessionModificationCmd.h"

XyzTimer::XyzTimer() :
        timerValue_(0), timerUnit_(XyzTimerUnit::TimerDeactivated) {}

void XyzTimer::set(XyzTimerUnit unit, int value)
{
    timerUnit_  = unit;
    timerValue_ = value;
}

void XyzTimer::encode(ByteBuffer& data)
{
    data.append(YEW::YewXyzTimer);
    uint8_t temp;
    temp = timerUnit_ << 5;

    if (timerUnit_ != XyzTimerUnit::TimerDeactivated)
    {
        if (timerValue_ <= 0x1F) // 5 bits max range
        {
            temp |= (timerValue_ & 0x1F);
        }
        else
        {
            std::cout << "XYZ timer value out of range. Encoding max value" << std::endl;
            temp |= 0x1F;
        }
    }
    data.append(temp);
}

