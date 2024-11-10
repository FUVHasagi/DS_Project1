package controller;

import model.Customer;
import model.User;
import view.CustomerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import DAO.*;

import static java.lang.Integer.parseInt;

public class CustomerController implements ActionListener {
    private CustomerView view;
    private DataAccess dataAccess;
    private SelectionListener selectionListener;
    private String mode;

    public CustomerController(DataAccess dataAccess, String mode, User User) {
        this.view = new CustomerView(mode);
        this.dataAccess = dataAccess;
        this.selectionListener = selectionListener;
        this.mode = mode;

        view.addQueryButtonListener(this);
        view.addActionButtonListener(this);
    }


    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void showCustomerView() {
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.view.getQueryButton()) {
            try {
                handleQueryCustomer();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == this.view.getActionButton()) {
            switch (this.mode) {
                case "SELLING":
                    String customerId = view.getCustomerId();
                    if (customerId.isEmpty()) {
                        selectionListener.onCustomerSelected(null);
                        view.setVisible(false);
                        return;
                    }

                    Customer customer = null;
                    try {
                        customer = this.dataAccess.getCustomerByID(parseInt(customerId));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (customer == null) {
                        JOptionPane.showMessageDialog(view, "No customer found with ID " + customerId);
                        return;
                    }

                    selectionListener.onCustomerSelected(customer);
                    view.dispose();
                    break;
                case "QUERY":
                    try {
                        handleSaveOrAddCustomer();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    selectionListener.onSaveSelected("Customer");
            }
        }
    }

    private void handleQueryCustomer() throws SQLException {
        String customerId = view.getCustomerId();
        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Customer ID is required for querying.");
            return;
        }

        Customer customer = this.dataAccess.getCustomerByID(parseInt(customerId));
        if (customer != null) {
            view.setCustomerName(customer.getName());
            view.setPhoneNumber(customer.getPhone());
            view.setAddress(customer.getAddress());
//            JOptionPane.showMessageDialog(view, "Customer details loaded.");
        } else {
            JOptionPane.showMessageDialog(view, "Customer not found.");
        }

    }

    private void handleSaveOrAddCustomer() throws SQLException {
        Customer customer = this.dataAccess.getCustomerByID(parseInt(view.getCustomerId()));
        if (customer != null) {
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "There has been a customer with this ID. Are you sure you want to Modify the information?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                // Code to execute if the user clicked "Yes"

            } else if (response == JOptionPane.NO_OPTION) {
                // Code to execute if the user clicked "No"
                return;
            }
        }

        else {
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to register new Customer?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                // Code to execute if the user clicked "Yes"

            } else if (response == JOptionPane.NO_OPTION) {
                // Code to execute if the user clicked "No"
                return;
            }
        }

        if (checkNoBlank()){
            try {
                if (customer == null) customer = new Customer();
                customer.setCustomerID(parseInt(view.getCustomerId()));
                customer.setName(view.getCustomerName());
                customer.setPhone(view.getPhoneNumber());
                customer.setAddress(view.getAddress());
                dataAccess.saveCustomer(customer);

                selectionListener.onSaveSelected("CUSTOMER");
                view.dispose();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Customer saving unsuccessful. Please check input or database");
                return;
            }

        }

    }

    private boolean checkNoBlank() {
        String customerId = view.getCustomerId();
        String customerName = view.getCustomerName();
        String phoneNumber = view.getPhoneNumber();
        String address = view.getAddress();
        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please specify customer ID");
            return false;
        }
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please specify customer Name");
            return false;
        }
        if (phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please specify phone number");
            return false;
        }
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please specify address");
            return false;
        }
        return true;
    }
}