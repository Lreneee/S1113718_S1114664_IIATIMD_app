package com.example.iiatimdapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Model.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ModelDAO modelDAO();

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
