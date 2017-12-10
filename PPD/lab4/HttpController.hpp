//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_HTTPCONTROLLER_HPP
#define LAB4_HTTPCONTROLLER_HPP


#include "IController.hpp"

#include <boost/property_tree/json_parser.hpp>
#include <boost/property_tree/ptree.hpp>

#include <client_http.hpp>

using namespace boost::property_tree;

using HttpClient = SimpleWeb::Client<SimpleWeb::HTTP>;

/**
 * Class that proxies all the controller calls to
 * a server
 */
class HttpController : public IController {
private:
    std::string conn; // connection string
public:
    HttpController(const std::string &conn);

    void addTransaction(std::string name, int id, int quant) override;
};


#endif //LAB4_HTTPCONTROLLER_HPP
