package com.geekbrains.product;

public class Product {
    private String name;
    private int price;
    private int quantity;

    /**
     * Копирование объекта.
     * @return Точная копия текущего объекта
     */
    @Override
    public Product clone() {
        Product product = new Product();

        product.name = name;
        product.price = price;
        product.quantity = quantity;

        return product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
