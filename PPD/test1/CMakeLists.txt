cmake_minimum_required(VERSION 3.8)
project(lab3)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")


set(SOURCE_FILES main.cpp linkedlist/IList.cpp linkedlist/IList.hpp linkedlist/FineList.cpp linkedlist/FineList.hpp linkedlist/Logger.hpp linkedlist/Logger.cpp)
add_executable(test1 ${SOURCE_FILES})