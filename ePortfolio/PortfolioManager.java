import java.util.ArrayList;

public class PortfolioManager {

    private ArrayList<Investment> investments;

    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    public Investment checkInvestment(String symb) {
        if (investments != null) {
            for (Investment investments : investments) {
                if (investments.getSymbol().equalsIgnoreCase(symb)) {
                    return investments;
                } else if (investments.getName().contains(symb)) {
                    return investments;
                }
            }
        }
        return null;
    }

    public Investment rangeInvestment(double upper, double lower) {
        if (investments != null) {
            for (Investment investments : investments) {
                if (investments.getPrice() < upper && investments.getPrice() > lower) {
                    return investments;
                }
            }
        }
        return null;
    }

    public void addInvestments(Investment investment) {
        if (investments == null) {
            investments = new ArrayList<>();
        }
        investments.add(investment);
    }
}
