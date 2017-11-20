//
// Created by nikitautiu on 11/5/17.
//

#ifndef LAB3_LIST_HPP
#define LAB3_LIST_HPP


#include <mutex>
#include <memory>
#include <ostream>
#include <iostream>
#include <fstream>
#include <shared_mutex>
#include "Logger.hpp"

class IIterator {
public:
    // iterator interface
    virtual bool isValid() const = 0;
    virtual void next() = 0;
    virtual float getValue() = 0;
};

class IList : public Logger {
// if specified, log the operations to it
    // mutex for the log operations
public:
    IList() : Logger() {} // initialization should be default
    IList(std::string fileName) : Logger(fileName) {}  // with logging
    // list interface
    void insert(std::pair<std::string, std::string>);
    std::pair<std::string, std::string> remove(std::string);
    virtual std::unique_ptr<IIterator> getIterator() = 0;
private:
    // template method
    virtual void insertImpl(std::pair<std::string, std::string>) = 0;
    virtual std::pair<std::string, std::string> removeImpl(std::string) = 0;
};


#endif //LAB3_LIST_HPP
