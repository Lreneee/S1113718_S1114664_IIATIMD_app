package com.example.iiatimdapp.Room;

import com.example.iiatimdapp.AppDatabase;

public class InsertTipTask implements Runnable {
    private AppDatabase db;
    private Tips tips;

    public InsertTipTask(AppDatabase db, Tips tips) {
        this.db = db;
        this.tips = tips;
    }

    @Override
    public void run() {
        db.tipsDAO().InsertTip(tips);
    }
}
