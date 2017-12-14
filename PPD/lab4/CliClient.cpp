//
// Created by nikitautiu on 12/10/17.
//

#include <iostream>
#include "CliClient.hpp"

void CliClient::run() {
    // run the controller, basically input what product to buy and how much
    std::cout << "INPUT PRODUCT ID AND QUANTITY:" << std::endl;
    while(true) {
        // just do it till you get a Ctrl+C
        int id, quant;
        std::cin >> id >> quant;
        std::cout << "WAITING - ";
        auto future = ctrl->addTransaction("CLI", id, quant);
        // asteptam degeaba dupa un future
        future.get();
        std::cout << "DONE" << std::endl;
    }
}
