package com.example.sikanla.maquettehandi.Model;

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


}

