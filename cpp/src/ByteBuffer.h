

#ifndef ENCODE_REFACTORING_KATA_BYTEBUFFER_H
#define ENCODE_REFACTORING_KATA_BYTEBUFFER_H


#include <vector>

class ByteBuffer {
public:
    ByteBuffer();

    void append(int i);
    int getAvailable();
    int read();

private:
    std::vector<int>* _buffer;
    int _current;
};


#endif //ENCODE_REFACTORING_KATA_BYTEBUFFER_H
