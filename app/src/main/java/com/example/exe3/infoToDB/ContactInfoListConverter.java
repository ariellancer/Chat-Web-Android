package com.example.exe3.infoToDB;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ContactInfoListConverter {

    @TypeConverter
    public static List<ContactInfo> fromString(String value) {
        Type listType = new TypeToken<List<ContactInfo>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toString(List<ContactInfo> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
