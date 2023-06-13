package com.example.exe3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exe3.R;

public class MainActivity extends AppCompatActivity {
    Button loginButton;

    Button clickMe;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);

        loginButton = findViewById(R.id.loginButton);
        clickMe = findViewById(R.id.clickToRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateFields()){
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(intent);
                }
            }
        });
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);

                startActivity(intent);
            }
        });
    }
    private boolean validateFields(){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username is required");
            usernameEditText.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }
        return true;
    }
}
