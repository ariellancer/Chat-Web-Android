package com.example.exe3.webService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatApi {
    Retrofit retrofit;
    WebServiceChats webServiceChats;

    public ChatApi() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        webServiceChats = retrofit.create(WebServiceChats.class);
    }
}
