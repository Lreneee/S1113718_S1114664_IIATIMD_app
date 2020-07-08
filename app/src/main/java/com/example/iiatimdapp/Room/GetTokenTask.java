package com.example.iiatimdapp.Room;

import android.util.Log;

import com.example.iiatimdapp.APIManager;
import com.example.iiatimdapp.AppDatabase;

import java.util.concurrent.Callable;

public class GetTokenTask implements Runnable {
    private AppDatabase db;
    private APIManager api;

    public GetTokenTask(AppDatabase db, APIManager api) {
        this.db = db;
        this.api = api;
    }

    @Override
    public void run() {
        Token token = this.db.tokenDAO().getToken();

        api.setAccessToken(token.getAccessToken());
    }
}
