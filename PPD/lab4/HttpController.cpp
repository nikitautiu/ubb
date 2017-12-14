//
// Created by nikitautiu on 12/10/17.
//

#include <future>
#include "HttpController.hpp"

std::future<void> HttpController::addTransaction(std::string name, int id, int quant) {
    // build the payload
    auto future = std::async(std::launch::async, [&]() {
        HttpClient client(conn); // connect

        auto json_string = "{\"code\":" + std::to_string(id) + ",\"quantity\":" + std::to_string(quant) + "}";
        auto resp = client.request("POST", "/transactions", json_string);
    });

    return future;
}

HttpController::HttpController(const std::string &conn) : conn(conn) {}
