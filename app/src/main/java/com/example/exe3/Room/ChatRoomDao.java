package com.example.exe3.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.exe3.infoToDB.Chat;

import java.util.List;

@Dao
public interface ChatRoomDao {
    @Query("Select * from chat")
    List<Chat> getChatList();
    @Query("Select * from chat where id=:id")
    Chat get(int id);
    @Insert
    void insert(Chat...chats);
    @Update
    void update(Chat ... chats);
    @Delete
    void delete(Chat... chats);
}