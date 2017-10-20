cmake_minimum_required(VERSION 3.8)
project(lab2)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")


set(HEADER_FILES operations.hpp)
set(SOURCE_FILES main.cpp operations.h utils.h)
add_executable(lab2 ${SOURCE_FILES})