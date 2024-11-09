package view;

// LoginView.java
import javax.swing.*;
import java.awt.*;
import model.User;

public class LoginView extends JFrame {
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JButton loginButton = new JButton("Login");

    public LoginView() {
        setTitle("Login");
        setSize(300, 150);
        this.setLayout(new BoxLayout(this.getContentPane(), 1));
        this.getContentPane().add(new JLabel("Store Management System"));
        JPanel main = new JPanel(new SpringLayout());
        main.add(new JLabel("Username:"));
        main.add(this.usernameField);
        main.add(new JLabel("Password:"));
        main.add(this.passwordField);
        SpringUtilities.makeCompactGrid(main, 2, 2, 6, 6, 6, 6);
        this.getContentPane().add(main);
        this.getContentPane().add(this.loginButton);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
