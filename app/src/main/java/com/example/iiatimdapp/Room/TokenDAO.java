package com.example.iiatimdapp.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TokenDAO {



    @Query("SELECT * FROM token LIMIT 1")
    LiveData<Token> getToken();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertToken(Token token);

    @Query("DELETE FROM token")
    void DeleteToken();

    @Update
    void UpdateToken(Token token);

}
