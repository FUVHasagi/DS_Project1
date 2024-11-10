package controller;

import model.Customer;
import model.ImportOrderLine;
import model.OrderLine;
import model.User;

public interface SelectionListener {
    public void onProductSelected(OrderLine OrderLine);
    public void onProductSelected(ImportOrderLine importOrderLine);
    public void onCustomerSelected(Customer customer);
    public void onUserSeletected(User user);
    public void onSaveSelected(String tableName);

}