package vttp.batch5.csf.assessment.server.Model;

public class OrderItems {
    private String id;
    private double price;
    private int quantity;
    private double total;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return "OrderItems [id=" + id + ", price=" + price + ", quantity=" + quantity + ", total=" + total + "]";
    }

    

    
}
