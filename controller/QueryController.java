package controller;

import DAO.DataAccess;
import model.*;
import view.QueryView;
import view.ProductView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class QueryController implements ActionListener, SelectionListener{
    private QueryView queryView;
    private User user;
    private DataAccess dataAccess;
    private QueryTableController queryTableController;
    private CustomerController customerController;


    public QueryController(User user, DataAccess dataAccess) {
        this.queryView = new QueryView();
        this.user = user;
        this.queryView.setActionListener(this);


        this.dataAccess = dataAccess;
        this.queryTableController = new QueryTableController(dataAccess);
        this.queryView.setVisible(true);
        System.out.println("Query called from Query controller");
    }

    private QueryView getQueryView() {
        return queryView;
    }

    public void showWindow(){
        this.queryView.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String queryType = queryView.getSelectedQueryType();
        if (e.getSource() == this.queryView.getQueryButton()){
            switch (queryType) {
                case "Products":
                    ProductController productController = new ProductController(this.dataAccess, "QUERY", user);
                    productController.showProductView();
                    break;
                case "Users":
                    JOptionPane.showMessageDialog(queryView, "This function is not yet implemented");
                    // Handle User Query
                    break;
                case "Customers":
                    customerController = new CustomerController(this.dataAccess, "QUERY", user);
                    customerController.showCustomerView();
                    customerController.setSelectionListener(this);
                    break;

                case "Orders":
                    int orderID = -1;
                    boolean validInput = false;
                    while (!validInput) {
                        String input = JOptionPane.showInputDialog(null, "Please the OrderID:", "OrderID", JOptionPane.PLAIN_MESSAGE);

                        if (input == null) {
                            // If the user presses "Cancel", exit or handle as needed
                            JOptionPane.showMessageDialog(null, "No input provided. Returning to Query Window.");
                            orderID = -1;
                            break;
                        }

                        try {
                            // Try to parse the input as an integer
                            orderID = Integer.parseInt(input);
                            QueryTableController queryTableController = new QueryTableController(dataAccess);
                            queryTableController.showOrderLine(orderID);
                            validInput = true; // If parsing is successful, exit the loop
                        } catch (NumberFormatException ne) {
                            // If parsing fails, show an error message and re-prompt
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case "ImportOrders":
                    int importOrderID = -1;
                    validInput = false;
                    while (!validInput) {
                        String input = JOptionPane.showInputDialog(null, "Please the ImportOrderID:", "ImportOrderID", JOptionPane.PLAIN_MESSAGE);
                        if (input == null) {
                            // If the user presses "Cancel", exit or handle as needed
                            JOptionPane.showMessageDialog(null, "No input provided. Returning to Query Window.");
                            orderID = -1;
                            break;
                        }
                        try {
                            // Try to parse the input as an integer
                            importOrderID = Integer.parseInt(input);
                            QueryTableController queryTableController = new QueryTableController(dataAccess);
                            queryTableController.showImportOrderLine(importOrderID);
                            validInput = true; // If parsing is successful, exit the loop
                        } catch (NumberFormatException ne) {
                            // If parsing fails, show an error message and re-prompt
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                default:
                    break;
            }

        }
        else if (e.getSource() == this.queryView.getGetAllButton()){
            if (user.getRole().equals("Manager")){
                QueryTableController queryTableController = new QueryTableController(dataAccess);
                queryTableController.showTable(queryType);
            }
            else {
                JOptionPane.showMessageDialog(queryView, "Only the Manager is allowed to see the full information");
            }

        }
    }

    @Override
    public void onProductSelected(OrderLine OrderLine) {
        return;
    }

    @Override
    public void onProductSelected(ImportOrderLine importOrderLine) {
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
    public void onSaveSelected(String tableName) {
        this.queryTableController = new QueryTableController(dataAccess);
        this.queryTableController.showTable(tableName);
    }
}