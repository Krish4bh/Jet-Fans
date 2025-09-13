package com.jetfans.models;

public class Order {

    private int order_id;
    private String order_date;
    private double order_price;
    private int quantity;
    private String shipping_address;
    private int user_id;
    private int product_id;

    Order (int order_id, String order_date, double order_price, int quantity, String shipping_address, int user_id, int product_id) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_price = order_price;
        this.quantity = quantity;
        this.shipping_address = shipping_address;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public int getOrder_id() { return order_id; }
    public String getOrder_date() { return order_date; }
    public double getOrder_price() { return order_price; }
    public int getQuantity() { return quantity; }
    public String getShipping_address() { return shipping_address; }
    public int getUser_id() { return user_id; }
    public int getProduct_id() { return product_id; }

    public void setOrder_id(int order_id) { this.order_id = order_id; }
    public void setOrder_date(String order_date) { this.order_date = order_date; }
    public void setOrder_price(double order_price) { this.order_price = order_price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setShipping_address(String shipping_address) { this.shipping_address = shipping_address; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }

    @Override
    public String toString() {
        return " { Id:" + order_id + " | Order Date: " + order_date + " | Order Total: " + order_price + " | quantity: " + quantity + " | Shipping Address: " + shipping_address + " | User Id: " + user_id + " | Product Id: " + product_id + " } ";
    }
}
