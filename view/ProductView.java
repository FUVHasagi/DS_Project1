package view;

import controller.ProductController;
import model.Order;
import model.OrderLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class ProductView extends JFrame {
    private JTextField productIdField, productNameField, quantityField, importPriceField, sellPriceField, totalCostField, discountField;
    private JButton actionButtonAdd;
    private JButton actionButtonSave;
    private JButton actionButtonDelete;
    private String mode;


    public ProductView(String mode) {
        this.mode = mode;
        setTitle("Product View");
        setSize(400, 300);

        setLocationRelativeTo(null);

        initUI();
        setMode(mode);
    }

    private void initUI() {
        // Initialize the main panel with a BorderLayout to allow switching panels
        setLayout(new BorderLayout());
        actionButtonAdd = new JButton();
        actionButtonSave = new JButton();
        actionButtonDelete = new JButton();
        // Add actionButton at the bottom
        add(actionButtonAdd, BorderLayout.SOUTH);

        // Set the mode to initialize the panel
        setMode(mode);
    }

    public void resetView() {
        // Reset the productIdField, productNameField, and quantityField
        productIdField.setText("");
        productNameField.setText("");
        quantityField.setText("0"); // Default quantity is 0

        // Reset specific fields based on the mode
        switch (mode) {
            case "SELLING":
                sellPriceField.setText(""); // Reset sell price field
                discountField.setText("0"); // Default discount to 0
                totalCostField.setText(""); // Reset total cost
                break;

            case "QUERY":
                importPriceField.setText(""); // Reset import price
                sellPriceField.setText(""); // Reset sell price
                break;

            case "IMPORT":
                importPriceField.setText(""); // Reset import price
                totalCostField.setText(""); // Reset total cost
                break;
        }
    }

    private void setMode(String mode) {
        this.mode = mode;

        // Remove the existing center panel if it exists
        getContentPane().removeAll();

        // Rebuild the panel based on the current mode
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdField = new JTextField();
        panel.add(productIdLabel);
        panel.add(productIdField);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameField = new JTextField();
        panel.add(productNameLabel);
        panel.add(productNameField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField("0");
        panel.add(quantityLabel);
        panel.add(quantityField);

        JLabel priceLabel;
        switch (mode) {
            case "SELLING":
                actionButtonAdd.setText("Add Product");
                productIdField.setEditable(true);
                productNameField.setEditable(false);
                quantityField.setEditable(true);

                priceLabel = new JLabel("Sell Price:");
                sellPriceField = new JTextField();
                panel.add(priceLabel);
                panel.add(sellPriceField);

                JLabel discountLabel = new JLabel("Discount:");
                discountField = new JTextField("0");
                panel.add(discountLabel);
                panel.add(discountField);

                JLabel totalCostLabel = new JLabel("Total Cost:");
                totalCostField = new JTextField();
                totalCostField.setEditable(false);
                panel.add(totalCostLabel);
                panel.add(totalCostField);


                break;

            case "QUERY":
                actionButtonAdd.setText("Query Product");
                actionButtonSave.setText("Save Modification");
                actionButtonDelete.setText("Delete Product");
                productIdField.setEditable(true);
                productNameField.setEditable(true);
                quantityField.setEditable(true);

                JLabel importPriceLabel = new JLabel("Import Price:");
                importPriceField = new JTextField();
                panel.add(importPriceLabel);
                panel.add(importPriceField);

                JLabel sellPriceLabel = new JLabel("Sell Price:");
                sellPriceField = new JTextField();
                panel.add(sellPriceLabel);
                panel.add(sellPriceField);



                break;


            case "IMPORT":
                actionButtonAdd.setText("Import Product");
                productIdField.setEditable(true);
                productNameField.setEditable(false);
                quantityField.setEditable(true);

                priceLabel = new JLabel("Import Price:");
                importPriceField = new JTextField();
                importPriceField.setEditable(false);
                panel.add(priceLabel);
                panel.add(importPriceField);

                totalCostLabel = new JLabel("Total Cost:");
                totalCostField = new JTextField();
                totalCostField.setEditable(false);
                panel.add(totalCostLabel);
                panel.add(totalCostField);



                break;
        }

        // Add the new panel and refresh the UI
        add(panel, BorderLayout.CENTER);
        if (!(mode == "QUERY")){
            add(actionButtonAdd, BorderLayout.SOUTH);
        }
        else {
            JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
            buttonPanel.add(actionButtonAdd);
            buttonPanel.add(actionButtonSave);
            buttonPanel.add(actionButtonDelete);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        revalidate();
        repaint();
    }

    public void setProductIdField(int id){
        this.productIdField.setText(String.valueOf(id));
    }

    public void setProductNameField(String productName){
        this.productNameField.setText(productName);
    }

    public void setQuantityField(int quantity){
        this.quantityField.setText(String.valueOf(quantity));
    }

    public void setImportPriceField(String price){
        this.importPriceField.setText(price);
    }

    public void setSellPriceField(String price){
        this.sellPriceField.setText(price);
    }

    public void setImportPriceField(double price){
        this.importPriceField.setText(String.valueOf(price));
    }

    public void setSellPriceField(double price){
        this.sellPriceField.setText(String.valueOf(price));
    }

    public void setTotalCostField(double cost){
        this.totalCostField.setText(String.valueOf(cost));
    }

    public int getProductId(){
        return parseInt(this.productIdField.getText());
    }

    public String getProductName(){
        return this.productNameField.getText();
    }

    public int getQuantity(){
        return parseInt(this.quantityField.getText());
    }

    public float getDiscount(){
        return Float.parseFloat(this.discountField.getText());
    }

    public float getPrice(){
        switch (this.mode){
            case "SELLING":
                return Float.parseFloat(this.sellPriceField.getText());
            case "IMPORT":
                return Float.parseFloat(this.importPriceField.getText());
        }
        return 0;
    }

    public float getTotalCost(){
        return Float.parseFloat(this.totalCostField.getText());
    }
    public JButton getActionButtonDelete(){
        return this.actionButtonDelete;
    }

    // Setup listener
    public void setActionListener(ActionListener listener){
        this.actionButtonAdd.addActionListener(listener);
        this.actionButtonSave.addActionListener(listener);
        this.productIdField.addActionListener(listener);
        this.quantityField.addActionListener(listener);
        if (this.discountField != null){
            this.discountField.addActionListener(listener);
        }
        this.actionButtonDelete.addActionListener(listener);
    }

    // Function to access the button
    public JButton getActionButtonAdd(){
        return this.actionButtonAdd;
    }

    public JTextField getDiscountField() {
        return this.discountField;
    }

    public JButton getActionButtonSave(){
        return this.actionButtonSave;
    }

    public JTextField getProductIdField(){
        return this.productIdField;
    }

    public JTextField getQuantityField() {
        return this.quantityField;
    }
}
