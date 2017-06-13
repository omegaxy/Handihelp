package com.example.sikanla.maquettehandi.Model;

/**
 * Created by Sikanla on 13/06/2017.
 */

public class InstantRequest {

    public String helpCategory;
    public String description;
    public String closeUsers;
    public String localisation;
    public String id;
    public String createdAt;
    public String idInstant;

    public String getHelpCategory() {
        return helpCategory;
    }

    public String getDescription() {
        return description;
    }

    public String getCloseUsers() {
        return closeUsers;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getIdInstant() {
        return idInstant;
    }

    public InstantRequest(String helpCategory, String description, String closeUsers,
                          String createdAt, String id, String idInstant) {
        this.helpCategory = helpCategory;
        this.description = description;
        this.closeUsers = closeUsers;
        this.id = id;
        this.createdAt = createdAt;
        this.idInstant = idInstant;


    }
}
