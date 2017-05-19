package com.example.sikanla.maquettehandi.Model;

/**
 * Created by Sikanla on 09/05/2017.
 */

public class PlannedRequest {
    public static String n1 = "Mobilité";
    public static String n2 = "Vision";
    public static String n3 = "Travaux manuels";
    public static String n4 = "Tâches ménagères";
    public static String n5 = "Force physique";
    public static String n6 = "Tâches techniques";
    public static String n7 = "Communication";
    public static String n8 = "Démarches administratives";
    public static String n9 = "Jardins";
    public static String n10 = "Social";
    public static String n11 = "Autres";


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

