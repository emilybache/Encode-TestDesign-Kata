

#include "SessionModificationCmd.h"

void SessionModificationCmd::setXyzTimer(XyzTimerUnit unit, int timerValue)
{
    _hasXyzTimer = true;
    _xyzTimer.set(unit, timerValue);
}

void SessionModificationCmd::setPqvl(int value)
{
    _hasPqvl = true;
    _pqvl    = value;
}

void SessionModificationCmd::encode(ByteBuffer& data)
{
    data.append(_srypId);
    data.append(_sessionId);
    data.append(_transactionId);
    data.append(_type);

    if (_hasXyzTimer)
    {
        _xyzTimer.encode(data);
    }
    if (_hasPqvl)
    {
        encodePqvl(data);
    }
}

void SessionModificationCmd::encodePqvl(ByteBuffer& data)
{
    data.append(YewAlwaysOnSession << 4 | (_pqvl & 0x01));
}


