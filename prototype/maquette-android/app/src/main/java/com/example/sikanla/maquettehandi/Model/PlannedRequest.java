package com.example.sikanla.maquettehandi.Model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Sikanla on 09/05/2017.
 */

public class PlannedRequest {
    public String helpCategory;
    public String description;
    public String scheduledAt;
    public String localisation;
    public String id;

    public PlannedRequest(String helpCategory, String description, String scheduledAt, String localisation, String id) {
        this.helpCategory = helpCategory;
        this.description = description;
        this.scheduledAt = scheduledAt;
        this.localisation = localisation;
        this.id = id;
    }

    // Factory method to convert an array of JSON objects into a list of objects
    // User.fromJson(jsonArray);
    public static ArrayList<PlannedRequest> fromJson(JSONArray jsonObjects) {
        ArrayList<PlannedRequest> plannedRequests = new ArrayList<PlannedRequest>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                //todo
                plannedRequests.add(new PlannedRequest(jsonObjects.getJSONObject(i).getString("f"),
                        jsonObjects.getJSONObject(i).getString("f"),jsonObjects.getJSONObject(i).getString("f"),
                        jsonObjects.getJSONObject(i).getString("f"),jsonObjects.getJSONObject(i).getString("f")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return plannedRequests;
    }
}

