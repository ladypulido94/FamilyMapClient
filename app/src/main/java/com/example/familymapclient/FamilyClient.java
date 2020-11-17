package com.example.familymapclient;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;

public class FamilyClient {

    private static final String HTTP = "http";
    private static final int TIME_OUT = 5000;
    private static final String LOGIN = "/user/login";
    private static final String REGISTER = "/user/register";

    private final String serverHost;
    private final int serverPort;

    public FamilyClient (String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LoginResult login(LoginRequest loginRequest)
    {
        URL loginURL;

        try {
            loginURL = new URL(FamilyClient.HTTP, serverHost, serverPort, LOGIN);
        }
        catch (MalformedURLException e) {
            Log.e("FamilyClient", "Incorrect login URL");
            return null;
        }

        Gson gson = new Gson();
        String stringRequest = gson.toJson(loginRequest);
        String stringResponse = postURL(loginURL, stringRequest);

        return gson.fromJson(stringResponse, LoginResult.class);
    }

    public RegisterResult register(RegisterRequest registerRequest) {

        URL registerURL;
        try {
            registerURL = new URL(FamilyClient.HTTP, serverHost, serverPort, REGISTER);
        }
        catch (MalformedURLException e) {
            Log.e("FamilyClient", "Incorrect register URL");
            return null;
        }

        Gson gson = new Gson();
        String stringRequest = gson.toJson(registerRequest);
        String stringResponse = postURL(registerURL, stringRequest);

        return gson.fromJson(stringResponse, RegisterResult.class);
    }

    private String postURL(URL url, String request) {
        try
        {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIME_OUT);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();

            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(request.getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readString(connection.getInputStream());
            } else {
                Log.e("FamilyClient", "HttpURLConnection response was not HTTP_OK");
                return readString(connection.getErrorStream());
            }
        }
        catch (IOException e) {
            System.out.println("Couldn't open url connection");
            e.printStackTrace();
        }
        return null;
    }

    private String readString(InputStream paramInputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream);

        char[] arrayOfChar = new char[1024];
        int i;

        while ((i = inputStreamReader.read(arrayOfChar)) > 0){
            stringBuilder.append(arrayOfChar, 0, i);
        }

        return stringBuilder.toString();
    }
}
