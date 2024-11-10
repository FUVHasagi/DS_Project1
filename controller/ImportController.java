package controller;

import DAO.DataAccess;
import model.*;
import view.ImportView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ImportController implements ActionListener, SelectionListener {
    private DataAccess dataAccess;
    private ImportView view;
    private ProductController productController;
    private List<ImportOrderLine> orderLines;
    private ImportOrder importOrder;
    private double totalPrice;
    private User user;

    public ImportController(DataAccess dataAccess, User user) {
        this.dataAccess = dataAccess;
        this.productController = new ProductController(this.dataAccess, "IMPORT", user);
        this.view = new ImportView();
        this.orderLines = new ArrayList<>();
        this.totalPrice = 0;
        this.productController.setSelectionListener(this);
        this.user = user;

        // Initialize view listeners
        this.view.getBtnAdd().addActionListener(this);
        this.view.getBtnPay().addActionListener(this);
    }

    public void showWindow() {
        this.view.setVisible(true);
    }

    @Override
    public void onProductSelected(OrderLine OrderLine) {
        return;
    }

    @Override
    public void onCustomerSelected(Customer customer) {
        return;
    }

    @Override
    public void onUserSeletected(User user) {
        return;
    }

    @Override
    public void onSaveSelected(String customer) {
        return;
    }


    @Override
    // Add ImportOrderLine to the list
    public void onProductSelected(ImportOrderLine importOrderLine) {
        if (importOrderLine != null) {
            orderLines.add(importOrderLine);
            view.addRow(new Object[]{
                    importOrderLine.getProductID(),
                    importOrderLine.getProductName(),
                    importOrderLine.getPrice(),
                    importOrderLine.getQuantity(),
                    importOrderLine.getCost()
            });
            updateTotalPrice(importOrderLine.getCost());
        }
    }

    private void updateTotalPrice(double cost) {
        this.totalPrice += cost;
        this.view.getLabTotal().setText("Total: $" + this.totalPrice);
    }


    private void makeOrder() {
        if (orderLines.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select at least one import order line");
            return;
        }
        int importOrderId = dataAccess.createImportOrder(this.totalPrice, this.user);
        if (importOrderId != -1) {
            for (ImportOrderLine line : orderLines) {
                this.dataAccess.createImportOrderLine(importOrderId, line.getProductID(), line.getQuantity(), line.getCost());
                updateProductQuantity(line.getProductID(), line.getQuantity());
            }
            JOptionPane.showMessageDialog(view, "Import order created successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Error creating import order.");
        }
    }

    private void updateProductQuantity(int productId, int quantityChange) {
        Product product = dataAccess.findProductById(productId);
        if (product != null) {
            int newQuantity = product.getQuantity() + quantityChange;
            product.setQuantity(newQuantity);
            dataAccess.updateProduct(product);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdd()) {
            productController.showProductView();
        } else if (e.getSource() == view.getBtnPay()) {
            makeOrder();
        }
    }
}
