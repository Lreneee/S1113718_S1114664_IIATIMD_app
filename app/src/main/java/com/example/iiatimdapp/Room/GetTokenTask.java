package com.example.iiatimdapp.Room;

import android.util.Log;

import com.example.iiatimdapp.AppDatabase;

public class GetTokenTask implements Runnable {
    private AppDatabase db;

    public GetTokenTask(AppDatabase db) {
        this.db = db;
    }

    @Override
    public void run() {
        Token token = this.db.tokenDAO().getToken();

        Log.d("TokenTest", token.getAccessToken());
    }
}
