package com.jetfans.models;

public class Product {
    private int product_id;
    private String product_name;
    private double price;
    private String description;
    private int user_id;


    Product (int product_id, String product_name, double price, String description, int user_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.user_id = user_id;
    }

    public int getProduct_id() { return product_id; }
    public String getProduct_name() { return product_name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getUser_id() { return user_id; }

    public void setProduct_id(int product_id) { this.product_id = product_id; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description ) { this.description = description; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Override
    public String toString() {
        return " { ID: " + product_id + " | Product Name: " + product_name + " | Price: " + price + " | Description: " + description + " | User Id: " + user_id + " } ";
    }
}
