class ByteBuffer {
    constructor() {
        this.buffer = [];
        this.current = 0;
    }

    append(i) {
        this.buffer.push(i);
    }

    read() {
        if (this.current < this.buffer.length) {
            const value = this.buffer[this.current];
            this.current += 1;
            return value;
        } else {
            return undefined;
        }
    }

    getAvailable() {
        return this.buffer.length - this.current;
    }
}

module.exports = { ByteBuffer };
