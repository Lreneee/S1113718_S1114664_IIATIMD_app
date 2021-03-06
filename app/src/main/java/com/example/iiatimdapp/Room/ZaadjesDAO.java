package com.example.iiatimdapp.Room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ZaadjesDAO {

    @Query("SELECT * FROM zaadjes")
    LiveData<List<Zaadjes>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertZaadjes(Zaadjes zaadjes);

    @Delete
    void DeleteZaadjes(Zaadjes zaadjes);
}
