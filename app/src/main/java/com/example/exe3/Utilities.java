package com.example.exe3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utilities {
    public static String editTime(String time){
        int start = time.indexOf("T");
        return time.substring(start +1,start+6);
    }
    public static Bitmap bitmapPic(String img) {
        byte[] decodedBytes = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    public static String extractImage(String webBase64) {
        int start = webBase64.indexOf(",");
        return webBase64.substring(start +1);
    }
}
