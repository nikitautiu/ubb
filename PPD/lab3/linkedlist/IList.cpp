//
// Created by nikitautiu on 11/5/17.
//

#include <thread>
#include <iomanip>
#include "IList.hpp"

// the logged methods
void IList::insert(float elem) {
    auto started = std::chrono::high_resolution_clock::now();
    insertImpl(elem);
    auto done = std::chrono::high_resolution_clock::now();

    // get the duration
    auto duration = std::chrono::duration_cast<std::chrono::nanoseconds>(done-started).count() / 1000000.;
    log("Inserted " + std::to_string(elem) + " in " + std::to_string(duration) + "ms\n");
}

void IList::remove(float elem) {
    auto started = std::chrono::high_resolution_clock::now();
    removeImpl(elem);
    auto done = std::chrono::high_resolution_clock::now();

    // get the duration
    auto duration = std::chrono::duration_cast<std::chrono::nanoseconds>(done-started).count() / 1000000.;
    log("Removed " + std::to_string(elem) + " in " + std::to_string(duration) + "ms\n");
}

void IList::log(std::string text) {
    auto started = std::chrono::high_resolution_clock::now();
    auto now_c = std::chrono::system_clock::to_time_t(started);

    std::lock_guard<std::mutex> lock(logMtx);  // thread safe logging
    (*out) << std::put_time(std::localtime(&now_c), "%T:")  << " " <<std::this_thread::get_id() << ": " << text; // output to the given file
}



