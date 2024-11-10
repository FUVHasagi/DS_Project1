package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class QueryTableView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private String tableName;
    private JLabel labTotal = new JLabel("Total: ");
    public QueryTableView() {
        setTitle("Order Table View");
        setSize(800, 600);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        // Set up the JTable model
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }
    private void initTotal(){
        labTotal.setText("Total: " + tableModel.getRowCount());
        add(labTotal, BorderLayout.SOUTH);
    }

    public JLabel getLabTotal(){
        return labTotal;
    }
    // Method to set columns dynamically based on table name
    public void setTableColumns(String tableName) {
        String[] columns = getColumnsForTable(tableName);
        if (columns != null) {
            tableModel.setColumnIdentifiers(columns);
        }
    }


    // Method to populate the JTable with data from the database
    public void setTableData(List<Object[]> data) {
        // Clear previous data
        tableModel.setRowCount(0);

        // Add new data to the table
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
        this.setVisible(true);
    }

    // Helper method to return the column names for each table
    private String[] getColumnsForTable(String tableName) {
        switch (tableName) {
            case "Roles":
                return new String[]{"ID", "Name"};
            case "Users":
                return new String[]{"ID", "Username", "DisplayName", "Role"};
            case "Products":
                return new String[]{"ID", "Name", "Quantity", "SellPrice", "ImportPrice"};
            case "Customers":
                return new String[]{"CustomerID", "CustomerName", "PhoneNumber", "Address"};
            case "Orders":
                return new String[]{"ID", "CustomerID", "CustomerName","OrderDate", "TotalCost"};
            case "ImportOrders":
                return new String[]{"ImportOrderID","ImportDate", "TotalPrice"};
            case "OrderLines":
                return new String[]{"Product", "Quantity", "Discount", "TotalCost"};
            case "ImportOrderLines":
                return new String[]{"Product", "Quantity", "TotalCost"};
            default:
                return null; // Return null for invalid table name
        }
    }
}
