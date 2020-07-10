package com.example.iiatimdapp.Room;

import com.example.iiatimdapp.AppDatabase;

public class InsertZaadjesTask implements Runnable {
    private AppDatabase db;
    private Zaadjes zaadjes;

    public InsertZaadjesTask(AppDatabase db, Zaadjes zaadjes) {
        this.db = db;
        this.zaadjes = zaadjes;
    }

    @Override
    public void run() {
        db.zaadjesDAO().InsertZaadjes(zaadjes);
    }
}
