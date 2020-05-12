package com.example.minest1.HomeAdapter;

public class FeaturedHelperClass {
    int img1,img2;
    String txt1,txt2,txt3;

    public FeaturedHelperClass(Integer img1, Object img2, String txt1, String txt2, String txt3) {
        this.img1 = img1;
        this.img2 = (int) img2;
        this.txt1 = txt1;
        this.txt2 = txt2;
        this.txt3 = txt3;

    }
    public int getImg1() {
        return img1;
    }

    public int getImg2() {
        return img2;
    }

    public String getTxt1() {
        return txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public String getTxt3() {
        return txt3;
    }


}
