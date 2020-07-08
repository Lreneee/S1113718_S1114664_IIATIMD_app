package com.example.iiatimdapp.Room;

import android.util.Log;

import com.example.iiatimdapp.APIManager;
import com.example.iiatimdapp.AppDatabase;

public class DeleteTokenTask implements Runnable{
    private AppDatabase db;

    public DeleteTokenTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void run() {
        db.tokenDAO().DeleteToken();
    }
}
