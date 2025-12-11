package model;

import acceptorStrategy.AcceptorStrategy;

public class CoinAcceptor implements AcceptorStrategy {
    private int amount;
    private String name = "Монетами";

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

    @Override
    public String getName() {
        return name;
    }
}
