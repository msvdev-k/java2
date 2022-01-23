package com.geekbrains.market;

import com.geekbrains.person.seller.Seller;

import java.util.ArrayList;
import java.util.List;

public class Market {
    private List<Seller> sellers;

    /**
     * Список продавцов.
     */
    public List<Seller> getSellers() {
        return sellers;
    }


    /**
     * Найти продавца по имени и фамилии.
     * @param sellerName Имя продавца.
     * @param sellerLastName Фамилия продавца.
     * @return
     */
    public Seller findSeller(String sellerName, String sellerLastName) {

        for (Seller seller: sellers) {

            if (seller.getName().equals(sellerName) &&
                seller.getLastName().equals(sellerLastName)) {

                return seller;
            }
        }
        return null;
    }


    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    public void addSeller(Seller seller) {
        if(sellers == null) {
            sellers = new ArrayList<>();
        }

        sellers.add(seller);
    }
}
