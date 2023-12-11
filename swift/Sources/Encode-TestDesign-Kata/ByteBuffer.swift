class ByteBuffer {
    private var buffer: [Int] = []
    private var current = 0

    func append(_ i: Int) {
        buffer.append(i)
    }

    func read() -> Int? {
        guard current < buffer.count else {
            return nil
        }
        let value = buffer[current]
        current += 1
        return value
    }

    func getAvailable() -> Int {
        return buffer.count - current
    }
}
