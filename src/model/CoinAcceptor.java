package model;

import acceptorStrategy.AcceptorStrategy;

public class CoinAcceptor implements AcceptorStrategy {
    private int amount;

    public CoinAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void pay(int amount) {
        this.amount -= amount;
    }

    @Override
    public void add(int num) {
        amount += num;
    }
}
