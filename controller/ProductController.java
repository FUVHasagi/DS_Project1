package controller;

import DAO.DataAccess;
import model.Product;
import view.ProductView;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ProductController implements ActionListener {
    private DataAccess dataAccess;
    private ProductView productView;
    private Product product;
    private int productId;
    private int quantity;
    private float discount;
    private float price;
    private float totalCost;
    private String mode;
    private User user;
    private SelectionListener selectionListener;

    public ProductController(DataAccess dataAccess, String mode, User user) {
        this.mode = mode;
        this.dataAccess = dataAccess;
        this.productView = new ProductView(this.mode);

        this.productView.setActionListener(this);

        this.user = user;

        this.quantity = 0;
        this.discount = 0;
        this.price = 0;
        this.totalCost = 0;
    }


    public void setSelectionListener(SelectionListener listener) {
        this.selectionListener = listener;
    }

    public void showProductView() {
        productView.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == productView.getProductIdField() || e.getSource() == productView.getActionButtonAdd()) {
            productId = productView.getProductId();
            Product product1 = this.dataAccess.findProductById(productId);

            // Check if product was found
            if (product1 != null) {
                product = product1;
                productView.setProductNameField(product1.getName());
                switch (this.mode){
                    case "SELLING":
                        productView.setSellPriceField(product1.getSellPrice());
                        break;

                    case "IMPORT":
                        productView.setImportPriceField(product1.getImportPrice());
                        break;
                    case "QUERY":
                        productView.setQuantityField(product1.getQuantity());
                        productView.setImportPriceField(product1.getImportPrice());
                        productView.setSellPriceField(product1.getSellPrice());
                        break;
                }

            } else {
                // Show warning if product is not found
                JOptionPane.showMessageDialog(productView, "Product with ID " + productId + " not found.");
            }
        }


        // If input quantity

        switch (mode) {
            case "SELLING":
                if (e.getSource() == productView.getQuantityField() ||
                e.getSource() == productView.getDiscountField() ||
                e.getSource() == productView.getActionButtonAdd()){
                    this.quantity = productView.getQuantity();
                    this.discount = productView.getDiscount();
                    this.price = productView.getPrice();
                    productView.setTotalCostField(getTotalCost());
                }
                if (e.getSource() == productView.getActionButtonAdd()) {
                    if (checkValidity()) {
                        OrderLine orderLine = getOrderLinefromView();
                        if (selectionListener != null && orderLine != null) {
                            OrderLine orderLine1 = new OrderLine();
                            orderLine1.setProductID(this.productView.getProductId());
                            orderLine1.setProductName(this.productView.getProductName());
                            orderLine1.setPrice(this.productView.getPrice());
                            orderLine1.setQuantity(this.productView.getQuantity());
                            orderLine1.setDiscount(this.productView.getDiscount());

                            this.totalCost = (float) ((this.productView.getPrice() * this.productView.getQuantity() * (1.0- (this.productView.getDiscount()/100.0))));
                            this.productView.setTotalCostField(this.totalCost);
                            orderLine1.setCost(this.totalCost);

                            selectionListener.onProductSelected(orderLine1);

                            productView.setVisible(false); // Close ProductView after selection
                            productView.resetView();
                        }
                    } else {
                        JOptionPane.showMessageDialog(productView, "Please select a valid product to sell.");
                    }
                }
                break;

            case "IMPORT":
                if (e.getSource() == productView.getQuantityField() ||
                e.getSource() == productView.getActionButtonAdd()){
                    this.quantity = productView.getQuantity();
                    this.price = productView.getPrice();
                    this.totalCost = this.productView.getPrice()*this.productView.getQuantity();
                    productView.setTotalCostField(totalCost);
                }
                if (e.getSource() == productView.getActionButtonAdd()) {
                    if (checkValidity()) {
                        ImportOrderLine importOrderLine = getImportOrderLinefromView();
                        if (selectionListener != null && importOrderLine != null) {
                            this.quantity = productView.getQuantity();
                            this.price = productView.getPrice();
                            this.totalCost = this.productView.getPrice()*this.productView.getQuantity();
                            productView.setTotalCostField(totalCost);

                            // Import OrderLine
                            ImportOrderLine importOrderLine1 = new ImportOrderLine();
                            importOrderLine1.setProductID(this.productView.getProductId());
                            importOrderLine1.setProductName(this.productView.getProductName());
                            importOrderLine1.setPrice(this.productView.getPrice());
                            importOrderLine1.setQuantity(this.productView.getQuantity());
                            importOrderLine1.setCost(this.totalCost);

                            selectionListener.onProductSelected(importOrderLine1);
                            productView.resetView();
                            productView.setVisible(false); // Close ProductView after selection

                        }
                    }
                }
                break;

            case "QUERY":
                if (e.getSource() == productView.getActionButtonAdd()) {
                    queryProduct();
                }
                if (e.getSource() == productView.getActionButtonSave()) {
                    if (user.getRole().equals("Manager")) {
                        updateProductInfo();
                        productView.resetView();
                    } else {
                        JOptionPane.showMessageDialog(productView, "You do not have permission to update product information.");
                    }
                }

                if (e.getSource() == productView.getActionButtonDelete()){
                    if (user.getRole().equals("Manager")) {
                        deleteProductInfo();
                        productView.resetView();
                        productView.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(productView, "You do not have permission to update product information.");
                    }
                }
                break;
        }
    }

    private void deleteProductInfo() {
        int ID = this.productView.getProductId();
        Product product = this.dataAccess.findProductById(ID);
        if (product == null) {
            JOptionPane.showMessageDialog(productView, "Product with ID " + ID + " not found.");
        }
        else {
            int response = JOptionPane.showConfirmDialog(productView, "Are you sure you want to delete product " + product.getName() + "?",
                    "Confirm delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                this.dataAccess.deleteProductByID(productId);
            } else if (response == JOptionPane.NO_OPTION) {
                productView.resetView();
                productView.setVisible(false);
            }


        }
    }

    private void queryProduct() {
        // Query logic for product
        int id = productView.getProductId();
        Product product = this.dataAccess.findProductById(id);
        if (product != null) {
            setProductView(product);
        } else {
            JOptionPane.showMessageDialog(null, "Product not found");
        }
    }

    public Object[] getViewInputValue(){
        this.productId = productView.getProductId();
        this.quantity = productView.getQuantity();
        this.price = productView.getPrice();
        this.discount = productView.getDiscount();
        this.totalCost = productView.getTotalCost();
        Object[] inputValue = {this.productId, this.quantity, this.price, this.discount, this.totalCost};
        return inputValue;
    }

    public void getProductInformation(int id) {
        Product product = this.dataAccess.findProductById(id);
        if (product != null) {
            this.product = product;
        }
        else {
            JOptionPane.showMessageDialog(null, "Product not found");
        }
    }

    public void setProductView(Product product) {
        this.productView.setProductIdField(product.getId());
        this.productView.setProductNameField(product.getName());
        this.productView.setImportPriceField(product.getImportPrice());
        this.productView.setSellPriceField(product.getSellPrice());
        if (Objects.equals(mode, "QUERY")) {
            this.productView.setQuantityField(product.getQuantity());
        }
    }

    public Product getProductfromView() {
        Product product = new Product();
        product.setId(this.productView.getProductId());
        product.setName(this.productView.getProductName());
        product.setImportPrice(product.getImportPrice());
        product.setSellPrice(product.getSellPrice());
        product.setQuantity(this.productView.getQuantity());

        return product;
    }

    public OrderLine getOrderLinefromView() {
        if (Objects.equals(mode, "SELLING")){
            OrderLine orderLine1 = new OrderLine();
            orderLine1.setProductID(this.productView.getProductId());
            orderLine1.setProductName(this.productView.getProductName());
            orderLine1.setPrice(this.productView.getPrice());
            orderLine1.setQuantity(this.productView.getQuantity());
            orderLine1.setDiscount(this.productView.getDiscount());
            return orderLine1;
        }
        return null;
    }

    public boolean checkValidity(){
        if (this.dataAccess.findProductById(this.productView.getProductId()) == null) {
            JOptionPane.showMessageDialog(null, "Product not found");
            return false;
        }

        Product stock = this.dataAccess.findProductById(this.productView.getProductId());

        if (this.productView.getQuantity() > stock.getQuantity() && this.mode == "SELLING") {
            JOptionPane.showMessageDialog(null, "Quantity is higher than stock");
            return false;
        }

        if (this.productView.getQuantity() <= 0) {
            JOptionPane.showMessageDialog(null, "Quantity must be greater than 0");
            return false;
        }
    if (mode == "SELLING") {
        if (this.productView.getDiscount() < 0) {
            JOptionPane.showMessageDialog(null, "Discount must be greater than 0");
            return false;
        }

        if (this.productView.getDiscount() >= 100) {
            JOptionPane.showMessageDialog(null, "Discount must be less than 100");
            return false;
        }
    }

        return true;
    }

    public ImportOrderLine getImportOrderLinefromView() {
        if (Objects.equals(mode, "IMPORT")){
            ImportOrderLine importOrderLine = new ImportOrderLine();
            importOrderLine.setProductID(this.productView.getProductId());
            importOrderLine.setQuantity(this.productView.getQuantity());
            importOrderLine.setCost(this.productView.getPrice()*this.productView.getQuantity());
            return importOrderLine;
        }
        return null;
    }

    public double getTotalCost() {
        this.totalCost = (float) (this.productView.getPrice() * this.productView.getQuantity() * (1.0- (this.productView.getDiscount()/100.0)));
        return this.totalCost;
    }

    public ProductView getProductView() {
        return this.productView;
    }

    private void updateProductInfo() {
        Product product = getProductfromView();
        this.dataAccess.saveProduct(product);
        JOptionPane.showMessageDialog(null, "Product information updated.");
    }
}