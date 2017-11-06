//
// Created by nikitautiu on 11/6/17.
//

#include <mutex>
#include <memory>
#include <bits/unique_ptr.h>
#include "CoarseList.hpp"

void CoarseList::insertImpl(float newElem) {
    std::unique_lock<std::shared_mutex> lock(mtx); // lock the for writing

    CoarseNode *last = nullptr, *curr = root.get(); // the previous and current nodes
    while(curr != nullptr && curr->getValue() < newElem) {
        last = curr; // shift the last
        curr = curr->getNextNode();  // iterate over;
    }

    if(last == nullptr) {
        // we are inserting the root
        root = std::make_unique<CoarseNode>(newElem, std::move(root));
    }
    else {
        // insert inbetween elements
        auto newNode = std::make_unique<CoarseNode>(newElem);
        newNode->setNextNode(std::move(last->getNextPtr()));
        last->setNextNode(std::move(newNode));
    }
}

void CoarseList::removeImpl(float elem) {
    std::unique_lock<std::shared_mutex> lock(mtx); // lock the entire list for writing

    CoarseNode *last = nullptr, *curr = root.get(); // the previous and current nodes
    while(curr != nullptr && curr->getValue() != elem) {
        last = curr; // shift the last
        curr = curr->getNextNode();  // iterate over;
    }
    if(curr != nullptr) {
        // the end of the list has not been reached
        if(last != nullptr)
            // ot hte beginning
            last->setNextNode(std::move(curr->getNextPtr()));
        else
            root = std::move(curr->getNextPtr());
    }
}

std::unique_ptr<IIterator> CoarseList::getIterator() {
    return std::unique_ptr < IIterator > (new CoarseIterator(*this));
}

CoarseIterator::CoarseIterator(CoarseList &list) : currNode(list.root.get()), list(&list) {
    list.mtx.lock_shared(); // read lock
}

bool CoarseIterator::isValid() const {
    return currNode != nullptr;
}

float CoarseIterator::getValue() {
    auto val = currNode->getValue();
    list->log("Accessed " + std::to_string(val) + "\n");
}

void CoarseIterator::next() {
    currNode = currNode->getNextNode(); // advance the iterator
    if(!isValid())
        list->mtx.unlock_shared(); // release read lock upon completion

}