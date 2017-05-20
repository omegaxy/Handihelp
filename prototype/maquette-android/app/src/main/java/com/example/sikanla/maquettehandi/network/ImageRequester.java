package com.example.sikanla.maquettehandi.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.text.BoringLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sikanla.maquettehandi.R;
import com.example.sikanla.maquettehandi.identification.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sikanla on 07/05/2017.
 */

public class ImageRequester {
    private String serverurl = "http://178.62.33.9/";

    public interface ImageInterface {
        void getUrl(String url);
    }

    public interface ImageInterface2 {
        void onImageSent(Boolean success);
    }

    public void getImage(String userId, Context context, final ImageInterface imageInterface) {
        User user = new User();
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", user.getAPIKEY());
        new AllRequest(context, parameters, headers, "/user/picture/" + userId, AllRequest.GET, new AllRequest.CallBackConnector() {
            @Override
            public void CallBackOnConnect(String response, Boolean success) {
                String url = null;
                try {
                    url = new JSONObject(response).getString("image_url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imageInterface.getUrl(serverurl + url);
            }
        });

    }

    public void sendImage(Bitmap bitmap, final Context context, final ImageInterface2 imageInterface2) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
                if (success) {
                    Toast.makeText(context, "Image Envoyée", Toast.LENGTH_LONG).show();
                    imageInterface2.onImageSent(true);
                } else {
                    Toast.makeText(context, "Erreur !", Toast.LENGTH_LONG).show();
                    imageInterface2.onImageSent(false);

                }

            }
        });

    }
}
