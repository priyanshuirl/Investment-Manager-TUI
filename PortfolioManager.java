import java.util.ArrayList;

public class PortfolioManager {

    private ArrayList<Stock> stocks;
    private ArrayList<MutualFund> mutualFunds;

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public Stock checkStock(String symbol) {
        if (stocks != null) {
            for (Stock stock : stocks) {
                if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                    return stock;
                } else if (stock.getName().contains(symbol)) {
                    return stock;
                }
            }
        }
        return null;
    }

    public MutualFund checkMutalFund(String symbol) {
        if (mutualFunds != null) {
            for (MutualFund mutualFund : mutualFunds) {
                if (mutualFund.getSymbol().equalsIgnoreCase(symbol)) {
                    return mutualFund;
                }
            }
        }
        return null;
    }

    public void setMutualFunds(ArrayList<MutualFund> mutualFunds) {
        this.mutualFunds = mutualFunds;
    }

    public void addMutualFund(MutualFund mutualFund) {
        if (mutualFunds == null) {
            mutualFunds = new ArrayList<>();
        }
        mutualFunds.add(mutualFund);
    }

    public void addStocks(Stock st) {
        if (stocks == null) {
            stocks = new ArrayList<>();
        }
        stocks.add(st);
    }

    public ArrayList<MutualFund> getMutualFunds() {
        return mutualFunds;
    }
}
