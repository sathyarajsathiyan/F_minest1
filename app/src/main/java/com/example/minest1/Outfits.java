package com.example.minest1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

public class Outfits extends Dashboard {
    private int count = 0;
    private CardView t_shirt,shirt,pullover,coat,trouser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfits);
        t_shirt = findViewById(R.id.t_shirt);
       shirt=findViewById(R.id.shirt);
       pullover=findViewById(R.id.pullover);
       coat=findViewById(R.id.coat);
       trouser=findViewById(R.id.trouser);


        t_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outfits.this,Tshirt.class);
                intent.putExtra("type_name","t_shirt");
                startActivity(intent);
            }
        });
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outfits.this,Tshirt.class);
                intent.putExtra("type_name","shirt");
                startActivity(intent);
            }
        });
        pullover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outfits.this,Tshirt.class);
                intent.putExtra("type_name","pullover");
                startActivity(intent);
            }
        });
        coat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outfits.this,Coat.class);
                intent.putExtra("type_name","coat");
                startActivity(intent);
            }
        });
        trouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Outfits.this,Trouser.class);
                intent.putExtra("type_name","trouser");
                startActivity(intent);
            }
        });


    }
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            Intent intent = new Intent(Outfits.this, DashbordMain.class);
            startActivity(intent);
            finish();
            super.onBackPressed();
        }


    }
}
