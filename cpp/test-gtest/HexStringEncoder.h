

#ifndef ENCODE_REFACTORING_KATA_HEXSTRINGENCODER_H
#define ENCODE_REFACTORING_KATA_HEXSTRINGENCODER_H


#include <string>
#include <ByteBuffer.h>

class HexStringEncoder {

public:
    std::string encode(ByteBuffer& buffer);
};


#endif //ENCODE_REFACTORING_KATA_HEXSTRINGENCODER_H
