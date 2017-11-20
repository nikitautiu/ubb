//
// Created by nikitautiu on 11/5/17.
//

#include <thread>
#include <iomanip>
#include "IList.hpp"

// the logged methods
void IList::insert(std::pair<std::string, std::string> elem) {
    insertImpl(elem);

    // get the duration
    Logger::log("Inserted " +  elem.first + ", " + elem.second);
}

std::pair<std::string, std::string> IList::remove(std::string elem) {
    auto res = removeImpl(elem);

    // get the duration
    Logger::log("Removed " + res.first + ", " + res.second);

    return res;
}



