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

    private int money = 250;
    private int coint = 100;

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
        System.out.println(money);
        System.out.println(coint);
        System.out.println(strategy.getName());
        print("У вас есть " + strategy.getAcceptor().getAmount() + " рублей " + strategy.getAcceptor().getName());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());

        chooseAction(allowProducts);
        syncingMoney(strategy.getAcceptor().getAmount());
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
                strategy.setStrategy(new MoneyAcceptor(money));
                strategy.setName("money");
                break;
            case 2:
            default:
                strategy.setStrategy(new CoinAcceptor(coint));
                strategy.setName("coint");
                break;
        }
    }

    private int inputPayment() {
        while (true) {
            try{
                print("Выберите способ оплаты:\n" +
                        "\t1 - купюрами\n" +
                        "\t2 - монетами\n");
                int num = Integer.parseInt(fromConsole().strip());
                if (num < 1 || num > 2) {
                    throw new IndetifacalNumber("Такого способа оплаты нет. Попробуйте еще раз: ");
                }
                return num;
            } catch (NumberFormatException nfe) {
                print("Введите число указывающий на способ оплаты.");
            } catch (IndetifacalNumber iu) {
                print(iu.getMessage());
            }
        }
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - пополнить");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if (action.equalsIgnoreCase("a")) {
            strategy.getAcceptor().add(10);
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

    private void syncingMoney(int sum) {
        try {
            switch (strategy.getName()) {
                case "money":
                    money = sum;
                    break;
                case "coint":
                    coint = sum;
                    break;
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Случилась ошибка с выбором способа оплаты");
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
