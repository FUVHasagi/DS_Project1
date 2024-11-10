package controller;

import DAO.DataAccess;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import model.*;
import javax.swing.*;

public class MainController implements ActionListener {
    private MainScreen mainScreen;
    private DataAccess dataAccess;
    private User user;

    private ImportController importController;
    private QueryController queryController;
    private CheckoutController checkoutController;

    public MainController(User user, DataAccess dataAccess) {
        this.user = user;
        this.mainScreen = new MainScreen(user);
        this.mainScreen.getActionListener(this);
        this.dataAccess = dataAccess;
        this.mainScreen.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainScreen.getBtnSell()) {
            if (Objects.equals(user.getRole(), "Manager") || Objects.equals(user.getRole(), "Cashier")) {
                this.checkoutController = new CheckoutController(dataAccess, this.user);
                checkoutController.showWindow();
            }
            else {
                JOptionPane.showMessageDialog(mainScreen, "Only Manager and Cashier can access this function");
            }

        }

        if (e.getSource() == mainScreen.getBtnImport()) {
            if (Objects.equals(user.getRole(), "Manager") || Objects.equals(user.getRole(), "Importer")) {
                this.importController = new ImportController(dataAccess, user);
                importController.showWindow();
            }
            else {
                JOptionPane.showMessageDialog(mainScreen, "Only Manager and Importer can access this function");
            }
        }
        if (e.getSource() == mainScreen.getBtnQuery()) {
            this.queryController = new QueryController(user, dataAccess);
            this.queryController.showWindow();
        }

    }

}

