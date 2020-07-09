package com.example.iiatimdapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface moestuinDAO {

    @Query("SELECT * FROM moestuin")
    LiveData<List<Moestuin>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMoestuin(Moestuin moestuin);

    @Delete
    void deleteMoestuin(Moestuin moestuin);
}
