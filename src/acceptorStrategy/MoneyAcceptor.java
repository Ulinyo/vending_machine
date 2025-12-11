package acceptorStrategy;

public class MoneyAcceptor implements AcceptorStrategy{
    private int amount;
    private String name  = "Купюрами";

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

    @Override
    public String getName() {
        return name;
    }
}
