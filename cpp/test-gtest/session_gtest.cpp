#include <gtest/gtest.h>

#include "SessionModificationCmd.h"
#include "ByteBuffer.h"
#include "XyzTimer.h"
#include "HexStringEncoder.h"

using namespace std;

class MessageTest : public ::testing::Test {

    virtual void SetUp() {
        command = new SessionModificationCmd(1, 1);
        data = new ByteBuffer();
        hex = new HexStringEncoder();
    }

    virtual void TearDown() {
        delete command;
        delete data;
        delete hex;
    }

public:
    SessionModificationCmd* command;
    ByteBuffer* data;
    HexStringEncoder* hex;
};

TEST_F(MessageTest, SessionModificationCmd_MultiplesOfHours) {
    command->setXyzTimer(MultiplesOfHours, 23);
    command->setPqvl(1);

    command->encode(*data);

    ASSERT_EQ(hex->encode(*data), "03010101083791");
}

TEST_F(MessageTest, SessionModificationCmd_MultiplesOfMinutes) {
    command->setPqvl(1);
    command->setXyzTimer(MultipliesOfMinutes, 32); // outside range(31), expect 31

    command->encode(*data);

    ASSERT_EQ(hex->encode(*data), "03010101085f91");
}

TEST_F(MessageTest, SessionModificationCmd_TimerDeactivated) {
    command->setPqvl(1);
    command->setXyzTimer(TimerDeactivated, 2); // deactivated, expect value 0

    command->encode(*data);

    ASSERT_EQ(hex->encode(*data), "03010101080091");
}