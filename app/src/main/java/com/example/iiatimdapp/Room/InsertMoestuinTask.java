package com.example.iiatimdapp.Room;

import com.example.iiatimdapp.AppDatabase;

public class InsertMoestuinTask implements Runnable{
    private AppDatabase db;
    private Moestuin moestuin;

    public InsertMoestuinTask(AppDatabase db, Moestuin moestuin) {
        this.db = db;
        this.moestuin = moestuin;
    }

    @Override
    public void run() {
        db.moestuinDAO().InsertMoestuin(moestuin);
    }
}
