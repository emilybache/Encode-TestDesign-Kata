@testable import Encode_TestDesign_Kata
import XCTest

class EncodeTests: XCTestCase {
    func testMessage() {
        let command = SessionModificationCmd(sessionId: 1, transactionId: 1)
        let data = ByteBuffer()
        command.setXyzTimer(unit: .multiplesOfHours, timerValue: 23)
        command.setPqvl(value: 1)
        command.encode(data)

        let hex = HexStringEncoder()
        let hexStr = hex.encode(data)
        print("Hex: \(hexStr)")
        XCTAssertEqual(hexStr, "03010101083791")

        command.setXyzTimer(unit: .multiplesOfMinutes, timerValue: 32) // outside range(31), expect 31
        command.encode(data)
        let hexStr2 = hex.encode(data)
        print("Hex: \(hexStr2)")
        XCTAssertEqual(hexStr2, "03010101085f91")

        command.setXyzTimer(unit: .timerDeactivated, timerValue: 2) // deactivated, expect value 0
        command.encode(data)
        let hexStr3 = hex.encode(data)
        print("Hex: \(hexStr3)")
        XCTAssertEqual(hexStr3, "03010101080091")
    }
}
