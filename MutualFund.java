public class MutualFund {

    public String symbol;
    public String name;
    public int quantity;
    public double price;
    public double bookValue;
    public double oldPrice;

    public MutualFund(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getBookValue() {
        return bookValue;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String toString() {
        return "\nSymbol : " + symbol + "\nName : " + name + "\nQuantity : " + quantity + "\nPrice : " + price
                + "\nBook Value : " + bookValue + "\n";
    }

}
