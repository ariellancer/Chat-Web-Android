package com.example.exe3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        });
    }


}
