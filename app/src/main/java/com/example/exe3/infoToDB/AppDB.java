package com.example.exe3.infoToDB;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Contact.class,Chat.class},version = 2)
@TypeConverters(Converters.class)

public abstract class AppDB extends RoomDatabase{
    public abstract ContactDao contactDao();
    public abstract ChatDao chatDao();
}
