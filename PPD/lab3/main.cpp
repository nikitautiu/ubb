#include <iostream>
#include <thread>
#include <vector>
#include <atomic>
#include "linkedlist/CoarseList.hpp"
#include "linkedlist/FineList.hpp"

// for millisecond literals
using namespace std::chrono_literals;


void test1(IList& list) {
    // runs tes1 on list object
    // conditional variable to notify the last one
    auto finishCounter = 0; // how any threads finished, no need to lock this
    std::condition_variable cv;
    std::mutex cvMtx;

    // measure execution time
    auto started = std::chrono::high_resolution_clock::now();

    // start the threads
    auto thVect = std::vector<std::thread>();
    // first one inserts 10 values
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 10; ++i)
            list.insert(2);
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });


    // second one adds 5
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 5; ++i)
            list.insert(3);
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });

    // third one deletes 7 values
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 5; ++i) {
            list.remove(2);
        }
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });

//     fourth one iterates every so often and waits for the others to finish
    thVect.emplace_back([&]() {
        auto ok = false;
        while(!ok) {
            {
                auto lock = std::unique_lock<std::mutex>(cvMtx); // lock the mutex
                ok = cv.wait_for(lock, 3ms, [&]{return finishCounter == 3;}); // wait for all to finish
            }
            for(auto it = list.getIterator(); it->isValid(); it->next())
                it->getValue();
        }
    });

    thVect.emplace_back([&]() {
        auto ok = false;
        while(!ok) {
            {
                std::unique_lock<std::mutex> lock(cvMtx); // lock the mutex
                ok = cv.wait_for(lock, 1ms, [&]{return finishCounter == 3;}); // wait for all to finish
            }
            for(auto it = list.getIterator(); it->isValid(); it->next())
                it->getValue();
        }
    });

    // wait on all threads
    for(auto& th : thVect) th.join();

    // get the duration in milliseconds
    auto done = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::nanoseconds>(done-started).count() / 1000000.;
    std::cout << "Test1 executed in " << duration << "ms\n";
}


void test2(IList& list) {
    // runs test2 on list object
    // conditional variable to notify the last one
    auto finishCounter = 0; // how any threads finished, no need to lock this
    std::condition_variable cv;
    std::mutex cvMtx;

    // measure execution time
    auto started = std::chrono::high_resolution_clock::now();

    // start the threads
    auto thVect = std::vector<std::thread>();
    // first one inserts 100 values
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 100; ++i) {
            list.insert(2);
        }
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });

    // second one adds 50
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 50; ++i) {
            list.insert(3);

        }
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });

    // third one deletes 50 values
    thVect.emplace_back([&]() {
        for(auto i = 0; i < 50; ++i) {
            list.remove(2 + (i % 3) / 2);
        }
        {
            // increment the counter
            auto lock = std::unique_lock<std::mutex>(cvMtx);
            ++finishCounter;
        }
        cv.notify_all();  // increase counter, notify all
    });

    // fourth one iterates every so often and waits for the others to finish
    thVect.emplace_back([&]() {
        auto ok = false;
        while(!ok) {
            {
                std::unique_lock<std::mutex> lock(cvMtx); // lock the mutex
                ok = cv.wait_for(lock, 2ms, [&]{return finishCounter == 3;}); // wait for all to finish
            }
            for(auto it = list.getIterator(); it->isValid(); it->next())
                it->getValue();
        }
    });

    thVect.emplace_back([&]() {
        auto ok = false;
        while(!ok) {
            {
                std::unique_lock<std::mutex> lock(cvMtx); // lock the mutex
                ok = cv.wait_for(lock, 2ms, [&]{return finishCounter == 3;}); // wait for all to finish
            }
            for(auto it = list.getIterator(); it->isValid(); it->next())
                it->getValue();
        }
    });


    // wait on all threads
    for(auto& th : thVect) th.join();

    // get the duration in milliseconds
    auto done = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::nanoseconds>(done-started).count() / 1000000.;
    std::cout << "Test2 executed in " << duration << "ms\n";
}


int main() {
    auto list1 = CoarseList("coarse1.log");
    auto list2 = FineList("fine1.log");
    test1(list1);
    test1(list2);

    std::cout << "\nBENCHMARK COARSE\n";
    for(auto i = 0; i < 10; ++i) {
        auto list = CoarseList("coarse2.log");
        test2(list);
    }

    std::cout << "\nBENCHMARK FINE\n";
    for(auto i = 0; i < 10; ++i) {
        auto list = FineList("fine2.log");
        test2(list);
    }
}