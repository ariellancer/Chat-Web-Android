package com.example.exe3.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Query;
import java.util.List;
@Dao
public interface ContactRoomDao {
    @Query("Select * from ContactForRoom")
    List<ContactForRoom> getContactsList();
    @Query("Select * from ContactForRoom WHERE id= :id ")
    ContactForRoom getContact (int id);
    @Insert
    void insertContact (ContactForRoom contactForRoom);
    @Update
    void updateContact (ContactForRoom contactForRoom);
    @Delete
    void deleteContact (ContactForRoom contactForRoom);
}
