package model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double ImportPrice;
    private double SellPrice;
    public Product(int id, String name, double importPrice, double sellPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.ImportPrice = importPrice;
        this.SellPrice = sellPrice;
    }

    public Product() {
        this.id = -1;
        this.name = null;
    }

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setImportPrice(double importPrice) {this.ImportPrice = importPrice;}
    public void setSellPrice(double SellPrice) {this.SellPrice = SellPrice;}

    public int getId() {return id;}
    public String getName() {return name;}
    public int getQuantity() {return quantity;}
    public double getImportPrice() {return ImportPrice;}
    public double getSellPrice() {return SellPrice;}

}