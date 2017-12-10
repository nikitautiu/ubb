//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_SERVER_HPP
#define LAB4_SERVER_HPP


#include "IController.hpp"

class Server {
private:
    IController* ctrl;
public:
    Server(IController &ctrl, unsigned short port) : ctrl(&ctrl), port(port) {}
    void run();

    unsigned short port;
};


#endif //LAB4_SERVER_HPP
