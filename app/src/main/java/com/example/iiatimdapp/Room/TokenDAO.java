package com.example.iiatimdapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TokenDAO {



    @Query("SELECT * FROM token LIMIT 1")
    Token getToken();

    @Insert
    void InsertToken(Token token);

    @Delete
    void DeleteToken(Token token);

    @Update
    void UpdateToken(Token token);

}
