package com.example.exe3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button button_light = findViewById(R.id.button_light_mode);
        Button button_dark = findViewById(R.id.button_dark_mode);
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
