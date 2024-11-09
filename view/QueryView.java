package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QueryView extends JFrame {
    private JComboBox<String> queryTypeComboBox;
    private JButton queryButton;
    private String[] queryOptions = {"Product", "User", "Customer", "Order"};

    public QueryView() {
        setTitle("Query Options");
        setSize(300, 150);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        queryTypeComboBox = new JComboBox<>(queryOptions);
        queryButton = new JButton("Query");

        panel.add(queryTypeComboBox);
        panel.add(queryButton);

        add(panel);
    }

    public void setActionListener(ActionListener listener) {
        queryButton.addActionListener(listener);
    }

    public String getSelectedQueryType() {
        return (String) queryTypeComboBox.getSelectedItem();
    }
}