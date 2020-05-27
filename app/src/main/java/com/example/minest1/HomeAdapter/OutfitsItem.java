package com.example.minest1.HomeAdapter;

public class OutfitsItem {
    private String oImageUrl;

    public OutfitsItem(String ImageUrl) {
        oImageUrl = ImageUrl;
    }

    public String getImageUrl() {
        return oImageUrl;
    }

    public void setImageUrl(String oImageUrl) {
        this.oImageUrl = oImageUrl;
    }
}
