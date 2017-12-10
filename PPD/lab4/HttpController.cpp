//
// Created by nikitautiu on 12/10/17.
//

#include "HttpController.hpp"

void HttpController::addTransaction(std::string name, int id, int quant) {
    HttpClient client(conn); // connect
    // build the payload
    std::string json_string = "{\"code\":" + std::to_string(id) + ",\"quantity\":" + std::to_string(quant) + "}";
    auto resp = client.request("POST", "/transactions", json_string);
}

HttpController::HttpController(const std::string &conn) : conn(conn) {}
