package com.example.exe3.infoToDB;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ContactDao {

    @Query("Select * FROM contact")
    List<Contact> index();

    @Query("Select * FROM contact WHERE id = :id")
    Contact get(int id);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}
