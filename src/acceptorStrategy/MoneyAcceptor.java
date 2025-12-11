package acceptorStrategy;

public class MoneyAcceptor implements AcceptorStrategy{
    private int amount;

    public MoneyAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void pay(int priceProduct) {
        this.amount -= priceProduct;
    }

    @Override
    public void add(int num) {
        this.amount += num;
    }
}
