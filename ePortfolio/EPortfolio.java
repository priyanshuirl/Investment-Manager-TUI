
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class EPortfolio {
    private static PortfolioManager portfolio;
    static ArrayList<String> Investmentsrecords = new ArrayList<>();
    static HashMap<String, String> index = new HashMap<String, String>(50);
    private static Scanner scan;
    static String line;

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
                    "(5) Search: find all investments that match a search request and display all attributes of these investments. ");
            System.out.println("(6) Load data from a File.");
            System.out.println("(7) Save data to the File.");
            System.out.println("(8) Display all Your Investments.");
            System.out.println("(9) Quit");
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
                            System.out.println("\nStock Shares\n");
                            System.out.println(stock.toString() + "\n");
                        }
                    }
                }

                if (portfolio.getInvestments() != null) { // prints mutual funds
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            System.out.println("Mutual Fund Units\n");
                            System.out.println(mutualFund.toString() + "\n");
                        }
                    }
                }
                break;

            // sell
            case 2:
                System.out.println("Enter The symbol for the investment you wish to sell: ");
                String symbol = scan.nextLine();
                if (portfolio.getInvestments() != null) {
                    for (Investment investment : portfolio.getInvestments()) {
                        if (investment.getType().equals("mutualfund")) {
                            investment = portfolio.checkInvestment(symbol); // checks if mutaul fund exists
                            if (investment != null) {
                                sellMutualFund(investment);
                            }
                        } else if (investment.getType().equals("stock")) {
                            investment = portfolio.checkInvestment(symbol); // checks if mutaul fund exists
                            if (investment != null) {
                                sellStock(investment); // sell stock using symbol
                            } else {
                                System.out.println("\nUh oh! There is no such investment in your Portfolio.\n");
                            }
                        }
                    }
                } else {
                    System.out.println("Data not found.");
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
                            System.out.println("\nStock Shares\n");
                            System.out.println(stock.toString() + "\n");
                        }
                    }
                }

                if (portfolio.getInvestments() != null) { // prints mutual funds
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            System.out.println("Mutual Fund Units\n");
                            System.out.println(mutualFund.toString() + "\n");
                        }
                    }
                }
                break;

            // calculates gain
            case 4:
                double sgain = 0, mgain = 0, tgain, gainstock = 0, gainmf = 0;
                if (portfolio.getInvestments() != null) {
                    for (Investment stock : portfolio.getInvestments()) {
                        if (stock.getType().equals("stock")) {
                            int sellqnt = stock.getSellQty();
                            double sellpricee = stock.getPrice();
                            double val = (sellqnt * sellpricee);
                            double val4 = stock.getBookValue();
                            gainstock += val - val4 - 9.99;
                        }
                    }
                    if (gainstock <= 0) {
                        System.out.println("\nYou have Not made any Profits Selling Stocks yet");
                    } else {
                        sgain = gainstock;
                        System.out.println("\nTotal Profit from Selling Stocks is " + sgain + "\n");
                    }
                }
                if (portfolio.getInvestments() != null) {
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            int sellqnt = mutualFund.getSellQty();
                            double sellpricee = mutualFund.getPrice();
                            double vval = (sellqnt * sellpricee);
                            double vval4 = mutualFund.getBookValue();
                            gainmf += vval - vval4;
                        }
                    }
                    if (gainmf <= 0) {
                        System.out.println("\nYou have Not made any Profits Selling Mutual Funds yet");
                    } else {
                        mgain = gainmf;
                        System.out.println("\nTotal Profit from Selling Mutual Funds is " + mgain + "\n");
                    }
                }
                tgain = sgain + mgain;
                if (tgain > 0) {
                    System.out.println("\nTotal gain for the Investements in your Portfolio is " + tgain + "\n");
                } else {
                    System.out.println("\n\nNo Gains To show. You have Not made any Profits Yet.\n");
                }
                break;

            // search
            case 5:
                System.out.println("\n\nDo you wanna Search Using Sequential Search or Hash Maps ?");
                System.out.println("\nEnter 1 for Sequential Search and 2 for Hash Map search");
                int optionch = scan.nextInt();
                if (optionch == 1) {
                    System.out.println("Do you wanna search using prince range? (Enter 1 for yes 2 for No)"); // price
                                                                                                              // range
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
                } else if (optionch == 2) {
                    System.out.println("Search using Hash Maps");
                    ArrayList<Integer> value = new ArrayList<Integer>();
                    if (portfolio.getInvestments() != null) { // prints mutual funds
                        for (Investment investment : portfolio.getInvestments()) {
                            String invname = investment.getName();
                            String[] tokens = invname.split(" ");
                            for (int i = 0; i < tokens.length; i++) {
                                for (int j = 0; j < portfolio.getInvestments().size(); j++) {
                                    String text = portfolio.getInvestments().get(j).getName();
                                    if (text.contains(tokens[i])) {
                                        value.add(j);
                                    }
                                }
                                StringBuffer sb = new StringBuffer();

                                for (int s : value) {
                                    sb.append(s);
                                    sb.append(" ");
                                }
                                String str = sb.toString();
                                index.put(tokens[i].toLowerCase(), str);
                                value.clear();
                            }
                        }
                    }
                    System.out.println("Enter the Keyword You wish to Search for");
                    scan.skip("\\R?");
                    String keyw = scan.nextLine().toLowerCase();
                    String[] keyar = keyw.split(" ");
                    ArrayList<Integer> intlist = new ArrayList<>();
                    for (int k = 0; k < keyar.length; k++) {
                        String word = keyar[k];
                        String listval = index.get(word);
                        if (!(listval == null)) {
                            String[] result = listval.split(" ");
                            for (int p = 0; p < result.length; p++) {
                                int pt = Integer.parseInt(result[p]);
                                intlist.add(pt);
                            }
                        }
                    }
                    Set<Integer> ss = new LinkedHashSet<Integer>(intlist);
                    Set<String> s = ss.stream().map(String::valueOf).collect(Collectors.toSet());
                    String[] str1 = new String[s.size()];
                    int x = 0;
                    for (String str : s) {
                        str1[x++] = str;
                    }
                    if (s.size() <= 0) {
                        System.out.println("No Results found for the keyword " + keyw + "\n");
                    } else {
                        System.out.println("Your Search results : ");
                        for (int i = 0; i < s.size(); i++) {
                            int pt = Integer.parseInt(str1[i]);
                            for (int j = 0; j < portfolio.getInvestments().size(); j++) {
                                if (pt == j) {
                                    System.out.println(portfolio.getInvestments().get(j).toString());
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Please enter a Valid Choice ( 1 or 2 )");
                }

                break;

            case 6:
                System.out.print("Enter the Name of the File you want to Load Data from: ");
                String fname = scan.nextLine();
                System.out.print("\nLoading Data from " + fname + "...\n\n");
                try {
                    BufferedReader input = new BufferedReader(new FileReader(fname));
                    while ((line = input.readLine()) != null) {
                        // add data from file
                        Investmentsrecords.add(line);
                    }
                    for (int i = 0; i < Investmentsrecords.size(); i++) {
                        String st = Investmentsrecords.get(i);
                        String[] data = st.split("-");
                        if (data[0].equals("stock")) {
                            Stock stockk = new Stock(data[1], data[2], Integer.parseInt(data[3]),
                                    Double.parseDouble(data[4]), "stock");
                            stockk.symbol = data[1];
                            stockk.name = data[2];
                            stockk.quantity = Integer.parseInt(data[3]);
                            stockk.price = Double.parseDouble(data[4]);
                            double bv = Double.parseDouble(data[4]) * Integer.parseInt(data[3]) + 9.99;
                            stockk.setBookValue((Double.parseDouble(data[4]) * Integer.parseInt(data[3])) + 9.99);
                            stockk.bookValue = bv;
                            portfolio.addInvestments(stockk);
                        } else if (data[0].equals("stock")) {
                            MutualFund mutualFund = new MutualFund(data[1], data[2], Integer.parseInt(data[3]),
                                    Double.parseDouble(data[4]), "mutualfund");
                            mutualFund.setBookValue(Double.parseDouble(data[4]) * Integer.parseInt(data[3]));
                            portfolio.addInvestments(mutualFund);
                        }
                    }
                    input.close();
                    System.out.println("\nData loaded from file " + fname + " Successfully.\n");
                } catch (IOException e) {
                    System.out.println("\nSomething Went Wrong, Could Not open the file " + fname
                            + ", Please recheck the Name of the file you entered and make sure it exists.\n");
                }
                break;

            case 7:
                System.out.println("\nSaving into investments.txt Output File...\n");
                File investmentrecord = new File("investments.txt");
                try {
                    FileWriter fw = new FileWriter(investmentrecord);
                    Writer output = new BufferedWriter(fw);
                    for (int i = 0; i < portfolio.getInvestments().size(); i++) {
                        output.write(portfolio.getInvestments().get(i).toFileString() + "\n");
                    }
                    output.close();
                    System.out.println("File Saved Successfully!\n");
                } catch (IOException e) {
                    System.out.println("\nSomething Went Wrong, Cannot Open File.\n");
                }
                break;

            case 8:
                if (portfolio.getInvestments() != null) { // prints stocks
                    for (Investment stock : portfolio.getInvestments()) {
                        if (stock.getType().equals("stock")) {
                            System.out.println("\nStock Shares\n");
                            System.out.println(stock.toString() + "\n");
                        }
                    }
                }

                if (portfolio.getInvestments() != null) { // prints mutual funds
                    for (Investment mutualFund : portfolio.getInvestments()) {
                        if (mutualFund.getType().equals("mutualfund")) {
                            System.out.println("Mutual Fund Units\n");
                            System.out.println(mutualFund.toString() + "\n");
                        }
                    }
                }
                break;

            // quitting program
            case 9:
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

        System.out.println("Added " + squantity + " Stocks at " + price + " per stock\n");
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

        System.out.println("Added " + mfquantity + " Mutual Fund units at " + price + " per unit\n");
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
        if (stock.getType().equals("stock")) {
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
                    double val1 = newQuantity;
                    double val2 = stock.getQuantity();
                    double bookValue = stock.getBookValue() - (stock.getBookValue() * (val1 / val2));
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
    }

    /**
     * Sells mutual fund and reduces quantity
     * 
     * @param mutualFund
     */

    private static void sellMutualFund(Investment mutualFund) {
        if (mutualFund.getType().equals("mutualfund")) {
            System.out.println("Enter Price for which you wanna sell the Mutual Funds");
            double price = scan.nextDouble();
            mutualFund.setPrice(price);
            System.out.println("Enter the Quantity of Mutual Fund Units you wanna sell");
            int quantity = scan.nextInt();

            if (quantity <= mutualFund.getQuantity()) { // checking if there are sufficient funds
                int newQuantity = mutualFund.getQuantity() - quantity;
                if (newQuantity > 0) {
                    double bookValue = mutualFund.getBookValue() * newQuantity / mutualFund.getQuantity();
                    mutualFund.setBookValue(bookValue - 45);
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
}
