package com.example.familymapclient;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import Request.LoginRequest;
import Result.LoginResult;

public class LoginFragment extends Fragment implements LoginTask.Listener {

    private EditText serverPort;
    private EditText serverHost;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioGroup genderRadioGroup;
    private Button signIn;
    private Button register;

    private editTextWatcher serverPortWatcher = new editTextWatcher();
    private editTextWatcher serverHostWatcher = new editTextWatcher();
    private editTextWatcher usernameWatcher = new editTextWatcher();
    private editTextWatcher passwordWatcher = new editTextWatcher();
    private editTextWatcher firstNameWatcher = new editTextWatcher();
    private editTextWatcher lastNameWatcher = new editTextWatcher();
    private editTextWatcher emailWatcher = new editTextWatcher();

    private String gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        serverHost = view.findViewById(R.id.server_host);
        serverHost.addTextChangedListener(serverHostWatcher);

        serverPort = view.findViewById(R.id.server_port);
        serverPort.addTextChangedListener(serverPortWatcher);

        username = view.findViewById(R.id.username);
        username.addTextChangedListener(usernameWatcher);

        password = view.findViewById(R.id.password);
        password.addTextChangedListener(passwordWatcher);

        firstName = view.findViewById(R.id.first_name);
        firstName.addTextChangedListener(firstNameWatcher);

        lastName = view.findViewById(R.id.last_name);
        lastName.addTextChangedListener(lastNameWatcher);

        email = view.findViewById(R.id.email);
        email.addTextChangedListener(emailWatcher);

        genderRadioGroup = view.findViewById(R.id.gender);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.female){
                    gender = "f";
                }
                if(checkedId == R.id.male) {
                    gender = "m";
                } else {
                    Log.e("LoginFragment", "There is another gender?");
                }
            }
        });

        signIn = view.findViewById(R.id.sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });

        register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

        enableButton();
        return view;

    }

    @Override
    public void onLoginComplete(LoginResult loginResult) {

    }

    private class editTextWatcher implements TextWatcher {

        private String pass = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pass = s.toString();
            enableButton();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        String getPass(){
            return pass;
        }
    }

    private void enableButton(){
        if(!serverHostWatcher.equals("") && !serverPortWatcher.equals("") &&
                !usernameWatcher.equals("") && !passwordWatcher.equals("")){
            signIn.setEnabled(true);
        } else {
            signIn.setEnabled(false);
        }

        if(!serverHostWatcher.equals("") && !serverPortWatcher.equals("") && !usernameWatcher.equals("") &&
                !passwordWatcher.equals("") && !firstName.equals("") && !lastNameWatcher.equals("") &&
                !emailWatcher.equals("")){
            register.setEnabled(true);
        } else {
            register.setEnabled(false);
        }
    }

    private void login(){
        try{
            LoginTask loginTask = new LoginTask(serverHostWatcher.getPass(), Integer.parseInt(serverPortWatcher.getPass()),this);
            LoginRequest loginRequest = new LoginRequest(usernameWatcher.getPass(), passwordWatcher.getPass());
            loginTask.execute(loginRequest);

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }


}
