package model;

import java.util.ArrayList;
import java.util.List;

public class ImportOrder {
    private int orderId;
    private int vendorID;
    private String OrderDate;
    private double TotalCost;
    private List<ImportOrderLine> lines =  new ArrayList<ImportOrderLine>();

    public void setImportOrderId(int orderId) {this.orderId = orderId;}
    public int getOrderId() {return orderId;}

    public void setCustomerId(int vendorId) {this.vendorID = vendorId;}
    public int getCustomerId() {return vendorID;}


    public void setDate(String OrderDate) {this.OrderDate = OrderDate;}
    public String getDate() {return OrderDate;}

    public void setTotalCost(double TotalCost) {this.TotalCost = TotalCost;}
    public double getTotalCost() {return TotalCost;}

    public void setLines(List<ImportOrderLine> lines) {this.lines = lines;}
    public List<ImportOrderLine> getLines() {return lines;}
}