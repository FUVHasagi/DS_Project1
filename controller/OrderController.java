package controller;

import DAO.DataAccess;
import view.OrderView;
import model.Order;
import model.OrderLine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderController implements ActionListener {
    private OrderView orderView;
    private DataAccess dataAccess;

    public OrderController(DataAccess dataAccess) {
        this.orderView = new OrderView();
        this.dataAccess = dataAccess;
        this.orderView.setQueryButtonListener(this);
    }

    public void showOrderView() {
        orderView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String orderID = orderView.getOrderID();
        if (orderID.isEmpty()) {
            JOptionPane.showMessageDialog(orderView, "Please enter an Order ID.");
            return;
        }

        Order order = dataAccess.getOrderByID(Integer.parseInt(orderID));
        if (order != null) {
            orderView.setOrderDetails(order.getCustomerName(), order.getOrderDate(), order.getUser().getDisplayName(), order.getTotalCost());
            showOrderLines(order.getOrderID());
        } else {
            JOptionPane.showMessageDialog(orderView, "Order not found.");
            orderView.clearFields();
        }
    }

    private void showOrderLines(int orderID) {
        List<OrderLine> orderLines = dataAccess.getOrderLinesByOrderID(orderID);
        if (orderLines.isEmpty()) {
            JOptionPane.showMessageDialog(orderView, "No items found for this order.");
            return;
        }

        String[] columnNames = {"Product Name", "Quantity", "Sell Price", "Discount", "Total Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (OrderLine line : orderLines) {
            Object[] rowData = {
                    line.getProductName(),
                    line.getQuantity(),
                    line.getPrice(),
                    line.getDiscount(),
                    line.getCost()
            };
            tableModel.addRow(rowData);
        }


        JTable orderLinesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderLinesTable);
        JOptionPane.showMessageDialog(orderView, scrollPane, "Order Lines", JOptionPane.PLAIN_MESSAGE);
    }
}
