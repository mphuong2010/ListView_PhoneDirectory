package com.example.listview;

import android.net.Uri;

public class contact {
    private String name, phoneNumber;
    private Uri avatarUri;
    private boolean isSelected; // New boolean field for selection

    // Constructor
    public contact(Uri avatarUri, String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatarUri = avatarUri;
        this.isSelected = false; // Default to false
    }

    // Getter and Setter for avatarUri
    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for isSelected
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
