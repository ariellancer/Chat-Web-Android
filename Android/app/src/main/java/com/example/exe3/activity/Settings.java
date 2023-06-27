package com.example.exe3.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.exe3.R;
import com.example.exe3.webService.ChatApi;
import com.example.exe3.webService.UserApi;

public class Settings extends AppCompatActivity {
    ImageView ret;
    final String regex = "https?://([0-9]{1,3}\\.){3}[0-9]{1,3}:[0-9]{1,5}(?:/.*)?";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button button_light = findViewById(R.id.button_light_mode);
        Button button_dark = findViewById(R.id.button_dark_mode);
        Button saveUrl= findViewById(R.id.save_new_url);
        EditText inputUrl = findViewById(R.id.editTextTextEmailAddress);
        ret = findViewById(R.id.returnToBase);
        ret.setOnClickListener(fun -> finish());
        inputUrl.setText(ChatApi.getInstance().getUrl());
        saveUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = inputUrl.getText().toString();
                if (url.matches(regex)){
                    ChatApi.getInstance().setRetrofit(url,getApplicationContext());
                    UserApi.getInstance().setRetrofit(url,getApplicationContext());
                }else{
                    Toast.makeText(getApplicationContext(), "URL not valid" , Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        button_dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }
}
