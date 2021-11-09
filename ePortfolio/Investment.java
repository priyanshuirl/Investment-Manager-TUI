public class Investment {
    public String symbol;
    public String name;
    public int quantity;
    public double price;
    public double bookValue;
    private double oldPrice;
    public int sellqty;
    public String type;

    public Investment(String symbol, String name, int quantity, double price, String type) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
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

    public int getSellQty() {
        return sellqty;
    }

    public String getType() {
        return type;
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

    public void sellqty(int sellqty) {
        this.sellqty = sellqty;
    }

    public String toString() {
        return "Type : " + type + "\nSymbol : " + symbol + "\nName : " + name + "\nQuantity : " + quantity
                + "\nPrice : " + price + "\nBook Value : " + bookValue + "\n";
    }

    public String toFileString() {
        return type + "-" + symbol + "-" + name + "-" + quantity + "-" + price + "-" + bookValue + " ";
    }
    public void add(Investment investment) {
    }
}
