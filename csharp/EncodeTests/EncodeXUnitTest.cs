using System;
using EncodeKata;
using Xunit;

public class EncodeXUnitTest
{
    private HexStringEncoder hexStringEncoder = new HexStringEncoder();

    private static SessionModificationCmd CreateSessionModificationCmdWithTimer(XyzTimerUnit timerUnit, int timerValue)
    {
        var command = new SessionModificationCmd(1, 1);
        command.setPqvl(1);
        command.setXyzTimer(timerUnit, timerValue);
        return command;
    }

    [Fact]
    public void ModifySessionTimer()
    {
        var timerUnit = XyzTimerUnit.MultiplesOfHours;
        var timerValue = 23;
        var command = CreateSessionModificationCmdWithTimer(timerUnit, timerValue);
        var data = new ByteBuffer();
        
        command.encode(data);

        var hexStr = hexStringEncoder.encode(data);
        Assert.Equal("03010101083791", hexStr);
    }

    [Fact]
    public void TimerOutsideRange()
    {
        var timerValue = 32; // outside range(31)
        var command = CreateSessionModificationCmdWithTimer(XyzTimerUnit.MultipliesOfMinutes, timerValue);
        var data = new ByteBuffer();

        command.encode(data);

        var hexStr = hexStringEncoder.encode(data);
        Assert.Equal("03010101085f91", hexStr);
        
    }

    [Fact]
    public void DeactivatedTimer()
    {
        var timerUnit = XyzTimerUnit.TimerDeactivated;
        var command = CreateSessionModificationCmdWithTimer(timerUnit, 2);
        var data = new ByteBuffer();

        command.encode(data);

        var hexStr = hexStringEncoder.encode(data);
        Assert.Equal("03010101080091", hexStr);
    }

    public class HexStringEncoder
    {
        public string encode(ByteBuffer buffer)
        {
            string str = "";
            while (buffer.GetAvailable() > 0)
            {
                int octet = buffer.Read();
                int tmp1 = (octet >> 4) & 0x0F;
                int tmp2 = octet & 0x0F;
                char char1;
                char char2;
                if (tmp1 <= 9)
                {
                    char1 = (char)(tmp1 + '0');
                }
                else
                {
                    char1 = (char)(tmp1 - 10 + 'a');
                }

                if (tmp2 <= 9)
                {
                    char2 = (char)(tmp2 + '0');
                }
                else
                {
                    char2 = (char)(tmp2 - 10 + 'a');
                }

                str += char1;
                str += char2;
            }

            return str;
        }
    }
}