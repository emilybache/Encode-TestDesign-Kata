#include <SessionModificationCmd.h>
#include <iostream>
#include "ApprovalTests.hpp"
#include "catch2/catch.hpp"
#include "HexStringEncoder.h"

using namespace std;


TEST_CASE ("SessionModificationCmd") {
    SessionModificationCmd command(1, 1);
    ByteBuffer data;
    HexStringEncoder hex;
    command.setPqvl(1);

    SECTION("MultiplesOfHours") {
        command.setXyzTimer(MultiplesOfHours, 23);
        command.encode(data);
        REQUIRE(hex.encode(data) == "03010101083791");
    }

    SECTION("MultiplesOfMinutes") {
        command.setXyzTimer(MultipliesOfMinutes, 32); // outside range(31), expect 31
        command.encode(data);
        REQUIRE(hex.encode(data) == "03010101085f91");
    }

    SECTION("TimerDeactivated"){
        command.setXyzTimer(TimerDeactivated, 2); // deactivated, expect value 0
        command.encode(data);
        REQUIRE(hex.encode(data) == "03010101080091");
    }

}


