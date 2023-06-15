package com.example.exe3.infoToDB;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static ContactInfo fromStringToContactInfo(String value) {
        String[] parts = value.split("\\$"); // Split the string using the delimiter "$"

        // Extract the individual values from the parts array
        String username = parts[0];
        String displayName = parts[1];
        String profilePic = parts[2];

        // Create and return the ContactInfo object
        return new ContactInfo(username, displayName, profilePic);
    }

    @TypeConverter
    public static String toString(ContactInfo contactInfo) {
        return contactInfo.getUsername()+"$"+contactInfo.getDisplayName()+"$"+contactInfo.getProfilePic();
    }
    @TypeConverter
    public static LastMessage fromStringToLastMessage(String value) {
        String[] parts = value.split("\\$"); // Split the string using the delimiter "$"

        // Extract the individual values from the parts array
        String id = parts[0];
        String created = parts[1];
        String content = parts[2];

        // Create and return the LastMessage object
        return new LastMessage(Integer.parseInt(id), created, content);
    }
    @TypeConverter
    public static String toString(LastMessage lastMessage) {
        return String.valueOf(lastMessage.getId())+"$"+lastMessage.getCreated()+"$"+lastMessage.getContent();
    }
}