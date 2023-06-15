package com.example.exe3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exe3.R;
import com.example.exe3.Settings;
import com.example.exe3.infoToDB.LoginData;
import com.example.exe3.webService.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    ImageView settingsButton;
    UserApi userApi;
    Button clickMe;
    LoginData loginData;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        settingsButton = findViewById(R.id.settings);
        loginButton = findViewById(R.id.loginButton);
        clickMe = findViewById(R.id.clickToRegister);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateFields()){
                    userApi = new UserApi();
                    login(loginData);
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
    private void login(LoginData loginData){
        try {
            Call<String> call = userApi.login(loginData);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("token", response.body());
                        intent.putExtra("username",loginData.getUsername());
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("RegistrationError", e.getMessage(), e); // Log the error
        }
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
        loginData = new LoginData(username,password);
        return true;
    }
}
