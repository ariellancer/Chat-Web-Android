package com.example.exe3.webService;

import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.service.FireBaseData;
import com.example.exe3.infoToDB.LoginData;
import com.example.exe3.infoToDB.User;

import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi {
    private static UserApi instance;
    private Retrofit retrofit;
    private WebServiceUsers webServiceUsers;

    private UserApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceUsers = retrofit.create(WebServiceUsers.class);
    }

    public static UserApi getInstance() {
        if (instance == null) {
            instance = new UserApi();
        }
        return instance;
    }

    public void setRetrofit(String newUrl) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(newUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceUsers = retrofit.create(WebServiceUsers.class);
    }

    public Call<Void> createNewUser(User user) {
        return webServiceUsers.createNewUser(user);
    }

    public Call<String> login(LoginData loginData) {
        return webServiceUsers.login(loginData);
    }
    public Call<String> fireBaseTokenGenerate(FireBaseData user,String token) {
        return webServiceUsers.fireBaseToken("Bearer " + token,user);
    }

    public CompletableFuture<ContactInfo> getUsernameInfo(String token, String username) {
        CompletableFuture<ContactInfo> future = new CompletableFuture<>();
        Call<ContactInfo> call = webServiceUsers.getUsernameInfo(token, username);

        call.enqueue(new Callback<ContactInfo>() {
            @Override
            public void onResponse(Call<ContactInfo> call, Response<ContactInfo> response) {
                if (response.isSuccessful()) {
                    ContactInfo contactInfo = response.body();
                    future.complete(contactInfo);
                } else {
                    future.completeExceptionally(new Exception("Failed to get username info"));
                }
            }

            @Override
            public void onFailure(Call<ContactInfo> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }
}
