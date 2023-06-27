package com.example.exe3.webService;

import android.content.Context;
import android.widget.Toast;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.Contact;
import com.example.exe3.infoToDB.GetFriend;
import com.example.exe3.infoToDB.NewMessage;
import com.example.exe3.infoToDB.ReturnMessage;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatApi {
    private static ChatApi instance;
    private Retrofit retrofit;
    private WebServiceChats webServiceChats;

    private ChatApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceChats = retrofit.create(WebServiceChats.class);
    }

    public static ChatApi getInstance() {
        if (instance == null) {
            instance = new ChatApi();
        }
        return instance;
    }

    public void setRetrofit(String newUrl, Context applicationContext) {
        Retrofit tempRetrofit = this.retrofit;
        WebServiceChats tempWeb = this.webServiceChats;
        try {

            this.retrofit = new Retrofit.Builder().baseUrl(newUrl).addConverterFactory(GsonConverterFactory.create()).build();
            webServiceChats = retrofit.create(WebServiceChats.class);
        }catch (Exception e){
            this.retrofit = tempRetrofit;
            this.webServiceChats= tempWeb;
        }
    }

    public CompletableFuture<ReturnMessage> postMessagesById(String token, int id, NewMessage newMessage){
        CompletableFuture<ReturnMessage> future = new CompletableFuture<>();
        Call <ReturnMessage> call = webServiceChats.postMessagesById(token,id,newMessage);
        call.enqueue(new Callback<ReturnMessage>() {
            @Override
            public void onResponse(Call<ReturnMessage> call, Response<ReturnMessage> response) {
                if (response.isSuccessful()) {
                    ReturnMessage messages = response.body();
                    future.complete(messages);
                } else {
                    future.completeExceptionally(new Exception("Failed to send message"));
                }
            }

            @Override
            public void onFailure(Call<ReturnMessage> call, Throwable t) {
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


    public CompletableFuture<Contact> addContact(Context activity,String username, String token){
        GetFriend friend=new GetFriend(username);
        CompletableFuture<Contact> future = new CompletableFuture<>();
        Call<Contact> call = webServiceChats.addingFriend(token, friend);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(activity, "Add friend  successful ", Toast.LENGTH_SHORT).show();
                    future.complete(response.body());
                } else {
                    Toast.makeText(activity, "Add friend failed ", Toast.LENGTH_SHORT).show();
                    future.completeExceptionally(new Exception("Failed to get username info"));
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;

    }



    public CompletableFuture<Void> deleteContact(int id, String token){
        CompletableFuture<Void> future = new CompletableFuture<>();
        Call<Void> call = webServiceChats.deleteChatById(token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    Contact contact=response.body();
//                    ContactInfo contactInfo = response;
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new Exception("Failed to get username info"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;

    }





}
