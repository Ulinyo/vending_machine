import acceptorStrategy.AcceptorStrategy;
import acceptorStrategy.MoneyAcceptor;
import acceptorStrategy.PaymentAcceptor;
import enums.ActionLetter;
import exceptions.IndetifacalNumber;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final PaymentAcceptor strategy = new PaymentAcceptor();

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }



    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        choosePayment();
        print("Монет на сумму: " + strategy.getAcceptor().getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());

        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (strategy.getAcceptor().getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void choosePayment() {
        int num = inputPayment();
        switch (num) {
            case 1:
                strategy.setStrategy(new MoneyAcceptor(250));
                break;
            case 2:
            default:
                strategy.setStrategy(new CoinAcceptor(100));
                break;
        }
    }

    private int inputPayment() {
        while (true) {
            try{
                System.out.println("Выберите способ оплаты:\n" +
                        "\t1 - купюрами\n" +
                        "\t2 - монетами\n");
                int num = Integer.parseInt(fromConsole().strip());
                if (num < 1 || num > 2) {
                    throw new IndetifacalNumber("Такого способа оплаты нет. Попробуйте еще раз: ");
                }
                return num;
            } catch (NumberFormatException nfe) {
                System.out.println("Введите число указывающий на способ оплаты.");
            } catch (IndetifacalNumber iu) {
                System.out.println(iu.getMessage());
            }
        }
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - пополнить");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if (action.equalsIgnoreCase("a")) {
            strategy.getAcceptor().add(strategy.getAcceptor().getAmount() + 10);
            System.out.println("Вы пополнили баланс на 10");
            return;
        } else if (action.equalsIgnoreCase("h")) {
            isExit = true;
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    strategy.getAcceptor().pay(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                } else if ("h".equalsIgnoreCase(action)) {
                    isExit = true;
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попрбуйте еще раз.");
            chooseAction(products);
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
