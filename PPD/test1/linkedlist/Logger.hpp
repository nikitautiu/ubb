//
// Created by nikitautiu on 11/20/17.
//

#ifndef LAB3_LOGGER_HPP
#define LAB3_LOGGER_HPP


#include <mutex>
#include <iostream>
#include <fstream>
#include <shared_mutex>

class Logger {

    std::ostream* out;
    std::mutex logMtx;
    std::ofstream fileStream;
protected:
    // logging metho
public:
    Logger(std::string logFile) : fileStream(logFile), out(&fileStream) {}

    Logger() : out(&std::cout) {}
    void log(std::string text);

};


#endif //LAB3_LOGGER_HPP
