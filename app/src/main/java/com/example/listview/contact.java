package com.example.listview;
public class contact {
    private String name, phoneNumber;
    private int avatar;

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //Tạo constructor
    public contact(int avatar, String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }
}

