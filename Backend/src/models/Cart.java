package models;

public class Cart {

    private int cart_id;
    private double total_price;
    private String product_names;
    private int user_id;


    Cart (int cart_id, double total_price, String product_names, int user_id) {
        this.cart_id = cart_id;
        this.total_price = total_price;
        this.product_names = product_names;
        this.user_id = user_id;
    }

    public int getCart_id() { return cart_id; }
    public double getTotal_price() { return total_price; }
    public String getProduct_names() { return product_names; }
    public int getUser_id() { return user_id; }

    public void setCart_id(int cart_id) { this.cart_id = cart_id; }
    public void setTotal_price(double total_price) { this.total_price = total_price; }
    public void setProduct_names(String product_names) { this.product_names = product_names; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Override
    public String toString() {
        return " { Id: " + cart_id + " | Total Price: " + total_price + " | Product Names: " + product_names + " | User Id: " + user_id + " } ";
    }
}
