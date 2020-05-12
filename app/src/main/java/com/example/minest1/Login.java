package com.example.minest1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.*;

public class Login extends AppCompatActivity {
    DataHelper dataHelper;
    SharedPreferences pref, sharedpreferences;
    Cursor cursor;
    Button signup, btn_login, btn_for_password;
    TextInputEditText email, password;
    TextInputLayout txt_email, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dataHelper = new DataHelper(this);
        setContentView(R.layout.activity_login);


        signup = findViewById(R.id.sign_screen);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);

        txt_email = (TextInputLayout) findViewById(R.id.txt_email);
        txt_password = (TextInputLayout) findViewById(R.id.txt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        checkConnection();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //Get values from TextInputEditText fields
                    String Email = email.getText().toString();
                    String Password = password.getText().toString();
                    // Query check email and password
                    SQLiteDatabase db = dataHelper.getReadableDatabase();
                    cursor = db.rawQuery("SELECT id FROM users WHERE email = '" + Email + "' AND password ='" + Password + "'", null);
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {

                        sharedpreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("session", cursor.getString(0).toString());
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Successfully Logged in",
                                Toast.LENGTH_LONG).show();
                        //User Logged in Successfully Launch You home screen activity
                        Intent intent = new Intent(Login.this, DashbordMain.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to log in , please try again",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);

            }
        });
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String session_id = pref.getString("session", null);
        if (session_id != null) {
            //User Logged in Successfully Launch You home screen activity
            Intent intent = new Intent(Login.this, DashbordMain.class);
            startActivity(intent);
            finish();
        }
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        //Handling validation for Password field
        if (Email.isEmpty()) {
            valid = false;
            txt_email.setError("Please enter valid Username!");
        } else {
            valid = true;
            txt_email.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            txt_password.setError("Please enter valid Password!");
        } else if (Password.length() < 8) {
            valid = false;
            txt_password.setError("Password is to short!");
        } else {
            valid = true;
        }
        return valid;
    }

    //this method used for the internet check method
    public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "wifi Enabled", Toast.LENGTH_SHORT).show();
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "wifi Enabled", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Connect to the Internet", Toast.LENGTH_SHORT).show();
        }


    }

}
