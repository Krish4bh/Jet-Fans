package models;

public class Feedback {

    private int feedback_id;
    private int rating;
    private String comment;
    private int user_id;
    private int product_id;

    Feedback(int feedback_id, int rating, String comment, int user_id, int product_id) {
        this.feedback_id = feedback_id;
        this.rating = rating;
        this.comment = comment;
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public int getFeedback_id() { return feedback_id; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public int getUser_id() { return user_id; }
    public int getProduct_id() { return product_id; }

    public void setFeedback_id(int feedback_id) { this.feedback_id = feedback_id; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id; }

    @Override
    public String toString() {
        return " { Id: " + feedback_id + " | Rating: " + rating + " | Comment: " + comment + " | User Id: " + user_id + " | Product Id: " + product_id + " } ";
    }
}
