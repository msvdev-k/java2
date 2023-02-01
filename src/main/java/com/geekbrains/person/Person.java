package com.geekbrains.person;

public abstract class Person {
    private long cash;

    /**
     * Увеличить cash на заданную величину.
     * @param cash Добавляемая величина.
     */
    public void addCash(long cash) {
        this.cash += cash;
    }

    /**
     * Уменьшить cash на заданную величину.
     * @param cash Вычитаемая величина.
     */
    public void spendCash(long cash) {
        this.cash -= cash;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }
}
