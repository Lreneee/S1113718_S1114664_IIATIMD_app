package com.example.iiatimdapp.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ZaadjesDAO {

    @Query("SELECT * FROM zaadjes")
    List<Zaadjes> getAll();

    @Insert
    void InsertMoestuin(Zaadjes zaadjes);

    @Delete
    void deleteMoestuin(Zaadjes zaadjes);
}
