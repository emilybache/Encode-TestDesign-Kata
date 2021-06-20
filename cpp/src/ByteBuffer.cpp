

#include "ByteBuffer.h"

void ByteBuffer::append(int i) {
    _buffer->push_back(i);
}

int ByteBuffer::getAvailable() {

    return _buffer->size()  - _current;
}

int ByteBuffer::read() {
    int value = _buffer->at(_current);
    _current += 1;
    return value;
}

ByteBuffer::ByteBuffer() {
   _buffer = new std::vector<int>();
   _current = 0;
}


