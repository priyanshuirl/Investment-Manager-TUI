import java.util.Scanner;

public class EPortfolio {
    private static PortfolioManager portfolio;
    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        portfolio = new PortfolioManager();
        System.out.println("\nWelcome to Your ePortfolio\n");
        int iteration = 1;
        while (iteration == 1) {
            System.out.println("Please Choose from the Available options\n");
            System.out.println("(1) Buy: own a new investment or add more quantity to an existing investment. ");
            System.out.println("(2) Sell: reduce some quantity of an existing investment. ");
            System.out.println("(3) Update: refresh the prices of Your existing investments.");
            System.out.println(
                    "(4) Get Gain: compute the total gain of the portfolio by accumulating the gains of all individual investments. ");
            System.out.println(
                    "(5) Search: find all investments that match a search request and display all attributes of these investments ");
            System.out.println("(6) Quit");
            System.out.println("\nPlease Enter the Number corresponding to your choice : ");
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
            case 1:
                System.out.println("\nChoose the Type of Investment You wanna Buy");
                System.out.println("(1)Stocks");
                System.out.println("(2)Mutual Funds");
                System.out.println("\nEnter Your Choice : ");
                int invtype = scan.nextInt();
                if (invtype == 1) {
                    System.out.println("\nEnter the Symbol for the Stock You wish to Buy");
                    scan.skip("\\R?");
                    String name = scan.nextLine();
                    Stock st = portfolio.checkStock(name);
                    if (st != null) {
                        addstock(st);
                    } else {
                        buyStock(name);
                    }

                } else if (invtype == 2) {
                    System.out.println("\nEnter the Symbol for the Mutual Fund You wish to Buy");
                    scan.skip("\\R?");
                    String name = scan.nextLine();
                    MutualFund mutualFund = portfolio.checkMutalFund(name);
                    if (mutualFund != null) {
                        addMutualFunds(mutualFund);
                    } else {
                        buyMutualFund(name);
                    }
                } else {
                    System.out.println("Please enter 1 or 2");
                }
                if (portfolio.getStocks() != null) {
                    System.out.println("\nYour stock Portfolio");
                    for (Stock st : portfolio.getStocks()) {
                        System.out.println(st.toString() + "\n");
                    }
                }
    
                if (portfolio.getMutualFunds() != null) {
                    System.out.println("\nYour Mutual Fund Portfolio");
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;
            case 2:
                System.out.println("Enter The symbol : ");
                String symbol = scan.nextLine();

                Stock st = portfolio.checkStock(symbol);
                if (st != null) {
                    sellStock(st);
                } else {
                    MutualFund mutualFund = portfolio.checkMutalFund(symbol);
                    if (mutualFund != null) {
                        sellMutualFund(mutualFund);
                    } else {
                        System.out.println("No such investment found in your Portfolio.");
                    }
                }
                if (portfolio.getStocks() != null) {
                    System.out.println("\nYour stock Portfolio");
                    for (Stock s1t : portfolio.getStocks()) {
                        System.out.println(s1t.toString() + "\n");
                    }
                }
    
                if (portfolio.getMutualFunds() != null) {
                    System.out.println("\nYour Mutual Fund Portfolio");
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;
            case 3:
                if (portfolio.getStocks() != null) {
                    for (Stock stt : portfolio.getStocks()) {
                        System.out.println(stt.toString());
                        System.out.println("Enter the new price ");
                        double price = scan.nextDouble();
                        stt.setOldPrice(stt.getPrice());
                        stt.setPrice(price);
                    }
                }

                if (portfolio.getMutualFunds() != null) {
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString());
                        System.out.println("Enter the new price ");
                        double price = scan.nextDouble();
                        mutualFund.setOldPrice(mutualFund.getPrice());
                        mutualFund.setPrice(price);
                    }
                }
                if (portfolio.getStocks() != null) {
                    System.out.println("\nYour stock Portfolio");
                    for (Stock s2t : portfolio.getStocks()) {
                        System.out.println(s2t.toString() + "\n");
                    }
                }
    
                if (portfolio.getMutualFunds() != null) {
                    System.out.println("\nYour Mutual Fund Portfolio");
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;
            case 4:
                double sgain = 0, mgain = 0, tgain;
                if (portfolio.getStocks() != null) {
                    for (Stock sttt : portfolio.getStocks()) {
                        double gainValue = ((sttt.getQuantity() * sttt.getPrice()) - 9.99) - sttt.getBookValue();
                        sgain = gainValue;
                    }
                }
                if (portfolio.getMutualFunds() != null) {
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        double gainValue = ((mutualFund.getQuantity() * mutualFund.getPrice()) - 9.99) - mutualFund.getBookValue();
                        mgain = gainValue;
                    }
                }
                tgain = sgain + mgain;
                System.out.println("\nTotal gain for the Investements in your Portfolio is " + tgain + "\n");
                break;
            case 5:
                System.out.println("Enter the symbol you wish to search for or leave blank and enter: ");
                String temp1 = scan.nextLine();

                System.out.println("Enter the name/keyword you wish search for or leave blank and enter: ");
                String temp2 = scan.nextLine();

                if (temp1 == "\n" && temp2 == "\n") {
                    System.out.println("\ncaSE-1");
                    System.out.println("\nYour Stock Portfolio");
                    for (Stock st1 : portfolio.getStocks()) {
                        System.out.println(st1.toString() + "\n");
                    }
                    System.out.println("\nYour Mutual Fund Portfolio");
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }else if (temp1 == "\n") {
                    System.out.println("\ncaSE-2");
                    Stock st2 = portfolio.checkStock(temp2);
                    if (st2 != null) {
                        System.out.println(st2.toString() + "\n");
                    } else
                        System.out.println("No match found");
                } else if (temp2 == "\n") {
                    System.out.println("\ncaSE-3");
                    Stock st3 = portfolio.checkStock(temp1);
                    if (st3 != null) {
                        System.out.println(st3.toString() + "\n");
                    } else
                        System.out.println("No match found");
                } else {
                    System.out.println("\ncaSE-4");
                    Stock st4 = portfolio.checkStock(temp1);
                    Stock st2 = portfolio.checkStock(temp2);
                    if (st4 == st2) {
                        System.out.println(st4.toString() + "\n");
                    } else {
                        System.out.println("The name and symbol entered do not seem to match");
                    }
                }
                break;
            case 6:
                System.out.println("Have a Nice day!");
                iteration = 0;
                break;
            default: {
                System.out.println("Please enter a Valid Choice");
            }
            }

        }
    }

    private static void addstock(Stock st) {
        System.out.print("Enter quantity of the Stocks : ");
        int qnt = scan.nextInt();

        System.out.print("Enter the price of each Stock : ");
        double price = scan.nextDouble();

        double bookValue = ((price * qnt) + 9.99) + st.getBookValue();
        st.setQuantity(st.getQuantity() + qnt);
        st.setBookValue(bookValue);
        st.setPrice(price);

        System.out.println("Added " + qnt + " Stocks at " + price + " per stock");
    }

    private static void addMutualFunds(MutualFund mutualFund) {
        System.out.print("Enter quantity of the Mutual Funds : ");
        int qnt = scan.nextInt();

        System.out.print("Enter the price of each Stock :");
        double price = scan.nextDouble();

        double bookValue = ((price * qnt) + mutualFund.getBookValue());
        int newQnt = mutualFund.getQuantity() + qnt;
        mutualFund.setPrice(price);
        mutualFund.setQuantity(newQnt);
        mutualFund.setBookValue(bookValue);

        System.out.println("Added " + qnt + " Mutual Fund units at " + price + " per unit");
    }

    private static void buyStock(String symbol) {
        System.out.print("Enter name of the Stock : ");
        String name = scan.nextLine();

        System.out.print("Enter quantity of the Stocks : ");
        int qnt = scan.nextInt();

        System.out.print("Enter price of each stock : ");
        double price = scan.nextDouble();

        Stock st = new Stock(symbol, name, qnt, price, 9.99);

        st.symbol = symbol;
        st.name = name;
        st.quantity = qnt;
        st.price = price;
        double ans = price * qnt + 9.99;

        st.setBookValue((price * qnt) + 9.99);
        st.bookValue = ans;

        portfolio.addStocks(st);
    }

    private static void buyMutualFund(String symbol) {
        System.out.print("Enter Name of the Mutual Fund : ");
        String name = scan.nextLine();

        System.out.print("Enter quantity of the Mutual Funds : ");
        int qnt = scan.nextInt();

        System.out.print("Enter price of each Unit of Mutual Fund : ");
        double price = scan.nextDouble();

        MutualFund mutualFund = new MutualFund(symbol, name, qnt, price);
        mutualFund.setBookValue(price * qnt);
        portfolio.addMutualFund(mutualFund);

    }

    private static void sellStock(Stock st) {
        System.out.println("Enter Price for which you wanna sell the Stock : ");
        double price = scan.nextDouble();

        System.out.println("Enter the Quantity of stock you wanna sell : ");
        int quantity = scan.nextInt();

        if (quantity <= st.getQuantity()) {
            int newQnt = st.getQuantity() - quantity;
            if (newQnt > 0) {
                double bookValue = st.getBookValue() * newQnt / st.getQuantity();
                st.setBookValue(bookValue);
                st.setQuantity(newQnt);
            } else {
                portfolio.getStocks().remove(st);
            }
            System.out.println("Successfully Sold the Stock!");
        } else {
            System.out.println("You don't have enough stocks to sell in your Portfolio!");
        }
    }

    private static void sellMutualFund(MutualFund mutualFund) {
        System.out.println("Enter Price for which you wanna sell the Mutual Funds : ");
        double price = scan.nextDouble();

        System.out.println("Enter the Quantity of Mutual Fund Units you wanna sell : ");
        int quantity = scan.nextInt();

        if (quantity <= mutualFund.getQuantity()) {
            int newQnt = mutualFund.getQuantity() - quantity;
            if (newQnt > 0) {
                double bookValue = mutualFund.getBookValue() * newQnt / mutualFund.getQuantity();
                mutualFund.setBookValue(bookValue);
                mutualFund.setQuantity(newQnt);
            } else {
                portfolio.getMutualFunds().remove(mutualFund);
            }
            System.out.println("Successfully Sold the Mutual Funds!");
        } else {
            System.out.println("You don't have enough Mutual Funds to sell!");
        }

    }
}
