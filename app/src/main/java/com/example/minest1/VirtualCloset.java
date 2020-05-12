package com.example.minest1;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minest1.HomeAdapter.ClosetAdapter;

import java.util.ArrayList;
import java.util.List;

public class VirtualCloset extends DashbordMain {
RecyclerView closet;
    List<Integer> top_img;
    List<Integer> button_img;
    ClosetAdapter closetAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_closet);
        closet=findViewById(R.id.closet);

        top_img=new ArrayList<Integer>();
        button_img=new ArrayList<Integer>();
        top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);
        top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);
        top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);
        top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);
        top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);top_img.add(R.drawable.transparent_fanshion);
        button_img.add(R.drawable.transparent_fanshion);
        closetAdapter=new ClosetAdapter(this,top_img,button_img);


        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
     closet.setLayoutManager(gridLayoutManager);
    closet.setAdapter(closetAdapter);

    }
}
