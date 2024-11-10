import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import DAO.DataAccess;
import controller.CheckoutController;
import controller.LoginController;
import controller.MainController;
import model.User;
import view.*;

public class Application {

    private static Application instance;

    public static Application getInstance() {
        if (instance == null) {
            try {
                instance = new Application();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return instance;
    }

    private Connection connection;
    private DataAccess dataAccess;
    private User currentUser = null;
    private LoginView loginView = new LoginView();
    private LoginController loginController;
    private MainController mainScreen;

    private Application() throws SQLException {
        try {            // Establish connection with MySQL database
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project1", "myuser", "mypassword"
            );
            this.dataAccess = new DataAccess(connection);

            // Initialize controllers
            this.loginController = new LoginController(loginView, dataAccess);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to connect to the database");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public static void main(String[] args) {
        Application.getInstance().getLoginScreen().setVisible(true);
    }

    public LoginView getLoginScreen() {
        return loginView;
    }
}