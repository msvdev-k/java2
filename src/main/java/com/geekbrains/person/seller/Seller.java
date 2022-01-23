package com.geekbrains.person.seller;

import com.geekbrains.person.Person;
import com.geekbrains.person.customer.Customer;
import com.geekbrains.product.Product;

import java.util.List;

public class Seller extends Person {
    private String name;
    private String lastName;
    private List<Product> products;

    public boolean sellProducts(Customer customer, Product expectedProduct) {

        // Поиск нужного товара
        Product product = findProduct(expectedProduct);

        // Проверяем, что продукт найден и его количество >= чем мы хотим купить
        if (product != null &&
            product.getQuantity() >= expectedProduct.getQuantity()) {

            // Проверяем что кэш покупателя позволяет купить товар
            long requiredCash = (long) product.getPrice() * expectedProduct.getQuantity();
            if (customer.getCash() >= requiredCash) {

                // Уменьшаем количество продукта у продавца
                product.setQuantity(product.getQuantity() - expectedProduct.getQuantity());

                // Создаем новый объект для покупателя, чтобы ссылка не дублировалась
                Product customerProduct = expectedProduct.clone();
                // Добавляем количество продуктов у покупателя
                customer.addPurchase(customerProduct);

                // Увеличиваем кэш продавца
                addCash(requiredCash);
                // Уменьшаем кэш покупателя
                customer.spendCash(requiredCash);

                // Сообщаем потребителю метода, что покупка совершена
                return true;
            }
        }

        return false;
    }

    /**
     * Поиск товара у продавца.
     * @param product Искомый товар.
     * @return Найденный товар, в противном случае null.
     */
    public Product findProduct(Product product) {

        for (Product prod: products) {
            // Проверяем по имени товара, что у продавца есть продукт
            if (prod.getName().equals(product.getName())) {
                return prod;
            }
        }

        return null;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
