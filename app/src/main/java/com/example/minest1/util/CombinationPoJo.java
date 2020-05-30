package com.example.minest1.util;

public class CombinationPoJo {
    //In DB it is wear

    private  String top_image;
    private  String top_color;
    private  String top_type;
    private  String bottom_image;
    private  String bottom_color;
    private  String bottom_type;
    private  String wear_date;
    private  int id;


    public CombinationPoJo() {
    }

    public CombinationPoJo(String top_image, String top_color, String top_type, String bottom_image, String bottom_color, String bottom_type, String wear_date) {
        this.top_image = top_image;
        this.top_color = top_color;
        this.top_type = top_type;
        this.bottom_image = bottom_image;
        this.bottom_color = bottom_color;
        this.bottom_type = bottom_type;
        this.wear_date = wear_date;
    }

    public String getTop_image() {
        return top_image;
    }

    public void setTop_image(String top_image) {
        this.top_image = top_image;
    }

    public String getTop_color() {
        return top_color;
    }

    public void setTop_color(String top_color) {
        this.top_color = top_color;
    }

    public String getTop_type() {
        return top_type;
    }

    public void setTop_type(String top_type) {
        this.top_type = top_type;
    }

    public String getBottom_image() {
        return bottom_image;
    }

    public void setBottom_image(String bottom_image) {
        this.bottom_image = bottom_image;
    }

    public String getBottom_color() {
        return bottom_color;
    }

    public void setBottom_color(String bottom_color) {
        this.bottom_color = bottom_color;
    }

    public String getBottom_type() {
        return bottom_type;
    }

    public void setBottom_type(String bottom_type) {
        this.bottom_type = bottom_type;
    }

    public String getWear_date() {
        return wear_date;
    }

    public void setWear_date(String wear_date) {
        this.wear_date = wear_date;
    }
}
