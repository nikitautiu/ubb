//
// Created by nikitautiu on 12/10/17.
//

#include <iomanip>
#include "ConcreteController.hpp"

void ConcreteController::addTransaction(std::string name, int id, int quant) {
    pool.enqueue([=]() { this->_addTransaction(name, id, quant); });

}

void ConcreteController::_addTransaction(std::string name, int id, int quant) {
    std::lock_guard <std::mutex> lock(mtx);

    std::size_t max_ind = 0;
    for(const auto& purch : purchases)
        max_ind = std::max(max_ind, purch.getCode()); // get max_code
    auto curr_code = max_ind + 1;

    // emplace sell
    purchases.emplace_back(curr_code, id, quant);

    // emplace invoice
    auto prod = *std::find_if(std::begin(products), std::end(products),
                              [=](const Product& prod) { return prod.getCode() == id; });
    invoices.emplace_back(name, curr_code, prod.getPrice() * quant); // add the total

    // update total
    total += prod.getPrice() * quant;

    // update stocks
    auto& stock = *std::find_if(std::begin(stocks), std::end(stocks),
                 [=](const Stock& stock) { return stock.getProdCode() == id; });
    stock.setRemaining(stock.getRemaining() - quant);

    // the lock will now be released
}

void ConcreteController::logAll() {
    std::lock_guard <std::mutex> lock(mtx);

    auto started = std::chrono::_V2::system_clock::now();
    auto now_c = std::chrono::_V2::system_clock::to_time_t(started);
    log << std::put_time(localtime(&now_c), "%T:") << std::endl;
    log << "STOCURI:" << std::endl;
    for(const auto& stock : stocks) log << stock << std::endl;

    log << "VANZARI:" << std::endl;
    for(const auto& purch : purchases) log << purch << std::endl;

    log << "FACTURI:" << std::endl;
    for(const auto& inv : invoices) log << inv << std::endl;

    log << "TOTAL: " << total << std::endl;
    log << std::endl;
}
