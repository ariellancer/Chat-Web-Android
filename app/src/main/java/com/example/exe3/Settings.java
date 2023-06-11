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
        Button button_red = findViewById(R.id.button_red);
        Button button_blue = findViewById(R.id.button_blue);
        Button button_green = findViewById(R.id.button_green);
        Button button_orange = findViewById(R.id.button_orange);
        Button button_purple = findViewById(R.id.button_purple);
        Button button_def = findViewById(R.id.button_default);
        button_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        button_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
        button_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                changeXmlBlue();
            }
        });
        button_purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                changeXmlPurple();
            }
        });
        button_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                changeXmlOrange();
            }
        });
        button_def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action to change XML file "A" here
                changeXmlDefault();
            }
        });
    }

    private void changeXmlRed() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);
//
//        // Find the view whose background you want to modify
//        ConstraintLayout constraintLayout = findViewById(R.id.constraint);
//
//        // Set a new background drawable
//        constraintLayout.setBackgroundResource(R.drawable.semi_red);
//
//        // Set the modified viewA as the content view of the activity
//        setContentView(viewA);
        // Find the view whose background you want to modify
        ConstraintLayout constraintLayout =viewA.findViewById(R.id.constraint);

        // Set a new background drawable
        constraintLayout.setBackgroundResource(R.drawable.semi_red);
       // setContentView(viewA);
       // String modifiedXml = Utils.viewToXml(viewA);
    }
    private void changeXmlGreen() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);

        // Find the view whose background you want to modify
//        ConstraintLayout constraintLayout = findViewById(R.id.constraint);
//
//        // Set a new background drawable
//        constraintLayout.setBackgroundResource(R.drawable.semi_red);

        // Set the modified viewA as the content view of the activity
        setContentView(viewA);
    }
    private void changeXmlBlue() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);

        // Find the view whose background you want to modify
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);

        // Set a new background drawable
        constraintLayout.setBackgroundResource(R.drawable.semi_red);

        // Set the modified viewA as the content view of the activity
        setContentView(viewA);
    }
    private void changeXmlPurple() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);

        // Find the view whose background you want to modify
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);

        // Set a new background drawable
        constraintLayout.setBackgroundResource(R.drawable.semi_red);

        // Set the modified viewA as the content view of the activity
        setContentView(viewA);
    }
    private void changeXmlDefault() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);

        // Find the view whose background you want to modify
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);

        // Set a new background drawable
        constraintLayout.setBackgroundResource(R.drawable.semi_red);

        // Set the modified viewA as the content view of the activity
        setContentView(viewA);
    }
    private void changeXmlOrange() {
        // Inflate XML file "A"
        LayoutInflater inflater = LayoutInflater.from(this);
        View viewA = inflater.inflate(R.layout.register, null);

        // Find the view whose background you want to modify
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);

        // Set a new background drawable
        constraintLayout.setBackgroundResource(R.drawable.semi_red);

        // Set the modified viewA as the content view of the activity
        setContentView(viewA);
    }
}
