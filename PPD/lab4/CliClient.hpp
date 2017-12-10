//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_CLICLIENT_HPP
#define LAB4_CLICLIENT_HPP


#include "IController.hpp"

class CliClient {
private:
    IController* ctrl;
public:
    CliClient(IController& ctrl) : ctrl(&ctrl) {}
    void run();
};


#endif //LAB4_CLICLIENT_HPP
