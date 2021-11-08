
/**
 * ================================= ePortfolio ======================================================
 * 
 * ePortfolio is a program that helps an investor maintain a portfolio consisting of details 
 * about different stock and mutual fund investments.
 * <p>
 * The program stores investment properties such as symbol, name, the number of shares, price of the 
 * shares and their bookvalue and then it lets the user:
 * <ul>
 * <li> (1) buy a new investment or add more quantity to an existing investment.
 * <li> (2) sell some investments by reducing some quantity from existing investment.
 * <li> (3) update and refresh the prices of all the existing investments.
 * <li> (4) calculate the total gain of their portfolio by accumulating all individual investments.
 * <li> (5) search of investments that match the search query. 
 * <ul>
 * <p> 
 * 
 * @author    Priyanshu Mishra
 * 
 */

import java.util.Scanner;

public class EPortfolio {
    private static PortfolioManager portfolio;
    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        portfolio = new PortfolioManager();
        System.out.println("\nWelcome to Your ePortfolio\n");
        int iteration = 1;
        // main menu
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

            // Buy
            case 1:
                System.out.println("\nChoose the Type of Investment You wanna Buy");
                System.out.println("(1)Stocks");
                System.out.println("(2)Mutual Funds");
                System.out.println("\nEnter Your Choice : ");
                int invtype = scan.nextInt();
                if (invtype == 1) { // to buy Stocks
                    System.out.println("\nEnter the Symbol for the Stock You wish to Buy");
                    scan.skip("\\R?");
                    String name = scan.nextLine();
                    Investment symbol = portfolio.checkInvestment(name); // to check if stock already exists
                    if (symbol != null) {
                        addstock(symbol); // to manipulate quantity
                    } else {
                        buyStock(name); // to buy stock
                    }

                } else if (invtype == 2) { // to buy mutal Funds
                    System.out.println("\nEnter the Symbol for the Mutual Fund You wish to Buy");
                    scan.skip("\\R?");
                    String name = scan.nextLine();
                    Investment symbol = portfolio.checkInvestment(name); // to check if mutual fund already exits
                    if (symbol != null) {
                        addMutualFunds(symbol); // to manupilate quantity
                    } else {
                        buyMutualFund(name); // to buy new mutual fund
                    }
                } else {
                    System.out.println("Please enter 1 or 2");
                }
                if (portfolio.getInvestments() != null) { // prints stocks
                    for (Investment stock : portfolio.getInvestments()) {
                        if (stock.getType().equals("stock")) {
                            System.out.println("Stock Shares");
                            System.out.println(stock.toString() + "\n");
                        }
                    }
                }

