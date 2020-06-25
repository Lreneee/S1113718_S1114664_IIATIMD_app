package com.example.iiatimdapp;

public class getMoestuinTask implements Runnable {

        AppDatabase db;

        public getMoestuinTask(AppDatabase db){
            this.db = db;
        }

    @Override
    public void run() {
        db.modelDAO().getAll();
    }
}
