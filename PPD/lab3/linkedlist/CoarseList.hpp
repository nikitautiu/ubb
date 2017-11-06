//
// Created by nikitautiu on 11/6/17.
//

#ifndef LAB3_COARSELIST_HPP
#define LAB3_COARSELIST_HPP


#include "IList.hpp"

class CoarseList : public IList {
private:
    // node class
    class CoarseNode {
    private:
        float value;
        std::unique_ptr<CoarseNode> nextNode;
    public:
        CoarseNode(float value, std::unique_ptr<CoarseNode> next) : value(value), nextNode(std::move(next)) {}

        CoarseNode(float value) : value(value), nextNode(nullptr) {}

        CoarseNode* getNextNode() const {
            return nextNode.get();
        }

        std::unique_ptr<CoarseNode> getNextPtr() {
            return std::move(nextNode);
        };

        void setNextNode(std::unique_ptr<CoarseNode> newNext) {
            nextNode = std::move(newNext);  // add the new next
        }

        float getValue() const {
            return value;
        }
    };

private:
    std::unique_ptr<CoarseNode> root;
    std::shared_mutex mtx; // instance lock. read/write lock
    friend class CoarseIterator;
public:
    CoarseList() : IList() {} // initialization should be default
    CoarseList(std::string fileName) : IList(fileName) {}  // with logging
    std::unique_ptr<IIterator> getIterator() override;
private:
    // the hooks
    void insertImpl(float newElem) override;  // add new element
    void removeImpl(float elem) override;  // remove the said element
};

class CoarseIterator : public IIterator {
private:
    CoarseList::CoarseNode* currNode;
    CoarseList* list;
public:
    CoarseIterator(CoarseList& list);
    bool isValid() const override;
    void next() override;
    float getValue() override;
};

#include "IList.hpp"

#endif //LAB3_COARSELIST_HPP