                if (portfolio.getInvestments() != null) { // prints mutual funds
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            System.out.println("Mutual Fund Shares");
                            System.out.println(mutualFund.toString() + "\n");
                        }
                    }
                }
                break;

            // sell
            case 2:
                System.out.println("Enter The symbol for the investment you wish to sell: ");
                String symbol = scan.nextLine();

                Investment investment = portfolio.checkInvestment(symbol); // checks if stock exists
                if (investment != null) {
                    sellStock(investment); // sell stock using symbol
                } else {
                    investment = portfolio.checkInvestment(symbol); // checks if mutaul fund exists
                    if (investment != null) {
                        sellMutualFund(investment); // sell mutual fund using symbol
                    } else {
                        System.out.println("Uh oh! There is no such investment in your Portfolio.");
                    }
                }
                break;

            // updates
            case 3:
                if (portfolio.getInvestments() != null) { // if investment exist
                    for (Investment investmentupdate : portfolio.getInvestments()) {
                        System.out.println(investmentupdate.toString());
                        System.out.println("Enter the updated price : \n");
                        double price = scan.nextDouble();
                        investmentupdate.setOldPrice(investmentupdate.getPrice());
                        investmentupdate.setPrice(price);
                    }
                }
                if (portfolio.getInvestments() != null) { // prints stocks
                    for (Investment stock : portfolio.getInvestments()) {
                        if (stock.getType().equals("stock")) {
                            System.out.println("\nStock Shares");
                            System.out.println(stock.toString() + "\n");
                        }
                    }
                }

                if (portfolio.getInvestments() != null) { // prints mutual funds
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            System.out.println("Mutual Fund Shares");
                            System.out.println(mutualFund.toString() + "\n");
                        }
                    }
                }
                break;

            // calculates gain
            case 4:
                double sgain, mgain, tgain, gainstock = 0, gainmf = 0;
                if (portfolio.getInvestments() != null) {
                    for (Investment stock : portfolio.getInvestments()) {
                        if (stock.getType().equals("stock")) {
                            int sellqnt = stock.getSellQty();
                            double sellpricee = stock.getPrice();
                            double oldbookval = stock.getOldPrice() * (stock.getQuantity() + stock.getSellQty()) + 9.99;
                            int oldqnt = stock.getQuantity() + stock.getSellQty();
                            gainstock = (sellqnt * sellpricee - 9.99) - oldbookval * (sellqnt / oldqnt);
                        }
                    }
                }
                sgain = gainstock;
                if (portfolio.getInvestments() != null) {
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            int sellqnt = mutualFund.getSellQty();
                            double sellpricee = mutualFund.getPrice();
                            double oldbookval = mutualFund.getOldPrice()
                                    * (mutualFund.getQuantity() + mutualFund.getSellQty());
                            int oldqnt = mutualFund.getQuantity() + mutualFund.getSellQty();
                            gainmf = (sellqnt * sellpricee) - oldbookval * (sellqnt / oldqnt);
                        }
                    }
                }
                mgain = gainmf;
                tgain = sgain + mgain;
                System.out.println("\nTotal gain for the Investements in your Portfolio is " + tgain + "\n");
                break;

            // search
            case 5:
                System.out.println("Do you wanna search using prince range? (Enter 1 for yes 2 for No)"); // price range
                int option = scan.nextInt();
                if (option == 1) {

                    System.out.println("Enter the Amount Above which you need to find Investments");
                    double lower = scan.nextDouble();

                    System.out.println("Enter the Amount Under which you need to find Investments");
                    double upper = scan.nextDouble();

                    Investment stock = portfolio.rangeInvestment(upper, lower); // finding using price range
                    if (stock != null && stock.getType().equals("stock")) {
                        System.out.println("\n\nStocks in the range of " + lower + " to " + upper + " are");
                        System.out.println(stock.toString());
                    }
                    Investment mutualfund = portfolio.rangeInvestment(upper, lower);
                    if (mutualfund != null && mutualfund.getType().equals("mutualfund")) {
                        System.out.println("\n\nMutual Funds in the range of " + lower + " to " + upper + " are");
                        System.out.println(mutualfund.toString());
                    }
                    System.out.println("\n\n");
                }

                System.out.println("Do you wanna search using symbol? (Enter 1 for yes 2 for No)"); // using symbol
                int option2 = scan.nextInt();
                if (option2 == 1) {

                    System.out.println("Enter the symbol you wish to search for or press enter to leave blank");
                    scan.skip("\\R?");
                    String searchSym = scan.nextLine();

                    Investment stock = portfolio.checkInvestment(searchSym);
                    if (stock != null && stock.getType().equals("stock")) {
                        System.out.println("Here is your Stock:");
                        System.out.println(stock.toString() + "\n");
                    }
                    Investment mutualfund = portfolio.checkInvestment(searchSym);
                    if (mutualfund != null && mutualfund.getType().equals("mutualfund")) {
                        System.out.println("Here is your Mutual Fund:");
                        System.out.println(mutualfund.toString() + "\n");
                    }
                }

                System.out.println("Do you wanna search using name? (Enter 1 for yes 2 for No)"); // using symbol
                int option3 = scan.nextInt();
                if (option3 == 1) {

                    System.out.println("Enter the name/keyword you wish search for or press enter to leave blank");
                    scan.skip("\\R?");
                    String searchkey = scan.nextLine();

                    Investment stock = portfolio.checkInvestment(searchkey);
                    if (stock != null && stock.getType().equals("stock")) {
                        System.out.println("Here is your Stock:");
                        System.out.println(stock.toString() + "\n");
                    }
                    Investment mutualfund = portfolio.checkInvestment(searchkey);
                    if (mutualfund != null && mutualfund.getType().equals("mutualfund")) {
                        System.out.println("Here is your Mutual Fund:");
                        System.out.println(mutualfund.toString() + "\n");
                    }
                }

                break;

            // quitting program
            case 6:
                System.out.println("Have a Nice day!");
                iteration = 0;
                break;

            // default case
            default: {
                System.out.println("Please enter a Valid Choice");
            }
            }

        }

    }

    /**
     * Adds Stock
     * 
     * @param stock, Stock
     * 
     */

    private static void addstock(Investment investment) {
        System.out.print("Enter quantity of the Stocks\n");
        int squantity = scan.nextInt();

        System.out.print("Enter the price of each Stock\n");
        double price = scan.nextDouble();

        double bookValue = ((squantity * price) + 9.99) + investment.getBookValue();
        investment.setQuantity(investment.getQuantity() + squantity);
        investment.setBookValue(bookValue);
        investment.setPrice(price);

        System.out.println("Added " + squantity + " Stocks at " + price + " per stock");
    }

    /**
     * Adds Mutual Fund
     * 
     * @param mutualFund, mutual fund
     */
    private static void addMutualFunds(Investment investment) {
        System.out.print("Enter quantity of the Mutual Funds\n");
        int mfquantity = scan.nextInt();

        System.out.print("Enter the price of each Stock\n");
        double price = scan.nextDouble();

        double bookValue = ((mfquantity * price) + investment.getBookValue());
        int newQuantity = investment.getQuantity() + mfquantity;
        investment.setPrice(price);
        investment.setQuantity(newQuantity);
        investment.setBookValue(bookValue);

        System.out.println("Added " + mfquantity + " Mutual Fund units at " + price + " per unit");
    }

    /**
     * Buys stock and adds more quantity to an existing investment.
     * 
     * @param symbol
     */

    private static void buyStock(String symbol) {
        System.out.print("Enter name of the Stock\n");
        String name = scan.nextLine();

        System.out.print("Enter quantity of the Stocks\n");
        int qnt = scan.nextInt();

        System.out.print("Enter price of each stock\n");
        double price = scan.nextDouble();

        Stock stockk = new Stock(symbol, name, qnt, price, "stock");
        stockk.symbol = symbol;
        stockk.name = name;
        stockk.quantity = qnt;
        stockk.price = price;
        double bv = price * qnt + 9.99;
        stockk.setBookValue((price * qnt) + 9.99);
        stockk.bookValue = bv;
        portfolio.addInvestments(stockk);
    }

    /**
     * Buys mutual fund and adds more quantity to an existing investment.
     * 
     * @param symbol
     */

    private static void buyMutualFund(String symbol) {
        System.out.print("Enter Name of the Mutual Fund\n");
        String name = scan.nextLine();
        System.out.print("Enter quantity of the Mutual Funds\n");
        int qnt = scan.nextInt();
        System.out.print("Enter price of each Unit of Mutual Fund\n");
        double price = scan.nextDouble();
        MutualFund mutualFund = new MutualFund(symbol, name, qnt, price, "mutualfund");
        mutualFund.setBookValue(price * qnt);
        portfolio.addInvestments(mutualFund);
    }

    /**
     * Sells stock and reduces quantity
     * 
     * @param stock
     */

    private static void sellStock(Investment stock) {
        stock.setOldPrice(stock.getPrice());
        System.out.println("Enter Price for which you wanna sell the Stock");
        double price = scan.nextDouble();
        stock.setPrice(price);
        System.out.println("Enter the Quantity of stock you wanna sell");
        int quantity = scan.nextInt();
        stock.sellqty(quantity);
        if (quantity <= stock.getQuantity()) { // checking if we have sufficient stocks
            int newQuantity = stock.getQuantity() - quantity;
            if (newQuantity > 0) {
                double bookValue = stock.getBookValue() * newQuantity / stock.getQuantity();
                stock.setBookValue(bookValue);
                stock.setQuantity(newQuantity);
            } else {
                portfolio.getInvestments().remove(stock);
            }
            System.out.println("Successfully Sold the Stock!");
        } else {
            System.out.println("You don't have enough stocks to sell in your Portfolio!");
        }
    }

    /**
     * Sells mutual fund and reduces quantity
     * 
     * @param mutualFund
     */

    private static void sellMutualFund(Investment mutualFund) {
        System.out.println("Enter Price for which you wanna sell the Mutual Funds\n");
        double price = scan.nextDouble();
        mutualFund.setPrice(price);
        System.out.println("Enter the Quantity of Mutual Fund Units you wanna sell\n");
        int quantity = scan.nextInt();

        if (quantity <= mutualFund.getQuantity()) { // checking if there are sufficient funds
            int newQuantity = mutualFund.getQuantity() - quantity;
            if (newQuantity > 0) {
                double bookValue = mutualFund.getBookValue() * newQuantity / mutualFund.getQuantity();
                mutualFund.setBookValue(bookValue);
                mutualFund.setQuantity(newQuantity);
            } else {
                portfolio.getInvestments().remove(mutualFund);
            }
            System.out.println("Successfully Sold the Mutual Funds!");
        } else {
            System.out.println("You don't have enough Mutual Funds to sell!");
        }

    }
}
