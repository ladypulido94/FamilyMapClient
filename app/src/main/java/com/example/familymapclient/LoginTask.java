package com.example.familymapclient;

import android.os.AsyncTask;
import android.util.Log;

import Request.LoginRequest;
import Result.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResult> {

    private String serverHost;
    private int serverPort;
    private Listener listener;

    public interface Listener {

        void onLoginComplete(LoginResult loginResult);
    }

    public LoginTask(String serverHost, int serverPort, Listener listener){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.listener = listener;
    }

    @Override
    protected LoginResult doInBackground(LoginRequest... loginRequests) {
        int numRequests = 1;

        if(loginRequests.length != numRequests){
            Log.e("doInBackground", "Too many requests");
        }

        LoginRequest loginRequest = loginRequests[0];
        FamilyClient familyClient = new FamilyClient(serverHost, serverPort);

        return familyClient.login(loginRequest);
    }

    @Override
    protected void onPostExecute(LoginResult loginResult) {
        super.onPostExecute(loginResult);
        listener.onLoginComplete(loginResult);
    }
}
