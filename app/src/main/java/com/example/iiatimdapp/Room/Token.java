package com.example.iiatimdapp.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity
public class Token {

    @PrimaryKey
    private int id;

    @ColumnInfo
    private String accessToken;
    @ColumnInfo
    private String refreshToken;

    public Token(String accessToken, String refreshToken) {
        this.id = 1;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
