//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_ICONTROLLER_HPP
#define LAB4_ICONTROLLER_HPP

#include <string>

class IController {
public:
    virtual void addTransaction(std::string name, int id, int quant) = 0;
};


#endif //LAB4_ICONTROLLER_HPP
