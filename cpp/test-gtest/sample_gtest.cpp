#include <gtest/gtest.h>

#include "SessionModificationCmd.h"
#include "ByteBuffer.h"
#include "XyzTimer.h"
#include "HexStringEncoder.h"

using namespace std;

TEST(MessageTest, SessionModificationCmd) {
    SessionModificationCmd command(1, 1);
    ByteBuffer data;
    command.setXyzTimer(MultiplesOfHours, 23);
    command.setPqvl(1);
    command.encode(data);
    std::string hexStr;
    HexStringEncoder hex;
    hexStr = hex.encode(data);
    ASSERT_EQ(hexStr, "60010101082091");

    command.setXyzTimer(MultipliesOfMinutes, 32); // outside range(31), expect 31
    command.encode(data);
    hexStr = hex.encode(data);
    ASSERT_EQ(hexStr, "b0010101084091");

    command.setXyzTimer(TimerDeactivated, 2); // deactivated, expect value 0
    command.encode(data);
    hexStr = hex.encode(data);
    ASSERT_EQ(hexStr, "d0010101082091");
}