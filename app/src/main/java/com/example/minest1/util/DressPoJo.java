package com.example.minest1.util;

public class DressPoJo {

    private  String image;
    private  String type_name;
    private  String color;
    private  String user_id;
    private  int id;

    public DressPoJo() {
    }

    public DressPoJo(String image, String type_name, String color, String user_id, int id) {
        this.image = image;
        this.type_name = type_name;
        this.color = color;
        this.user_id = user_id;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
