package models;

public class Payments {

    private int payment_id;
    private String transaction_id;
    private double amount;
    private String payment_date;
    private String payment_method;
    private int user_id;

    Payments( int payment_id, String transaction_id, double amount, String payment_date, String payment_method, int user_id) {
        this.payment_id = payment_id;
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.payment_date = payment_date;
        this.payment_method = payment_method;
        this.user_id = user_id;
    }

    public int getPayment_id() { return payment_id; }
    public String getTransaction_id() { return transaction_id; }
    public double getAmount() { return amount; }
    public String getPayment_date() { return payment_date; }
    public String getPayment_method() { return payment_method; }
    public int getUser_id() { return user_id; }

    public void setPayment_id(int payment_id) { this.payment_id = payment_id; }
    public void setTransaction_id(String transaction_id) { this.transaction_id = transaction_id; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPayment_date(String payment_date) { this.payment_date = payment_date; }
    public void setPayment_method(String payment_method) { this.payment_method = payment_method; }
    public void setUser_id(int user_id) { this.user_id = user_id; }


    @Override
    public String toString() {
        return " { Id: " + payment_id + " | Transaction Id: " + transaction_id + " | Amount: " + amount + " | Payment Date: " + payment_date + " | Payment method: " + payment_method + " | User Id: " + user_id + " } ";
    }
}
