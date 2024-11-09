package model;

public class OrderLine {

    private int productID;
    private int orderID;
    private String productName;
    private double price;
    private int quantity;
    private double cost;
    private double discount;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        this.cost = this.quantity * this.price * this.discount;
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setDiscount(double discount) {this.discount = discount;}

    public double getDiscount() {return  this.discount;}

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}