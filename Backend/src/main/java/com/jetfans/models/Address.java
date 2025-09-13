package com.jetfans.models;

public class Address {

    private int address_id;
    private String permanent_address;
    private int pincode;
    private String state;
    private String city;
    private int landline;
    private int user_id;

    Address (int address_id, String permanent_address, int pincode, String state, String city, int landline, int user_id) {
        this.address_id = address_id;
        this.permanent_address = permanent_address;
        this.pincode = pincode;
        this.state = state;
        this.city = city;
        this.landline = landline;
        this.user_id = user_id;
    }

    public int getAddress_id() { return address_id; }
    public String getPermanent_address() { return permanent_address; }
    public int getPincode() { return pincode; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public int getLandline() { return landline; }
    public int getUser_id() { return user_id; }

    public void setAddress_id(int address_id) { this.address_id = address_id; }
    public void setPermanent_address(String permanentAddress) { this.permanent_address = permanent_address; }
    public void setPincode(int pincode) { this.pincode = pincode; }
    public void setState(String state) { this.state = state; }
    public void setCity(String city) { this.city = city; }
    public void setLandline(int landline) { this.landline = landline; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Override
    public String toString() {
        return " { Id: " + address_id + " | Permanent Address: " + permanent_address + " | Pincode: " + pincode + " | State: " + state + " | City: " + city + " | Landline: " + landline + " | User Id: " + user_id + " } ";
    }
}
