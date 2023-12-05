using System;

namespace EncodeKata
{
    public class SessionModificationCmd
    {
        private readonly MessageType messageType;
        private readonly int sessionId;
        private readonly int transactionId;
        private bool hasPqvl;
        private int pqvl;
        private SRYP srypId;
        private bool hasXyzTimer;
        private XyzTimer xyzTimer;

        public SessionModificationCmd(int sessionId, int transactionId)
        {
            this.messageType = MessageType.SessionModification;
            this.sessionId = sessionId;
            this.transactionId = transactionId;
            this.xyzTimer = new XyzTimer();
            this.srypId = SRYP.SessionManagement;
        }

        public void SetXyzTimer(XyzTimerUnit unit, int timerValue)
        {
            this.hasXyzTimer = true;
            this.xyzTimer.Set(unit, timerValue);
        }

        public void SetPqvl(int value)
        {
            this.hasPqvl = true;
            this.pqvl = value;
        }   

        public void Encode(ByteBuffer data)
        {
            data.Append((int)this.srypId);
            data.Append(this.sessionId);
            data.Append(this.transactionId);
            data.Append((int)this.messageType);

            if (this.hasXyzTimer)
            {
                this.xyzTimer.Encode(data);
            }
            if (this.hasPqvl)
            {
                this.encodePqvl(data);
            }
        }

        private void encodePqvl(ByteBuffer data)
        {
            data.Append((int)YEW.YewAlwaysOnSession << 4 | (this.pqvl & 0x01));
        }
    }

    internal class XyzTimer
    {
        private XyzTimerUnit timerUnit;
        private int timerValue;

        public void Set(XyzTimerUnit unit, int timerValue)
        {
            this.timerUnit = unit;
            this.timerValue = timerValue;
        }

        public void Encode(ByteBuffer data)
        {
            data.Append((int)YEW.YewXyzTimer);
            int temp;
            temp = (int)this.timerUnit << 5;

            if (this.timerUnit != XyzTimerUnit.TimerDeactivated)
            {
                if (this.timerValue <= 0x1F) // 5 bits max range
                {
                    temp |= (this.timerValue & 0x1F);
                }
                else
                {
                    Console.Write("XYZ timer value out of range. Encoding max value\n");
                    temp |= 0x1F;
                }
            }
            data.Append(temp);
        }
    }
    
    public enum SRYP
    {
        SessionManagement = 0x3,
    }

    public enum YEW
    {
        YewXyzTimer = 0x8,
        YewAlwaysOnSession = 0x9,
    }
    public enum MessageType 
    {
        SessionCreation = 0x0,
        SessionModification = 0x1,
        SessionDelete = 0x2,
    };
    public enum XyzTimerUnit 
    {
        TimerDeactivated,
        MultiplesOfHours,
        MultipliesOfMinutes,
        MultiplesOfSeconds,
    };
}