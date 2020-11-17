package com.example.familymapclient;

import android.os.AsyncTask;

import Request.RegisterRequest;
import Result.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResult> {

    public final int serverPort;
    public final String serverHost;
    public final Listener listener;

    public interface Listener {
        void onRegisterComplete(RegisterResult registerResult);
    }

    public RegisterTask(String serverHost, int serverPort, Listener listener ){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.listener = listener;
    }

    @Override
    protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
        return null;
    }
}
