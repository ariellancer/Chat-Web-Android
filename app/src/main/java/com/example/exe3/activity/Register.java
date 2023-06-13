package com.example.exe3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exe3.R;

public class Register extends AppCompatActivity{
    Button btnRegister;
    Button onClick;
    private EditText usernameEditText, displayNameEditText, passwordEditText, verifyPasswordEditText;

    private static final int REQUEST_CODE_SELECT_PICTURE = 1;
    private ImageView imageView;
    private Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnRegister=findViewById(R.id.btnRegister);
        onClick = findViewById(R.id.clickToLogin);
        onClick.setOnClickListener(fun -> finish());

        usernameEditText = findViewById(R.id.registered_username);
        displayNameEditText = findViewById(R.id.displayName);
        passwordEditText = findViewById(R.id.password_text);
        verifyPasswordEditText = findViewById(R.id.password_text_verify);

        Button btnChoosePic = findViewById(R.id.btn_choose_pic);
        imageView = findViewById(R.id.imageView);

        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateFields()) {
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);

                    startActivity(intent);
                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_PICTURE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(selectedImageUri);
            }
        }
    }


    private boolean validateFields() {
        String username = usernameEditText.getText().toString().trim();
        String displayName = displayNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String verifyPassword = verifyPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username is required");
            usernameEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(displayName)) {
            displayNameEditText.setError("Display name is required");
            displayNameEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }
        if (!isValidPassword(password)) {
            passwordEditText.setError("Password must be at least 8 characters long, contain at least one uppercase letter, and at least one number");
            passwordEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(verifyPassword)) {
            verifyPasswordEditText.setError("Please verify your password");
            verifyPasswordEditText.requestFocus();
            return false;
        }

        if (!password.equals(verifyPassword)) {
            verifyPasswordEditText.setError("Passwords do not match");
            verifyPasswordEditText.requestFocus();
            return false;
        }
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please choose a picture", Toast.LENGTH_SHORT).show();
            // No image selected
            // You can display an error message or handle it as needed
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

}




