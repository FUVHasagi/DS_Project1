package controller;

import DAO.DataAccess;
import model.*;
import view.SellView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CheckoutController implements ActionListener, SelectionListener {
    private DataAccess dataAccess;
    private SellView sellView;
    private ProductController productController;
    private List<OrderLine> orderLines;
    private Order order;
    private double totalPrice;
    private CustomerController customerController;
    private Customer customer;
    private ImportOrderLine importOrderLine;

    @Override
    public void onProductSelected(ImportOrderLine importOrderLine) {
        this.importOrderLine = importOrderLine;
        return;
    }

    public void onCustomerSelected(Customer customer) {
        if (customer != null) {
            this.customer = customer;
            order.setCustomerID(customer.getCustomerID());
        }
    }

    @Override
    public void onUserSeletected(User user) {
        return;
    }

    @Override
    public void onSaveSelected(String customer) {
        return;
    }

    public CheckoutController(DataAccess dataAccess, User user) {
        this.dataAccess = dataAccess;
        this.productController = new ProductController(this.dataAccess, "SELLING", user);
        this.customerController = new CustomerController(this.dataAccess,"SELLING", user);

        this.sellView = new SellView();
        this.orderLines = new ArrayList<>();
        this.totalPrice = 0;
        this.productController.setSelectionListener(this);
        this.customerController.setSelectionListener(this);
        this.order = new Order();

        // Initialize view listeners
        this.sellView.getBtnAdd().addActionListener(this);
        this.sellView.getBtnPay().addActionListener(this);
        this.sellView.getBtnCustomer().addActionListener(this);
    }

    public void showWindow() {
        this.sellView.setVisible(true);
    }


    public void onProductSelected(OrderLine orderLine) {
        if (orderLine != null) {
            orderLines.add(orderLine);
            sellView.addRow(new Object[]{
                    orderLine.getProductID(),
                    orderLine.getProductName(),
                    orderLine.getPrice(),
                    orderLine.getQuantity(),
                    orderLine.getDiscount(),
                    orderLine.getCost()
            });
            updateTotalPrice(orderLine.getCost());
        }
    }

    private void updateTotalPrice(double cost) {
        this.totalPrice += cost;
        this.sellView.getLabTotal().setText("Total: $" + this.totalPrice);
    }

    private void makeOrder() {

        if (orderLines.size() <= 0) {
            JOptionPane.showMessageDialog(sellView, "Please select at least one order line");
            return;
        }
        // Order
        int orderId = dataAccess.createOrder(this.customer, this.totalPrice);

        // OrderLine
        if (orderId != -1) {
            for (OrderLine line : orderLines) {
                dataAccess.createOrderLine(orderId, line.getProductID(), line.getQuantity(), line.getDiscount(), line.getCost());
                updateProductQuantity(line.getProductID(), line.getQuantity());
            }
            JOptionPane.showMessageDialog(sellView, "Order created successfully!");
        } else {
            JOptionPane.showMessageDialog(sellView, "Error creating order.");
        }
    }

    private void updateProductQuantity(int productId, int quantityChange) {
        Product product = dataAccess.findProductById(productId);
        if (product != null) {
            int newQuantity = product.getQuantity() + quantityChange;
            if (newQuantity >= 0) { // Ensure that quantity doesn't go negative
                product.setQuantity(newQuantity);
                dataAccess.updateProduct(product);
            } else {
                JOptionPane.showMessageDialog(sellView, "Not enough stock for this product.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sellView.getBtnAdd()) {
            productController.showProductView();
        } else if (e.getSource() == sellView.getBtnPay()) {
            makeOrder();
        }
        else if (e.getSource() == sellView.getBtnCustomer()) {
            customerController.showCustomerView();
        }
    }

}
