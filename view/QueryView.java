package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QueryView extends JFrame {
    private JComboBox<String> queryTypeComboBox;
    private JButton queryButton;
    private JButton getAllButton;
    private String[] queryOptions = {"Products", "Users", "Customers", "Orders", "ImportOrders"};

    public QueryView() {
        setTitle("Query Options");
        setSize(300, 100);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        queryTypeComboBox = new JComboBox<>(queryOptions);
        queryButton = new JButton("Query");
        getAllButton = new JButton("Get All");

        panel.add(queryTypeComboBox);
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout(1, 2));
        panelBtn.add(queryButton);
        panelBtn.add(getAllButton, BorderLayout.SOUTH);

        panel.add(panelBtn);

        add(panel);
    }

    public void setActionListener(ActionListener listener) {
        queryButton.addActionListener(listener);
        getAllButton.addActionListener(listener);
    }

    public JButton getQueryButton(){
        return queryButton;
    }

    public JButton getGetAllButton(){
        return getAllButton;
    }


    public String getSelectedQueryType() {
        return (String) queryTypeComboBox.getSelectedItem();
    }

    public void disposeView() {
        this.setVisible(false);
        // Remove all action listeners from buttons
        for (ActionListener listener : queryButton.getActionListeners()) {
            queryButton.removeActionListener(listener);
        }
        for (ActionListener listener : getAllButton.getActionListeners()) {
            getAllButton.removeActionListener(listener);
        }

        // Clear references to components
        queryTypeComboBox.removeAllItems();
        queryTypeComboBox = null;
        queryButton = null;
        getAllButton = null;
        queryOptions = null;

        // Dispose of JFrame resources
        dispose();
        System.out.println("QueryView resources cleaned up and disposed.");
    }
}