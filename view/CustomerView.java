package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JFrame {
    private JTextField customerIdField, customerNameField, phoneNumberField, addressField;
    private JButton queryButton, actionButton;
    private String mode;

    public CustomerView(String mode) {
        this.mode = mode;
        setTitle("Customer View");
        setSize(300, 200);
        setLocationRelativeTo(null);

        initUI();
        setMode(this.mode);
    }

    private void initUI() {
        setLayout(new BorderLayout());
        actionButton = new JButton();
        queryButton = new JButton();

        setMode(mode);
    }

    public void setMode(String mode) {
        this.mode = mode;
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField();
        panel.add(customerIdLabel);
        panel.add(customerIdField);

        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField();
        panel.add(customerNameLabel);
        panel.add(customerNameField);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        panel.add(addressLabel);
        panel.add(addressField);

        switch (this.mode) {
            case "SELLING":
                actionButton.setText("Add Customer");
                queryButton.setText("Get Customer");
                customerNameField.setEditable(false);
                phoneNumberField.setEditable(false);
                addressField.setEditable(false);
                break;

            case "QUERY":
                queryButton.setText("Get Customer");
                actionButton.setText("Save Customer");
                customerIdField.setEditable(true);
                customerNameField.setEditable(true);
                phoneNumberField.setEditable(true);
                addressField.setEditable(true);
                break;
        }

        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(queryButton);
        buttonPanel.add(actionButton);
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    public void addActionButtonListener(ActionListener listener) {
        actionButton.addActionListener(listener);
    }

    public void addQueryButtonListener(ActionListener listener) {
        queryButton.addActionListener(listener);
    }

    public String getCustomerId() {
        return customerIdField.getText();
    }

    public String getCustomerName() {
        return customerNameField.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public void setCustomerName(String name) {
        customerNameField.setText(name);
    }

    public void setPhoneNumber(String phone) {
        phoneNumberField.setText(phone);
    }

    public void setAddress(String address) {
        addressField.setText(address);
    }

    public JButton getActionButton() {
        return this.actionButton;
    }

    public JButton getQueryButton() {
        return this.queryButton;
    }
}
