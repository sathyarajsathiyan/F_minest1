package com.example.minest1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class Tshirt extends AppCompatActivity {
    private String type_name;
    int user_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt);
        //take user_id sharedpref
        Intent intent = getIntent();
        if (intent.hasExtra("type_name")) {
            type_name = intent.getStringExtra("type_name");
            Listoutfits(type_name,1);
        } else {
            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Something went wrong!")
                    .show();
        }


    }

    private void Listoutfits(String type, int id) {

        String dbUrl = "http://192.168.1.3:4000/Dress"+"?type_name="+type+"&user_id="+id;
    }

}
