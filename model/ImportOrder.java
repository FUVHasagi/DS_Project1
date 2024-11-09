package model;

import java.util.ArrayList;
import java.util.List;

public class ImportOrder {
    private int orderId;
    private int vendorID;
    private String OrderDate;
    private float TotalCost;
    private List<ImportOrderLine> lines =  new ArrayList<ImportOrderLine>();

    public void setOrderId(int orderId) {this.orderId = orderId;}
    public int getOrderId() {return orderId;}

    public void setCustomerId(int vendorId) {this.vendorID = vendorId;}
    public int getCustomerId() {return vendorID;}


    public void setOrderDate(String OrderDate) {this.OrderDate = OrderDate;}
    public String getOrderDate() {return OrderDate;}

    public void setTotalCost(float TotalCost) {this.TotalCost = TotalCost;}
    public float getTotalCost() {return TotalCost;}
}