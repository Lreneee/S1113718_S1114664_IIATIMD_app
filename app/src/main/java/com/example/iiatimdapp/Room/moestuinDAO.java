package com.example.iiatimdapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface moestuinDAO {

    @Query("SELECT * FROM Moestuin")
    List<Moestuin> getAll();

    @Insert
    void InsertMoestuin(Moestuin moestuin);

    @Delete
    void deleteMoestuin(Moestuin moestuin);
}
