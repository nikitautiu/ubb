//
// Created by nikitautiu on 12/10/17.
//

#include "CliClient.hpp"
#include "HttpController.hpp"

int main(void) {
    auto ctrl = HttpController("localhost:8080");
    auto client = CliClient(ctrl);

    client.run();
}
