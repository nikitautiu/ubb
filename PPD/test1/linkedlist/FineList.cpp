//
// Created by nikitautiu on 11/6/17.
//

#include "FineList.hpp"
#include <string>

void FineList::insertImpl(std::pair<std::string, std::string> newElem) {
    std::shared_lock<std::shared_mutex> lock(mtx); // shared lock for anything but iteration
    FineNode *last = nullptr, *curr = root.get(); // the previous and current nodes

    root_mtx.lock();
    while (curr != nullptr) {
        if (curr->getNextNode() != nullptr)
            curr->getNextNode()->mtx.lock(); // lock the next one

        auto lastTmp = last; // befor shifting the lock
        last = curr; // shift the last
        curr = curr->getNextNode();  // iterate over
        if(lastTmp == nullptr)
            root_mtx.unlock();
        else
            lastTmp->mtx.unlock();
    }

    if (last == nullptr) {
        // we are inserting the root
        root = std::make_unique<FineNode>(newElem, std::move(root));
    } else {
        // insert inbetween elements
        auto newNode = std::make_unique<FineNode>(newElem);
        newNode->setNextNode(std::move(last->getNextPtr()));
        last->setNextNode(std::move(newNode));
    }

    // unlock remaining locks
    if(last != nullptr)
        last->mtx.unlock();
    if(last == nullptr);
        root_mtx.unlock();
}

std::pair<std::string, std::string> FineList::removeImpl(std::string cat) {
    std::shared_lock<std::shared_mutex> lock(mtx); // lock the entire list for writing
    auto found_val = std::pair<std::string,std::string>("", "");

    root_mtx.lock();
    FineNode *last = nullptr, *curr = root.get(); // the previous and current nodes
    while(curr != nullptr && curr->getValue().first != cat) {
        if (curr->getNextNode() != nullptr)
            curr->getNextNode()->mtx.lock(); // lock the next one

        auto lastTmp = last; // before shifting the lock
        last = curr; // shift the last
        curr = curr->getNextNode();  // iterate over
        if(lastTmp == nullptr)
            root_mtx.unlock();
        else
            lastTmp->mtx.unlock();
    }
    if(curr != nullptr) {
        if(curr->getValue().first == cat)
            found_val = curr->getValue();
        // the end of the list has not been reached
        if(last != nullptr)
            // or the beginning
            last->setNextNode(std::move(curr->getNextPtr()));
        else
            root = std::move(curr->getNextPtr());
    }

    // unlock remaining locks
    if(curr != nullptr)
        curr->mtx.unlock();
    if(last != nullptr)
        last->mtx.unlock();
    if(last == nullptr);
        root_mtx.unlock();

    return found_val;
}

std::unique_ptr<IIterator> FineList::getIterator() {
    return std::unique_ptr < IIterator > (new FineIterator(*this));
}

FineIterator::FineIterator(FineList &list) : currNode(list.root.get()), list(&list) {
    list.mtx.lock(); // exclusive lock
}

bool FineIterator::isValid() const {
    return currNode != nullptr;
}

float FineIterator::getValue() {
    auto val = currNode->getValue();
}

void FineIterator::next() {
    currNode = currNode->getNextNode(); // advance the iterator
    if(!isValid())
        list->mtx.unlock(); // release read lock upon completion

}
