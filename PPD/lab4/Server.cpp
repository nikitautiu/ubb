//
// Created by nikitautiu on 12/10/17.
//

#include <memory>

#include "Server.hpp"
#include <client_http.hpp>
#include <server_http.hpp>

#include <boost/property_tree/json_parser.hpp>
#include <boost/property_tree/ptree.hpp>

using namespace boost::property_tree;


using HttpServer = SimpleWeb::Server<SimpleWeb::HTTP>;

void Server::run() {
    HttpServer server;
    server.config.port = this->port;

    // Create endpoint for transactions
    server.resource["^/transactions$"]["POST"] = [&](std::shared_ptr<HttpServer::Response> response, std::shared_ptr<HttpServer::Request> request) {
        try {
            // parse the json
            ptree pt;
            read_json(request->content, pt);

            auto code = pt.get<int>("code");
            auto quant = pt.get<int>("quantity");

            // add the transaction read from json
            ctrl->addTransaction("SERVER", code, quant);

            response->write(SimpleWeb::StatusCode::success_created);
        }
        catch(const std::exception &e) {
            response->write(SimpleWeb::StatusCode::client_error_bad_request, e.what());
        }
    };

    server.start();
}
