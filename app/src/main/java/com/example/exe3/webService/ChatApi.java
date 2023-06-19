package com.example.exe3.webService;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.infoToDB.NewMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatApi {
    Retrofit retrofit;
    WebServiceChats webServiceChats;

    public ChatApi() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        webServiceChats = retrofit.create(WebServiceChats.class);
    }
    public CompletableFuture<Integer> postMessagesById(String token, int id, NewMessage newMessage){
        CompletableFuture<Integer> future = new CompletableFuture<>();
        Call <Integer> call = webServiceChats.postMessagesById(token,id,newMessage);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer messages = response.body();
                    future.complete(messages);
                } else {
                    future.completeExceptionally(new Exception("Failed to send message"));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    public CompletableFuture<Chat> getMessages(String token, int id){
        CompletableFuture<Chat> future = new CompletableFuture<>();
        Call <Chat> call = webServiceChats.getChatById(token,id);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    Chat messages = response.body();
                    future.complete(messages);
                } else {
                    future.completeExceptionally(new Exception("Failed to get messages"));
                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
    public CompletableFuture<ArrayList<Contact>> getContact(String token){
        CompletableFuture<ArrayList<Contact>> future = new CompletableFuture<>();
        Call <ArrayList<Contact>> call = webServiceChats.getUpdatedContact(token);
        call.enqueue(new Callback<ArrayList<Contact>>() {
            @Override
            public void onResponse(Call<ArrayList<Contact>> call, Response<ArrayList<Contact>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Contact> contacts = response.body();
                    future.complete(contacts);
                } else {
                    future.completeExceptionally(new Exception("Failed to get all contacts"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Contact>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
