var { SessionModificationCmd, XyzTimerUnit } = require('../src/SessionModificationCmd');
var { ByteBuffer } = require('../src/ByteBuffer');
var { HexStringEncoder } = require('../src/HexStringEncoder');

describe('EncodeTest', () => {
  it('encodes messages', () => {
    const command = new SessionModificationCmd(1, 1);
    const data = new ByteBuffer();
    command.setXyzTimer(XyzTimerUnit.MultiplesOfHours, 23);
    command.setPqvl(1);
    command.encode(data);

    const hex = new HexStringEncoder();
    const hexStr = hex.encode(data);
    console.log(`Hex: ${hexStr}`);
    expect(hexStr).toBe('03010101083791');

    command.setXyzTimer(XyzTimerUnit.MultiplesOfMinutes, 32); // outside range(31), expect 31
    command.encode(data);
    const hexStr2 = hex.encode(data);
    console.log(`Hex: ${hexStr2}`);
    expect(hexStr2).toBe('03010101085f91');

    command.setXyzTimer(XyzTimerUnit.TimerDeactivated, 2); // deactivated, expect value 0
    command.encode(data);
    const hexStr3 = hex.encode(data);
    console.log(`Hex: ${hexStr3}`);
    expect(hexStr3).toBe('03010101080091');
  });
});
