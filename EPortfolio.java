
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
 * @author    Arshia Sandhu (Student ID: 1125681)
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
                    Stock sym = portfolio.checkStock(name); // to check if stock already exists
                    if (sym != null) {
                        addstock(sym); // to manipulate quantity
                    } else {
                        buyStock(name); // to buy stock
                    }

                } else if (invtype == 2) { // to buy mutal Funds
                    System.out.println("\nEnter the Symbol for the Mutual Fund You wish to Buy");
                    scan.skip("\\R?");
                    String name = scan.nextLine();
                    MutualFund sym = portfolio.checkMutalFund(name); // to check if mutual fund already exits
                    if (sym != null) {
                        addMutualFunds(sym); // to manupilate quantity
                    } else {
                        buyMutualFund(name); // to buy new mutual fund
                    }
                } else {
                    System.out.println("Please enter 1 or 2");
                }
                if (portfolio.getStocks() != null) { // prints stocks
                    for (Stock stockk : portfolio.getStocks()) {
                        System.out.println("Stock Share");
                        System.out.println(stockk.toString() + "\n");
                    }
                }

                if (portfolio.getMutualFunds() != null) { // prints mutual funds
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println("\nMutual Fund Share");
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;

            // sell
            case 2:
                System.out.println("Enter The symbol for the investment you wish to sell: ");
                String symbol = scan.nextLine();

                Stock Stocksym = portfolio.checkStock(symbol); // checks if stock exists
                if (Stocksym != null) {
                    sellStock(Stocksym); // sell stock using symbol
                } else {
                    MutualFund MFsym = portfolio.checkMutalFund(symbol); // checks if mutaul fund exists
                    if (MFsym != null) {
                        sellMutualFund(MFsym); // sell mutual fund using symbol
                    } else {
                        System.out.println("Uh oh! There is no such investment in your Portfolio.");
                    }
                }

                if (portfolio.getStocks() != null) { // prints stocks
                    for (Stock s1t : portfolio.getStocks()) {
                        System.out.println("Stock Share");
                        System.out.println(s1t.toString() + "\n");
                    }
                }

                if (portfolio.getMutualFunds() != null) { // prints mutual funds
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println("Mutual Fund Share");
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;

            // updates
            case 3:
                if (portfolio.getStocks() != null) { // if stocks exist
                    for (Stock upstock : portfolio.getStocks()) {
                        System.out.println(upstock.toString());
                        System.out.println("Enter the updated price for the current Stock.\n");
                        double price = scan.nextDouble();
                        upstock.setOldPrice(upstock.getPrice());
                        upstock.setPrice(price);
                    }
                }

                if (portfolio.getMutualFunds() != null) { // if mutal funds exist
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println(mutualFund.toString());
                        System.out.println("Enter the updated price for the current Stock.\n");
                        double price = scan.nextDouble();
                        mutualFund.setOldPrice(mutualFund.getPrice());
                        mutualFund.setPrice(price);
                    }
                }
                if (portfolio.getStocks() != null) { // prints stocks
                    for (Stock s2t : portfolio.getStocks()) {
                        System.out.println("Stock Share");
                        System.out.println(s2t.toString() + "\n");
                    }
                }

                if (portfolio.getMutualFunds() != null) { // prints mutual funds
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        System.out.println("Mutual Fund Share");
                        System.out.println(mutualFund.toString() + "\n");
                    }
                }
                break;

            // calculates gain
            case 4:
                double sgain, mgain, tgain, gainstock = 0, gainmf = 0;
                if (portfolio.getStocks() != null) {
                    for (Stock sttt : portfolio.getStocks()) {
                        int sellqnt = sttt.getSellQty();
                        double sellpricee = sttt.getPrice();
                        double oldbookval = sttt.getOldPrice() * (sttt.getQuantity() + sttt.getSellQty()) + 9.99;
                        int oldqnt = sttt.getQuantity() + sttt.getSellQty();
                        gainstock = (sellqnt * sellpricee - 9.99) - oldbookval * (sellqnt / oldqnt);
                    }
                }
                sgain = gainstock;
                if (portfolio.getMutualFunds() != null) {
                    for (MutualFund mutualFund : portfolio.getMutualFunds()) {
                        int sellqnt = mutualFund.getSellQty();
                        double sellpricee = mutualFund.getPrice();
                        double oldbookval = mutualFund.getOldPrice()
                                * (mutualFund.getQuantity() + mutualFund.getSellQty());
                        int oldqnt = mutualFund.getQuantity() + mutualFund.getSellQty();
                        gainmf = (sellqnt * sellpricee) - oldbookval * (sellqnt / oldqnt);
                    }
                }
                mgain = gainmf;
                tgain = sgain + mgain;
                System.out.println("\nTotal gain for the Investements in your Portfolio is " + tgain + "\n");
                break;

            // search
            case 5:

                System.out.println("Enter the symbol you wish to search for or press enter to leave blank");
                String searchSym = scan.nextLine();

                System.out.println("Enter the name/keyword you wish search for or press enter to leave blank");
                String searchkey = scan.nextLine();

                System.out.println("Enter the Amount Under which you need to find Investments");
                double upper = scan.nextDouble();

                System.out.println("Enter the Amount Above which you need to find Investments");
                double lower = scan.nextDouble();
                if (searchkey == "\n") { // searching for symbol
                    Stock stock3 = portfolio.checkStock(searchSym);
                    if (stock3 != null) {
                        System.out.println(stock3.toString() + "\n");
                    } else
                        System.out.println("No match found");
                    MutualFund stock8 = portfolio.checkMutalFund(searchSym);
                    if (stock8 != null) {
                        System.out.println(stock8.toString() + "\n");
                    } else
                        System.out.println("No match found");
                }

                else if (searchSym == "\n") { // searching for key
                    Stock stock2 = portfolio.checkStock(searchkey);
                    if (stock2 != null) {
                        System.out.println(stock2.toString() + "\n");
                    } else
                        System.out.println("No match found");
                    MutualFund stock7 = portfolio.checkMutalFund(searchkey);
                    if (stock7 != null) {
                        System.out.println(stock7.toString() + "\n");
                    } else
                        System.out.println("No match found");
                }

                else if (searchSym == "\n" && searchkey == "\n") {
                    if (portfolio.getMutualFunds() != null && portfolio.getStocks() != null) {
                        Stock stock4 = portfolio.checkStock(searchSym); // searching for symbol and key
                        MutualFund stock10 = portfolio.checkMutalFund(searchSym);
                        Stock stock2 = portfolio.checkStock(searchkey);
                        MutualFund stock11 = portfolio.checkMutalFund(searchkey);
                        if (stock4 == stock2) {
                            System.out.println(stock4.toString() + "\n");
                        } else {
                            System.out.println("The name and symbol entered do not seem to match");
                        }
                        if (stock10 == stock11 && stock10 != null) {
                            System.out.println(stock10.toString() + "\n");
                        } else {
                            System.out.println("The name and symbol entered do not seem to match");
                        }
                    }
                }

                Stock stock5 = portfolio.rangeStock(upper, lower); // finding using price range
                if (stock5 != null) {
                    System.out.println("\n\nStocks in the range of " + lower + " to " + upper + " are");
                    System.out.println(stock5.toString());
                }
                MutualFund stock6 = portfolio.rangeMutalFund(upper, lower);
                if (stock6 != null) {
                    System.out.println("\n\nMutual Funds in the range of " + lower + " to " + upper + " are");
                    System.out.println(stock6.toString());
                }
                System.out.println("\n\n");
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

    private static void addstock(Stock stock) {
        System.out.print("Enter quantity of the Stocks\n");
        int squantity = scan.nextInt();

        System.out.print("Enter the price of each Stock\n");
        double price = scan.nextDouble();

        double bookValue = ((squantity * price) + 9.99) + stock.getBookValue();
        stock.setQuantity(stock.getQuantity() + squantity);
        stock.setBookValue(bookValue);
        stock.setPrice(price);

        System.out.println("Added " + squantity + " Stocks at " + price + " per stock");
    }

    private static void addMutualFunds(MutualFund mutualFund) {
        System.out.print("Enter quantity of the Mutual Funds\n");
        int mfquantity = scan.nextInt();

        System.out.print("Enter the price of each Stock\n");
        double price = scan.nextDouble();

        double bookValue = ((mfquantity * price) + mutualFund.getBookValue());
        int newQuantity = mutualFund.getQuantity() + mfquantity;
        mutualFund.setPrice(price);
        mutualFund.setQuantity(newQuantity);
        mutualFund.setBookValue(bookValue);

        System.out.println("Added " + mfquantity + " Mutual Fund units at " + price + " per unit");
    }

    private static void buyStock(String symbol) {
        System.out.print("Enter name of the Stock\n");
        String name = scan.nextLine();

        System.out.print("Enter quantity of the Stocks\n");
        int qnt = scan.nextInt();

        System.out.print("Enter price of each stock\n");
        double price = scan.nextDouble();

        Stock stockk = new Stock(symbol, name, qnt, price, 9.99);
        stockk.symbol = symbol;
        stockk.name = name;
        stockk.quantity = qnt;
        stockk.price = price;
        double bv = price * qnt + 9.99;
        stockk.setBookValue((price * qnt) + 9.99);
        stockk.bookValue = bv;
        portfolio.addStocks(stockk);
    }

    private static void buyMutualFund(String symbol) {
        System.out.print("Enter Name of the Mutual Fund\n");
        String name = scan.nextLine();
        System.out.print("Enter quantity of the Mutual Funds\n");
        int qnt = scan.nextInt();
        System.out.print("Enter price of each Unit of Mutual Fund\n");
        double price = scan.nextDouble();
        MutualFund mutualFund = new MutualFund(symbol, name, qnt, price);
        mutualFund.setBookValue(price * qnt);
        portfolio.addMutualFund(mutualFund);

    }

    private static void sellStock(Stock stockk) {
        stockk.setOldPrice(stockk.getPrice());
        System.out.println("Enter Price for which you wanna sell the Stock");
        double price = scan.nextDouble();
        stockk.setPrice(price);
        System.out.println("Enter the Quantity of stock you wanna sell");
        int quantity = scan.nextInt();
        stockk.sellqty(quantity);
        if (quantity <= stockk.getQuantity()) { // checking if we have sufficient stocks
            int newQuantity = stockk.getQuantity() - quantity;
            if (newQuantity > 0) {
                double bookValue = stockk.getBookValue() * newQuantity / stockk.getQuantity();
                stockk.setBookValue(bookValue);
                stockk.setQuantity(newQuantity);
            } else {
                portfolio.getStocks().remove(stockk);
            }
            System.out.println("Successfully Sold the Stock!");
        } else {
            System.out.println("You don't have enough stocks to sell in your Portfolio!");
        }
    }

    private static void sellMutualFund(MutualFund mutualFund) {
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
                portfolio.getMutualFunds().remove(mutualFund);
            }
            System.out.println("Successfully Sold the Mutual Funds!");
        } else {
            System.out.println("You don't have enough Mutual Funds to sell!");
        }

    }
}
