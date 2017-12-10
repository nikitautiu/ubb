#include "ConcreteController.hpp"
#include "CliClient.hpp"
#include "Server.hpp"

int main(void) {
    auto products = std::vector<Product> ({Product("aa", 1, 10.5, 100),
                                           Product("bb", 2, 5.6, 200),
                                           Product("cc", 3, 5.1, 40)});
    auto localCtrl = ConcreteController(4, products, "log.log");
    auto cliClient = CliClient(localCtrl);
    auto autoClient = std::thread([&]() {
        while(true) {
            localCtrl.addTransaction("AUTO", 1, 2);
            std::this_thread::sleep_for(5000ms);
            localCtrl.addTransaction("AUTO", 2, 1);
            std::this_thread::sleep_for(5000ms);
        }
    });
    auto serverThread = std::thread([&]() {
        auto srv = Server(localCtrl, 8080);
        srv.run();
    });

    // run the client in the main thread
    cliClient.run();
    return 0;
}