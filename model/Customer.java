package model;

public class Customer {
    private int CustomerID;
    private String Name;
    private String Phone;
    private String Address;
    // private int Score;
    // private String Rank;


    public void setCustomerID(int CustomerID) {this.CustomerID = CustomerID;}
    public int getCustomerID() {
        return CustomerID;
    }

    public void setName(String Name) {this.Name = Name;}
    public String getName() {return Name;}


    public void setPhone(String Phone) {this.Phone = Phone;}
    public String getPhone() {return Phone;}

    public void setAddress(String Address) {this.Address = Address;}
    public String getAddress() {return Address;}
}