package com.geekbrains.person.customer;

import com.geekbrains.market.Market;
import com.geekbrains.person.Person;
import com.geekbrains.person.seller.Seller;
import com.geekbrains.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Product> expectedPurchaseList;
    private List<Product> purchaseList;

    public Customer(List<Product> expectedPurchaseList, int cash) {
        this.purchaseList = new ArrayList<>();
        this.expectedPurchaseList = expectedPurchaseList;
        this.setCash(cash);
    }

    public void addPurchase(Product product) {
        if (purchaseList == null) {
            purchaseList = new ArrayList<>();
        }

        purchaseList.add(product);
    }

    public void findProductOnMarket(Market market) {
        for (Product product : getExpectedPurchaseList()) {
            for (Seller seller : market.getSellers()) {
                boolean isBought = seller.sellProducts(this, product);
                if (isBought) {
                    break;
                }
            }
        }
    }

    /**
     * Поиск товара по имени и фамилии продавца.
     *
     * @param market Экземпляр класса рынка.
     * @param sellerName Имя продавца.
     * @param sellerLastName Фамилия продавца.
     */
    public void findProductOnMarket(Market market, String sellerName, String sellerLastName) {

        // Индикатор показывающий, что у одного продавца куплены все продукты
        boolean isBoughtAllProducts = true;

        // Покупаем товары у заданного продавца
        for (Seller seller: market.getSellers()) {

            if (seller.getName().equals(sellerName) &&
                seller.getLastName().equals(sellerLastName)) {

                for (Product product: getExpectedPurchaseList()) {
                    isBoughtAllProducts = seller.sellProducts(this, product) && isBoughtAllProducts;
                }

                break;
            }
        }

        // Если какие-либо товары не нашлись, то ищем их по всему рынку
        if (isBoughtAllProducts) {
            System.out.println("Все товары куплены у " + sellerName + " " + sellerLastName);
        }
        else {
            findProductOnMarket(market);
        }
    }

    public void info() {
        StringBuilder result = new StringBuilder("Я купил ");
        if (purchaseList.size() == 0) {
            result.append("ничего");
        } else {
            for(Product product: purchaseList) {
                result.append(product.getName());
                result.append(" в количестве ");
                result.append(product.getQuantity());
                result.append(" ");
            }
        }

        result.append(". У меня осталось: ");
        result.append(getCash());
        result.append(" рублей");

        System.out.println(result);
    }

    public List<Product> getExpectedPurchaseList() {
        return expectedPurchaseList;
    }

    public List<Product> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Product> purchaseList) {
        this.purchaseList = purchaseList;
    }

}
