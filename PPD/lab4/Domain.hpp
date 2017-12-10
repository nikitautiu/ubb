//
// Created by nikitautiu on 12/10/17.
//

#ifndef LAB4_DOMAIN_HPP
#define LAB4_DOMAIN_HPP

#include <string>
#include <ostream>

class Product {
public:
    Product(const std::string &name, size_t code, float price, std::size_t stock)
            : name(name), code(code), price(price), stock(stock) {}
    Product(const Product&) = default;

    const std::string &getName() const {
        return name;
    }

    void setName(const std::string &name) {
        Product::name = name;
    }

    size_t getCode() const {
        return code;
    }

    void setCode(size_t code) {
        Product::code = code;
    }

    float getPrice() const {
        return price;
    }

    void setPrice(float price) {
        Product::price = price;
    }

    size_t getStock() const {
        return stock;
    }

    void setStock(size_t stock) {
        Product::stock = stock;
    }

    friend std::ostream &operator<<(std::ostream &os, const Product &product);

private:
    std::string name;
    std::size_t code;
    float price;
    std::size_t stock;
};


class Purchase {
public:
    Purchase(size_t code, size_t prodCode, size_t quant) : code(code), prodCode(prodCode), quant(quant) {}

    Purchase(const Purchase&) = default;

    size_t getCode() const {
        return code;
    }

    void setCode(size_t code) {
        Purchase::code = code;
    }

    size_t getProdCode() const {
        return prodCode;
    }

    void setProdCode(size_t prodCode) {
        Purchase::prodCode = prodCode;
    }

    size_t getQuant() const {
        return quant;
    }

    void setQuant(size_t quant) {
        Purchase::quant = quant;
    }

    friend std::ostream &operator<<(std::ostream &os, const Purchase &purchase) {
        os << "code: " << purchase.code << " prodCode: " << purchase.prodCode << " quant: " << purchase.quant;
        return os;
    }

private:
    std::size_t code;
    std::size_t prodCode;
    std::size_t quant;
};


class Invoice {
public:
    Invoice(const std::string &name, size_t purchCode, float total) : name(name), purchCode(purchCode), total(total) {}
    Invoice(const Invoice&) = default;

    const std::string &getName() const {
        return name;
    }

    void setName(const std::string &name) {
        Invoice::name = name;
    }

    size_t getPurchCode() const {
        return purchCode;
    }

    void setPurchCode(size_t purchCode) {
        Invoice::purchCode = purchCode;
    }

    float getTotal() const {
        return total;
    }

    void setTotal(float total) {
        Invoice::total = total;
    }

    friend std::ostream &operator<<(std::ostream &os, const Invoice &invoice) {
        os << "name: " << invoice.name << " purchCode: " << invoice.purchCode << " total: " << invoice.total;
        return os;
    }

private:
    std::string name;
    std::size_t purchCode;
    float total;
};

class Stock {
public:
    Stock(size_t prodCode, size_t remaining) : prodCode(prodCode), remaining(remaining) {}

    size_t getProdCode() const {
        return prodCode;
    }

    void setProdCode(size_t prodCode) {
        Stock::prodCode = prodCode;
    }

    size_t getRemaining() const {
        return remaining;
    }

    void setRemaining(size_t remaining) {
        Stock::remaining = remaining;
    }

    friend std::ostream &operator<<(std::ostream &os, const Stock &stock) {
        os << "prodCode: " << stock.prodCode << " remaining: " << stock.remaining;
        return os;
    }

private:
    std::size_t prodCode;
    std::size_t remaining;
};
#endif //LAB4_DOMAIN_HPP
