package com.example.iiatimdapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.iiatimdapp.Room.Moestuin;
import com.example.iiatimdapp.Room.MoestuinMaten;
import com.example.iiatimdapp.Room.Token;
import com.example.iiatimdapp.Room.TokenDAO;
import com.example.iiatimdapp.Room.moestuinDAO;
import com.example.iiatimdapp.Room.moestuinMatenDAO;

@Database(entities = {Moestuin.class, MoestuinMaten.class, Token.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract moestuinDAO moestuinDAO();

    public abstract moestuinMatenDAO moestuinMatenDAO();

    public abstract TokenDAO tokenDAO();

    private static AppDatabase instance;

    static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = create(context);
        }
        return instance;
    }
    private static AppDatabase create(final Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "Moestuinen").fallbackToDestructiveMigration().build();
    }
}
