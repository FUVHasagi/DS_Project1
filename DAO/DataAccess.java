package DAO;

import java.sql.*;
import java.util.Optional;
import model.*;

import java.util.*;

public class DataAccess {
    private Connection connection;

    public DataAccess(Connection connection) {
        this.connection = connection;
    }

    // Authenticate a user
    public User authenticateUser(String username, String password) {
        String query = "SELECT Roles.Name FROM Users JOIN Roles ON Users.RoleID = Roles.ID WHERE Users.Username = ? AND Users.Password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String role = rs.getString("Name");
                return new User(username, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find product by ID
    public Product findProductById(int id) {
        String query = "SELECT * FROM Products WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getDouble("ImportPrice"),
                        rs.getDouble("SellPrice"),
                        rs.getInt("Quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update product stock
    public void updateProduct(Product product) {
        String query = "UPDATE Products SET Quantity = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, product.getQuantity());
            stmt.setInt(2, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save product with upsert functionality
    public boolean saveProduct(Product product) {
        String query = "INSERT INTO Products (ID, Name, SellPrice, ImportPrice, Quantity) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE Name = ?, SellPrice = ?, ImportPrice = ?, Quantity = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getSellPrice());
            stmt.setDouble(4, product.getImportPrice());
            stmt.setInt(5, product.getQuantity());

            stmt.setString(6, product.getName());
            stmt.setDouble(7, product.getSellPrice());
            stmt.setDouble(8, product.getImportPrice());
            stmt.setInt(9, product.getQuantity());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Create a new order
    public int createOrder(int customerId, double totalCost) {
        String query = "INSERT INTO Orders (CustomerID, OrderDate, TotalCost) VALUES (?, CURRENT_TIMESTAMP, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setDouble(2, totalCost);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // Return generated Order ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if order creation fails
    }

    // Create an order line
    public void createOrderLine(int orderId, int productId, int quantity, double discount, double price) {
        String query = "INSERT INTO OrderLines (OrderID, ProductID, Quantity, Discount, Price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, discount);
            stmt.setDouble(5, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new import order
    public int createImportOrder(double totalCost, User user) {
        String query = "INSERT INTO ImportOrders (ImportDate, TotalCost) VALUES (CURRENT_TIMESTAMP, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, totalCost);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1); // Return generated ImportOrder ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if import order creation fails
    }

    // Create an import order line
    public void createImportOrderLine(int importOrderId, int productId, int quantity, double cost) {
        String query = "INSERT INTO ImportOrderLines (ImportOrderID, ProductID, Quantity, Cost) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, importOrderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, cost);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderByID(int orderID) {
        String query = "SELECT Orders.ID, Customers.CustomerName, Orders.OrderDate, Orders.TotalCost, Users.DisplayName " +
                "FROM Orders " +
                "JOIN Customers ON Orders.CustomerID = Customers.CustomerID " +
                "JOIN Users ON Orders.User = Users.Username " +
                "WHERE Orders.ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("ID"));
                order.setCustomerName(rs.getString("CustomerName"));
                order.setOrderDate(rs.getString("OrderDate"));
                order.setTotalCost(rs.getFloat("TotalCost"));
                order.setUser(new User(rs.getString("DisplayName")));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderLine> getOrderLinesByOrderID(int orderID) {
        String query = "SELECT Products.Name AS ProductName, OrderLines.Quantity, OrderLines.Discount, OrderLines.Price " +
                "FROM OrderLines " +
                "JOIN Products ON OrderLines.ProductID = Products.ID " +
                "WHERE OrderLines.OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();
            List<OrderLine> lines = new ArrayList<>();
            while (rs.next()) {
                OrderLine line = new OrderLine();
                line.setProductName(rs.getString("ProductName"));
                line.setQuantity(rs.getInt("Quantity"));
                line.setDiscount(rs.getDouble("Discount"));
                line.setPrice(rs.getDouble("Price"));
                lines.add(line);
            }
            return lines;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
