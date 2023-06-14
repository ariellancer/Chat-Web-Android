package com.example.exe3.webService;

import com.example.exe3.infoToDB.Chat;
import com.example.exe3.infoToDB.LoginData;
import com.example.exe3.infoToDB.Message;
import com.example.exe3.infoToDB.NewMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface WebServiceChats {
    @GET("Chats")
    Call<List<Chat>> getUpdatedContact(@Header ("Authorization") String token);

    @POST("Chats")
    Call<Chat> addingFriend( @Header ("Authorization") String token,@Body LoginData friend);

    @GET("Chats/{id}")
    Call<List<Message>> getChatById(@Header ("Authorization") String token, @Path("id") int id);

    @DELETE("Chats/{id}")
    Call<Void> deleteChatById(@Header ("Authorization") String token, @Path("id") int id);

    @POST("Chats/{id}/Messages")
    Call<Message> postMessagesById(@Header ("Authorization") String token, @Body NewMessage newMessage);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessagesById(@Header ("Authorization") String token);
}
