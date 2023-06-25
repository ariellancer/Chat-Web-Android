package com.example.exe3.webService;

import com.example.exe3.infoToDB.ContactInfo;
import com.example.exe3.service.FireBaseData;
import com.example.exe3.infoToDB.LoginData;
import com.example.exe3.infoToDB.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceUsers {
    @GET("Users/{username}")
    Call<ContactInfo> getUsernameInfo(@Header("Authorization") String token ,@Path("username") String username);

    @POST("Users")
    Call<Void> createNewUser(@Body User user);

    @POST("Tokens")
    Call<String> login(@Body LoginData user);


    @POST("Tokens/fireBaseToken")
    Call<String> fireBaseToken(@Header("Authorization") String token,@Body FireBaseData user);

}

