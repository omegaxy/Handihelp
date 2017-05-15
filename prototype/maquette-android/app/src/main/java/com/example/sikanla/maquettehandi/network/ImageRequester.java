package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.BoringLayout;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.identification.User;

import java.io.ByteArrayOutputStream;
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
        User user = new User();
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        new AllRequest(context, parameters, headers, "/user/picture/" + userId, AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                final String pureBase64Encoded = response.substring(response.indexOf(",") + 1);
                final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                bitmapInterface.getBitmap(bitmap);
            }
        });

    }

    public void sendImage(Bitmap bitmap, final Context context) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = "data: image/jpg;base64," + Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("encoded image: ", encodedImage);

        User user = new User();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("image", encodedImage);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        new AllRequest(context, parameters, headers, "/user/picture", AllRequest.POST, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        });

    }
}
