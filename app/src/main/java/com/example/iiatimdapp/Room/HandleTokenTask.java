package com.example.iiatimdapp.Room;

import com.example.iiatimdapp.AppDatabase;

public class HandleTokenTask implements Runnable {

    private AppDatabase db;
    private Token token;

    public HandleTokenTask(AppDatabase db, Token token) {
        this.db = db;
        this.token = token;
    }

    @Override
    public void run() {
        Token token = db.tokenDAO().getToken();

        if (token == null) {
            db.tokenDAO().InsertToken(this.token);
        } else {
            db.tokenDAO().UpdateToken(this.token);
        }
    }
}
