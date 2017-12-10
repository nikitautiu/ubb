cmake_minimum_required(VERSION 3.8)
project(lab4)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")

add_subdirectory(Simple-Web-Server)
include_directories(Simple-Web-Server)

set(SERVER_SOURCE_FILES server.cpp ConcreteController.cpp ConcreteController.hpp ThreadPool/ThreadPool.hpp Domain.hpp IController.hpp CliClient.cpp CliClient.hpp Server.cpp Server.hpp)
add_executable(server ${SERVER_SOURCE_FILES})
target_link_libraries(server simple-web-server)

set(CLIENT_SOURCE_FILES client.cpp  ConcreteController.cpp ConcreteController.hpp ThreadPool/ThreadPool.hpp Domain.hpp IController.hpp CliClient.cpp CliClient.hpp Server.cpp Server.hpp client.cpp HttpController.cpp HttpController.hpp)
add_executable(client ${CLIENT_SOURCE_FILES})
target_link_libraries(client simple-web-server)
