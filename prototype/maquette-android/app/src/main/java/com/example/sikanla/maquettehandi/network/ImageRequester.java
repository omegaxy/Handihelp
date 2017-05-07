package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 07/05/2017.
 */

public class ImageRequester {

    public interface BitmapInterface {
        void getBitmap(Bitmap bitmap);
    }

    public void getImage(String userId, Context context, final BitmapInterface bitmapInterface) {
        Map<String, String> parameters = new HashMap<>();
        new AllRequest(context, parameters, "/user/picture/" + userId, AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response) {
                final String pureBase64Encoded = response.substring(response.indexOf(",") + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                bitmapInterface.getBitmap(bitmap);
            }
        });

    }
}
