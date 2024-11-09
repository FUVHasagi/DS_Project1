package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import view.*;
import controller.*;
import model.*;


public class MainScreen extends JFrame {
    private JButton btnSell = new JButton("Sell Product");
    private JButton btnImport = new JButton("Import Product");
    private JButton btnQuery = new JButton("Query");
    private User user;



    public MainScreen(User user) {
        this.user = user;

        setTitle("Store Management System");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        initializeComponents();
        setupRoleBasedAccess();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Store Management System");
        title.setFont(new Font("Sans Serif", Font.BOLD, 24));
        JPanel panelTitle = new JPanel();
        panelTitle.add(title);
        this.getContentPane().add(panelTitle);

        JPanel panelButton = new JPanel();
        panelButton.add(btnSell);
        panelButton.add(btnImport);
        panelButton.add(btnQuery);


        if (!Objects.equals(this.user.getRole(), "Manager") &&
                !Objects.equals(this.user.getRole(), "Cashier")){
            btnSell.setEnabled(false);
        }

        else if (!Objects.equals(this.user.getRole(), "Importer") &&
                !Objects.equals(this.user.getRole(), "Manager")){
            btnImport.setEnabled(false);
        }

        this.getContentPane().add(panelButton);
    }

    private void setupRoleBasedAccess() {
        // Restrict access to buttons based on user role
        switch (this.user.getRole()) {
            case "Cashier":
                btnImport.setEnabled(false); // Cashiers cannot import products
                break;

            case "Importer":
                btnSell.setEnabled(false); // Importers cannot sell products
                break;

            case "Manager":
                // Managers have access to all buttons, so no restrictions needed
                break;

            default:
                JOptionPane.showMessageDialog(this, "Invalid role specified", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
        }
    }

    public JButton getBtnSell() {
        return btnSell;
    }

    public JButton getBtnImport() {
        return btnImport;
    }

    public JButton getBtnQuery() {
        return btnQuery;
    }

    public void getActionListener(ActionListener actionListener) {
        btnSell.addActionListener(actionListener);
        btnImport.addActionListener(actionListener);
        btnQuery.addActionListener(actionListener);
    }
}