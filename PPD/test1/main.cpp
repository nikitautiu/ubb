#include <iostream>
#include <thread>
#include <vector>
#include <atomic>
#include "linkedlist/FineList.hpp"

// for millisecond literals
using namespace std::chrono_literals;


void test(IList& list, std::string logfile) {
    auto logger = Logger(logfile);
    // start the threads
    auto thVect = std::vector<std::thread>();
    // first one inserts 10 values
    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 5; ++i) {
            list.insert(std::make_pair<std::string, std::string>("A", std::to_string(i * 3)));
            logger.log(std::string("PROD1: INSERTED ") + "A, " + std::to_string(i*3));
            std::this_thread::sleep_for(2ms);
        }
        for(auto i = 1; i <= 10; ++i) {
            list.insert(std::make_pair<std::string, std::string>("B", std::to_string(i * 2)));
            logger.log(std::string("PROD1: INSERTED ") + "B, " + std::to_string(i*2));
            std::this_thread::sleep_for(2ms);
        }
        for(auto i = 1; i <= 20; ++i) {
            list.insert(std::make_pair<std::string, std::string>("A", std::to_string(i * 5)));
            logger.log(std::string("PROD1: INSERTED ") + "A, " + std::to_string(i*5));
            std::this_thread::sleep_for(2ms);
        }
    });

    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 10; ++i) {
            list.insert(std::make_pair<std::string, std::string>("B", std::to_string(i * 3)));
            logger.log(std::string("PROD2: INSERTED ") + "B, " + std::to_string(i*3));
            std::this_thread::sleep_for(2ms);
        }
        for(auto i = 1; i <= 2; ++i) {
            list.insert(std::make_pair<std::string, std::string>("A", std::to_string(i * 2)));
            logger.log(std::string("PROD2: INSERTED ") + "A, " + std::to_string(i*2));
            std::this_thread::sleep_for(2ms);
        }
        for(auto i = 1; i <= 5; ++i) {
            list.insert(std::make_pair<std::string, std::string>("B", std::to_string(i * 5)));
            logger.log(std::string("PROD2: INSERTED ") + "B, " + std::to_string(i*5));
            std::this_thread::sleep_for(2ms);
        }
        for(auto i = 1; i <= 10; ++i) {
            list.insert(std::make_pair<std::string, std::string>("A", std::to_string(i * 3)));
            logger.log(std::string("PROD2: INSERTED ") + "A, " + std::to_string(i*3));
            std::this_thread::sleep_for(2ms);
        }
    });

    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 100; ++i) {
            auto res = list.remove("A");
            if(res.first != "")
                logger.log(std::string("CONS1: CONSUMED ") + res.first + ", " + res.second);
            std::this_thread::sleep_for(5ms);
        }
    });
    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 100; ++i) {
            auto res = list.remove("B");
            if(res.first != "")
                logger.log(std::string("CONS2: CONSUMED ") + res.first + ", " + res.second);
            std::this_thread::sleep_for(5ms);
        }
    });
    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 100; ++i) {
            auto res = list.remove("A");
            if(res.first != "")
                logger.log(std::string("CONS3: CONSUMED ") + res.first + ", " + res.second);
            std::this_thread::sleep_for(3ms);
        }
    });
    thVect.emplace_back([&]() {
        for(auto i = 1; i <= 100; ++i) {
            auto res = list.remove("B");
            if(res.first != "")
                logger.log(std::string("CONS4: CONSUMED ") + res.first + ", " + res.second);
            std::this_thread::sleep_for(3ms);
        }
    });



    // wait on all threads
    for(auto& th : thVect) th.join();
}


int main() {
    auto list = FineList("fine.log");
    test(list, "tranzactii.log");
}