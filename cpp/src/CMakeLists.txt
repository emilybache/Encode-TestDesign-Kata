set(SRC_LIB_NAME src)
set(SOURCES SessionModificationCmd.cpp SessionModificationCmd.h ByteBuffer.cpp ByteBuffer.h XyzTimer.cpp XyzTimer.h HexStringEncoder.cpp)
add_library(${SRC_LIB_NAME} ${SOURCES})
target_sources(${SRC_LIB_NAME} PRIVATE ${SOURCES})
target_include_directories(${SRC_LIB_NAME} PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
