package models;

public class User {
    private int user_id;
    private String name;
    private String email;
    private String password;
    private String contact_details;

    User(int user_id, String name, String email, String password, String contact_details) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact_details = contact_details;
    }

    public int getUser_id() { return user_id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getContact_details() { return contact_details; }

    public void setUser_id(int user_id) { this.user_id = user_id; }
    public void setName(String name ) { this.name = name ; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String Password) { this.password = password; }
    public void setContact_details(String contact_details) { this.contact_details = contact_details; }

    @Override
    public String toString() {
        return " { Id: " + user_id + " | Name: " + name + " | Email: " + email + " | Password: " + password + " | Contact: " + contact_details + " } ";
    }
}
