package com.example.exe3.infoToDB;

public class Picture {
    private static Picture instance;
    private String picture;

    private Picture() {
        // Private constructor to prevent instantiation from outside
    }

    public static Picture getInstance() {
        if (instance == null) {
            instance = new Picture();
        }
        return instance;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
