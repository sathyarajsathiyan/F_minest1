package com.example.minest1;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minest1.util.DressCombinationClass;
import com.example.minest1.util.DressPoJo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Splashscreen extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    //variables
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.botton_animation);
    //hooks
        image=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView5);
        slogo=findViewById(R.id.textView6);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogo.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splashscreen.this ,Login.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(logo,"logo_name");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Splashscreen.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);


        //FOR TEST
        List<DressPoJo> tops = new ArrayList<DressPoJo>();
        List<DressPoJo> bots = new ArrayList<DressPoJo>();
        DressPoJo dress;

        //tops
        tops.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "coat",
                "White",
                "1",
                36
        ));
        tops.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "Shirt",
                "White",
                "1",
                37
        ));
        tops.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "Shirt",
                "Grey",
                "1",
                38
        ));
        tops.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "coat",
                "Red",
                "1",
                39
        ));
        tops.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "pullover",
                "Green",
                "1",
                40
        ));
        //bottom
        bots.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "pyjama",
                "Grey",
                "1",
                41
        ));
        bots.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "formals",
                "Black",
                "1",
                42
        ));
        bots.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "jeans",
                "Blue",
                "1",
                43
        ));
        bots.add(new DressPoJo(
                "/assets/images/1/INCI_IMG_1590421872504.jpg",
                "jeans",
                "Light Grey",
                "1",
                44
        ));

        List<List<DressPoJo>> collection = new ArrayList<List<DressPoJo>>(2);
        collection.add(tops);
        collection.add(bots);

       Set<List<DressPoJo>> combset =  DressCombinationClass.getCombinations(collection);

       tops.clear();
       bots.clear();
    }
}
