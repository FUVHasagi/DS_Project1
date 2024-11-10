package controller;

import model.*;

import DAO.DataAccess;
import view.QueryTableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class QueryTableController {
    private QueryTableView view;
    private DataAccess dataAccess;

    public QueryTableController(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        this.view = new QueryTableView();
    }

    // This method is called to display the table based on table name
    public void showTable(String tableName) {
        // Set the appropriate columns for the table
        this.view.setTableColumns(tableName);

        // Fetch the data from DataAccess and display it in the JTable
        List<Object[]> data = fetchDataForTable(tableName);
        if (data != null) {
            view.setTableData(data);
        }
        else {
            JOptionPane.showMessageDialog(view, "Data not found");
        }
    }

    public void showOrderLine(int orderID){
        Order order = new Order();
        order = dataAccess.getOrderByID(orderID);
        if (order != null) {
            // Set the appropriate columns for the table
            this.view.getLabTotal().setText("Total: "+ order.getTotalCost() + " $");
            this.view.setTableColumns("OrderLines");
            this.view.setTableData(fetchOrderLines(orderID));
        }
        else {
            JOptionPane.showMessageDialog(view, "Order not found");
        }
    }

    public void showImportOrderLine(int importOrderID){
        ImportOrder importOrder = new ImportOrder();
        importOrder = dataAccess.getImportOrderByID(importOrderID);
        if (importOrder != null) {
            this.view.getLabTotal().setText("Total: "+ importOrder.getTotalCost() + " $");
            this.view.setTableColumns("ImportOrderLines");
            this.view.setTableData(fetchImportOrderLines(importOrderID));
        }
        else {
            JOptionPane.showMessageDialog(view, "ImportOrder not found");
        }
    }

    private List<Object[]> fetchOrderLines(int orderID){
        List<OrderLine> orderLines = dataAccess.getOrderLinesByOrderID(orderID);
        List<Object[]> data = new ArrayList<>();
        for (OrderLine orderLine : orderLines) {
            Object[] row = new Object[4];
            row[0] = orderLine.getProductName();
            row[1] = orderLine.getQuantity();
            row[2] = orderLine.getDiscount();
            row[3] = orderLine.getPrice();
            data.add(row);
        }
        return data;
    }


    private List<Object[]> fetchImportOrderLines(int orderID){
        List<ImportOrderLine> orderLines = dataAccess.getImportOrderLinesByOrderID(orderID);
        List<Object[]> data = new ArrayList<>();
        for (ImportOrderLine orderLine : orderLines) {
            Object[] row = new Object[4];
            row[0] = orderLine.getProductName();
            row[1] = orderLine.getQuantity();
            row[2] = orderLine.getCost();
            data.add(row);
        }
        return data;
    }
    // Fetch data based on the table name
    private List<Object[]> fetchDataForTable(String tableName) {
        switch (tableName) {
            case "Roles":
                return dataAccess.getAllRoles();
            case "Users":
                return dataAccess.getAllUsers();
            case "Products":
                return dataAccess.getAllProducts();
            case "Customers":
                return dataAccess.getAllCustomers();
            case "Orders":
                return dataAccess.getAllOrders();
            case "ImportOrders":
                return dataAccess.getAllImportOrders();
            default:
                return null;
        }
    }
}
