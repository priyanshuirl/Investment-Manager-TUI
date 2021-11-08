import java.util.ArrayList;

public class PortfolioManager {

    private ArrayList<Stock> stocks;
    private ArrayList<MutualFund> mutualFunds;

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public Stock checkStock(String symb) {
        if (stocks != null) {
            for (Stock stock : stocks) {
                if (stock.getSymbol().equalsIgnoreCase(symb)) {
                    return stock;
                } else if (stock.getName().contains(symb)) {
                    return stock;
                }
            }
        }
        return null;
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

    public MutualFund checkMutalFund(String symb) {
        if (mutualFunds != null) {
            for (MutualFund mutualFund : mutualFunds) {
                if (mutualFund.getSymbol().equalsIgnoreCase(symb)) {
                    return mutualFund;
                }
            }
        }
        return null;
    }

    public void setMutualFunds(ArrayList<MutualFund> mf) {
        this.mutualFunds = mf;
    }

    public void addMutualFund(MutualFund mFund) {
        if (mutualFunds == null) {
            mutualFunds = new ArrayList<>();
        }
        mutualFunds.add(mFund);
    }

    public void addStocks(Stock stock) {
        if (stocks == null) {
            stocks = new ArrayList<>();
        }
        stocks.add(stock);
    }

    public ArrayList<MutualFund> getMutualFunds() {
        return mutualFunds;
    }
}
