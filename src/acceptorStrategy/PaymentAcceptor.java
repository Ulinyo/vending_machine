package acceptorStrategy;

public class PaymentAcceptor {
    private AcceptorStrategy acceptor;
    private String name;

    public void setStrategy(AcceptorStrategy acceptor) {
        this.acceptor = acceptor;
    }

    public AcceptorStrategy getAcceptor() {
        return acceptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
