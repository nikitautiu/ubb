//
// Created by nikitautiu on 11/20/17.
//

#include "IList.hpp"
#include <iomanip>
#include <thread>
#include "Logger.hpp"

void Logger::log(std::string text) {
    auto started = std::chrono::_V2::system_clock::now();
    auto now_c = std::chrono::_V2::system_clock::to_time_t(started);

    std::lock_guard<std::mutex> lock(logMtx);  // thread safe logging
    (*out) << std::put_time(localtime(&now_c), "%T:") << " " << std::this_thread::get_id() << ": " << text << '\n'; // output to the given file
}