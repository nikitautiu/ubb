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

class IIterator {
public:
    // iterator interface
    virtual bool isValid() const = 0;
    virtual void next() = 0;
    virtual float getValue() = 0;
};

class IList {
private:
    std::ostream* out; // if specified, log the operations to it
    std::mutex logMtx; // mutex for the log operations
    std::ofstream fileStream;
public:
    IList(std::string logFile) : fileStream(logFile), out(&fileStream) {}
    IList() : out(&std::cout) {}

    // list interface
    void insert(float);
    void remove(float);
    virtual std::unique_ptr<IIterator> getIterator() = 0;
private:
    // template method
    virtual void insertImpl(float) = 0;
    virtual void removeImpl(float) = 0;
protected:
    // logging method
    void log(std::string text);
};


#endif //LAB3_LIST_HPP
