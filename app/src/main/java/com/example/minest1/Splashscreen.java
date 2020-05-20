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


    }
}