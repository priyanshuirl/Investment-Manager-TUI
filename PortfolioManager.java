import java.util.ArrayList;

public class PortfolioManager {

    private ArrayList<Stock> stocks;
    private ArrayList<MutualFund> mutualFunds;

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public Stock rangeStock(double upper, double lower) {
        if (stocks != null) {
            for (Stock stock : stocks) {
                if (stock.getPrice() < upper && stock.getPrice() > lower) {
                    return stock;
                }
            }
        }
        return null;
    }

    public MutualFund rangeMutalFund(double upper, double lower) {
        if (mutualFunds != null) {
            for (MutualFund mutualFund : mutualFunds) {
                if (mutualFund.getPrice() < upper && mutualFund.getPrice() > lower) {
                    return mutualFund;
                }
            }
        }
        return null;
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
