package com.example.iiatimdapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface moestuinMatenDAO {
    @Query("SELECT * FROM MoestuinMaten")
    List<MoestuinMaten> getAll();

    @Insert
    void InsertMoestuin(MoestuinMaten moestuinMaten);

    @Delete
    void deleteMoestuin(MoestuinMaten moestuinMaten);
}
