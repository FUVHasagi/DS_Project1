package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderView extends JFrame {
    private JTextField orderIDField;
    private JTextField customerNameField;
    private JTextField orderDateField;
    private JTextField userField;
    private JTextField totalCostField;
    private JButton queryButton;

    public OrderView() {
        setTitle("Order Query");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel orderIDLabel = new JLabel("Order ID:");
        orderIDField = new JTextField();
        JLabel customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField();
        JLabel orderDateLabel = new JLabel("Order Date:");
        orderDateField = new JTextField();
        JLabel userLabel = new JLabel("User in Charge:");
        userField = new JTextField();
        JLabel totalCostLabel = new JLabel("Total Cost:");
        totalCostField = new JTextField();

        queryButton = new JButton("Query");

        panel.add(orderIDLabel);
        panel.add(orderIDField);
        panel.add(customerNameLabel);
        panel.add(customerNameField);
        panel.add(orderDateLabel);
        panel.add(orderDateField);
        panel.add(userLabel);
        panel.add(userField);
        panel.add(totalCostLabel);
        panel.add(totalCostField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(queryButton);

        add(panel);
    }

    public void setQueryButtonListener(ActionListener listener) {
        queryButton.addActionListener(listener);
    }

    public String getOrderID() {
        return orderIDField.getText();
    }

    public void setOrderDetails(String customerName, String orderDate, String user, float totalCost) {
        customerNameField.setText(customerName);
        orderDateField.setText(orderDate);
        userField.setText(user);
        totalCostField.setText(String.valueOf(totalCost));
    }

    public void clearFields() {
        customerNameField.setText("");
        orderDateField.setText("");
        userField.setText("");
        totalCostField.setText("");
    }
}
