package com.example.exe3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Parcelable {
    private String userName;
    private int pictureId;
    private String lastMassage;
    private String lastMassageSendingTime;

    public User(String userName, int pictureId, String lastMassage, String lastMassageSendingTime) {
        this.userName = userName;
        this.pictureId = pictureId;
        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    protected User(Parcel in) {
        userName = in.readString();
        pictureId = in.readInt();
        lastMassage = in.readString();
        lastMassageSendingTime = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getPictureId() {
        return pictureId;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(pictureId);
        dest.writeString(lastMassage);
        dest.writeString(lastMassageSendingTime);
    }
}