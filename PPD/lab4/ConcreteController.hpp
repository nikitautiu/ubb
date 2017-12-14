//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_CONTROLLER_HPP
#define LAB4_CONTROLLER_HPP


#include <cstddef>
#include <fstream>
#include "ThreadPool/ThreadPool.hpp"
#include "Domain.hpp"
#include "IController.hpp"

using namespace std::chrono_literals;


/**
 * Class that operates on on the data structures directly
 * Thread safe
 */
class ConcreteController : public IController {

private:
    std::mutex mtx;
    ThreadPool pool;
    std::ofstream log;

    // relevant info
    std::vector <Product> products;
    std::vector <Purchase> purchases;

    // inferred info
    std::vector <Invoice> invoices;
    std::vector <Stock> stocks;
    float total;
public:
    void logAll();

    ConcreteController(std::size_t size, std::vector<Product> prods, std::string logfile)
            : pool(size), products(prods), log(logfile), purchases(), invoices(), stocks(), total(0) {
        // init stocks
        for(const auto& prod : products)
            stocks.emplace_back(prod.getCode(), prod.getStock());

        // start the logging thread
        loggerThread = std::thread([=]() {
            while(true) {
                logAll();
                std::this_thread::sleep_for(2000ms);
            }
        });
    }
    std::future<void> addTransaction(std::string name, int id, int quant) override; // add the transaction
private:
    void _addTransaction(std::string name, int id, int quant);
    std::thread loggerThread;
};


#endif //LAB4_CONTROLLER_HPP
