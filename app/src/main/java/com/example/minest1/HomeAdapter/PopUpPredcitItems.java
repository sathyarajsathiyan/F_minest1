package com.example.minest1.HomeAdapter;

public class PopUpPredcitItems {
    String ImageUrl;
    String type_name;
    String color;
    private String user_id;
    private int id;


    public PopUpPredcitItems(String image, String type_names, String Color, String users_id, int ids) {
        ImageUrl = image;
        type_name = type_names;
        color = Color;
        user_id = users_id;
        id = ids;

    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
