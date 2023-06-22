package com.example.exe3.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Converters;

@Database(entities = {ContactForRoom.class, Chat.class},version = 2)
@TypeConverters(Converters.class)
public abstract class RoomAppDB extends RoomDatabase {
    public abstract ContactRoomDao contactRoomDao();
    public abstract ChatRoomDao chatRoomDao();
}
