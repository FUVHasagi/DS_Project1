package controller;

import DAO.DataAccess;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import view.LoginView;
import view.*;

public class LoginController {
    private LoginView loginView;
    private DataAccess dataAccess;


    public LoginController(LoginView loginView, DataAccess dataAccess) {
        this.loginView = loginView;
        this.dataAccess = dataAccess;

        // Show the login view
        this.loginView.setVisible(true);

        // Add action listener to the login button
        loginView.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }

    private void authenticateUser() {
        String username = loginView.getUsernameField().getText();
        String password = String.valueOf(loginView.getPasswordField().getPassword());

        User user = dataAccess.authenticateUser(username, password);

        if (user != null) {
            loginView.dispose(); // Close the login screen
            openMainScreen(user); // Open main screen with user's role
        } else {
            loginView.displayErrorMessage("Invalid username or password");
        }
    }

    private void openMainScreen(User user) {
        SwingUtilities.invokeLater(() -> {
            // Open the main screen based on user's role
            MainController mainController = new MainController(user, this.dataAccess);
        });
    }
}