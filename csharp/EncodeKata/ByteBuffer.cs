using System.Collections.Generic;

namespace EncodeKata
{
    public class ByteBuffer
    {
        private List<int> buffer = new List<int>();
        private int current;
        public void Append(int i)
        {
            buffer.Add(i);
        }

        public int Read()
        {
            var value = buffer[current];
            current += 1;
            return value;
        }

        public int GetAvailable()
        {
            return buffer.Count - current;
        }
    }
}