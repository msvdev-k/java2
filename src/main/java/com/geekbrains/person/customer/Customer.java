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

        // Список не купленных товаров
        List<Product> unpurchasedProducts = new ArrayList<>();

        for (Product product : getExpectedPurchaseList()) {

            boolean isBought = false;

            // Ищем товар по всем продавцам на рынке
            for (Seller seller : market.getSellers()) {
                isBought = seller.sellProducts(this, product);
                if (isBought) {
                    break;
                }
            }

            // Если товар не приобретён, то заносим его в список
            if (!isBought) {
                unpurchasedProducts.add(product);
            }
        }

        // В списке желаемых товаров оставляем только те, которые не удалось купить
        expectedPurchaseList = unpurchasedProducts;
    }

    /**
     * Поиск товара по имени и фамилии продавца.
     *
     * @param market Экземпляр класса рынка.
     * @param sellerName Имя продавца.
     * @param sellerLastName Фамилия продавца.
     */
    public void findProductOnMarket(Market market, String sellerName, String sellerLastName) {

        // Список не купленных товаров
        List<Product> unpurchasedProducts = new ArrayList<>();

        // Покупаем товары у заданного продавца
        Seller seller = market.findSeller(sellerName, sellerLastName);

        if (seller != null) {
            for (Product product: getExpectedPurchaseList()) {

                boolean isBought = seller.sellProducts(this, product);

                // Если товар не приобретён, то заносим его в список
                if (!isBought) {
                    unpurchasedProducts.add(product);
                }
            }
            // В списке желаемых товаров оставляем только те, которые не удалось купить
            expectedPurchaseList = unpurchasedProducts;
        }

        // Если в списке покупок остались товары, идём на рынок
        findProductOnMarket(market);
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
