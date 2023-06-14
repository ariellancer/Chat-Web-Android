package com.example.exe3.infoToDB;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ChatDao {

    @Query("Select * FROM chat")
    List<Chat> index();

    @Query("Select * FROM chat WHERE id = :id")
    Chat get(int id);

    @Insert
    void insert(Chat... chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);
}