package com.example.iiatimdapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ModelDAO {

    @Query("SELECT * FROM model")
    List<Model> getAll();

    @Insert
    void InsertMoestuin(Model model);

    @Delete
    void deleteMoestuin(Model model);
}
