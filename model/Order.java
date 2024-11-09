package model;
import java.util.*;

public class Order {
    private int orderID;
    private int customerID;
    private String OrderDate;
    private float TotalCost;
    private User user;
    private List<OrderLine> lines;
    private String customerName;


    public void setOrderID(int orderID) {this.orderID = orderID;}
    public int getOrderID() {return orderID;}

    public void setCustomerID(int customerId) {this.customerID = customerId;}
    public int getCustomerID() {return customerID;}


    public void setOrderDate(String OrderDate) {this.OrderDate = OrderDate;}
    public String getOrderDate() {return OrderDate;}

    public void setTotalCost(float TotalCost) {this.TotalCost = TotalCost;}
    public float getTotalCost() {return TotalCost;}

    public List<OrderLine> getLines() {return lines;}
    public void setLines(List<OrderLine> lines) {this.lines = lines;}


    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public String getCustomerName() {return customerName;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
}