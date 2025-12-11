package acceptorStrategy;

public class PaymentAcceptor {
    private AcceptorStrategy acceptor;

    public void setStrategy(AcceptorStrategy acceptor) {
        this.acceptor = acceptor;
    }

    public AcceptorStrategy getAcceptor() {
        return acceptor;
    }
}
