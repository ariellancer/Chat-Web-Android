package com.example.exe3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exe3.R;
import com.example.exe3.infoToDB.User;
import com.example.exe3.webService.UserApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity{
    Button btnRegister;
    Button onClick;
    UserApi userApi;
    User user;

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
                userApi = UserApi.getInstance();
                if( validateFields()){
                    createNewUser();

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
    private void createNewUser (){
        try {
            Call<Void> call = userApi.createNewUser(user);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Registration failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Toast.makeText(Register.this, "Registration failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("RegistrationError", t.getMessage(), t); // Log the error
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Register.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("RegistrationError", e.getMessage(), e); // Log the error
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
        String picture;
        try {
            Drawable drawable = imageView.getDrawable();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String imageType;
            if (bitmap.hasAlpha()) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageType = "data:image/png;base64,";
            }
            else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                imageType = "data:image/jpeg;base64,";
            }
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            picture =  imageType + Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return false;
        }
        user = new User(username,password,picture,displayName);
            return true;
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

}




