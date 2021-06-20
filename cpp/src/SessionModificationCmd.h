

#ifndef ENCODE_REFACTORING_KATA_SESSIONMODIFICATIONCMD_H
#define ENCODE_REFACTORING_KATA_SESSIONMODIFICATIONCMD_H


#include "XyzTimer.h"
#include "ByteBuffer.h"

enum MessageType {
    SessionCreation = 0x0,
    SessionModification = 0x1,
    SessionDelete = 0x2,
};

enum YEW {
    YewXyzTimer = 0x8,
    YewAlwaysOnSession = 0x9,
};

class SessionModificationCmd {
public:
    SessionModificationCmd(int sessionId, int transactionId) :
            _type(SessionModification), _sessionId(sessionId), _transactionId(transactionId), _hasXyzTimer(false), _hasPqvl(false) {}

    void setXyzTimer(XyzTimerUnit unit, int timerValue);
    void setPqvl(int value);
    void encode(ByteBuffer& data);
private:

    MessageType _type;
    int _sessionId;
    int _transactionId;
    bool _hasXyzTimer;

    XyzTimer _xyzTimer;
    bool _hasPqvl;
    int _pqvl;
    int _srypId;
    void encodePqvl(ByteBuffer& data);
};


#endif //ENCODE_REFACTORING_KATA_SESSIONMODIFICATIONCMD_H
