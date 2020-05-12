package com.example.minest1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Profile extends Dashboard {
    TextInputEditText ext_fullname,etx_email,etx_date,etx_favclr,etx_pass;
    TextInputLayout txt_fullname ,txt_email ,txt_date ,txt_favclr ,txt_pass ;
    TextView Username,email,txt_edit,txt_cpass;
    Button btn_logout,saveButtonPassword;

    SharedPreferences pref; //Declaration SharedPreferences
    Cursor cursor; //Declaration Cursor
    DataHelper dataHelper; //Declaration SqliteHelper
    private LayoutInflater inflater;
    TextInputLayout txt_Pass, txt_ConfirmPassword;
    EditText pass_word, confirmPass;


    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txt_edit = findViewById(R.id.txt_edit);
        pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        String session_id = pref.getString("session", null);
        dataHelper = new DataHelper(this);
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT full_name,email,password,dob,favcolor FROM users WHERE id = '" + session_id + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ext_fullname = findViewById(R.id.ext_fullname);
            etx_email = findViewById(R.id.etx_email);
            etx_date = findViewById(R.id.etx_date);
            etx_favclr = findViewById(R.id.etx_favclr);
            //etx_pass = findViewById(R.id.etx_pass);
            //textview for the user
            Username=findViewById(R.id.Username);
            email=findViewById(R.id.email);
            ext_fullname.setText("" + cursor.getString(0).toString());
            etx_email.setText("" + cursor.getString(1).toString());
            etx_date.setText("" + cursor.getString(3).toString());
            etx_favclr.setText("" + cursor.getString(4).toString());
           //etx_pass.setText("" + cursor.getString(2).toString());
            Username.setText("" + cursor.getString(0).toString());
            email.setText("" + cursor.getString(1).toString());
        }
        btn_logout=findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                //User Logged in Successfully Launch You home screen activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        txt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = ext_fullname.getText().toString();
                    String Email = etx_email.getText().toString();
                    String Dob = etx_date.getText().toString();
                    String Favcolor = etx_favclr.getText().toString();
                    //String Password = etx_pass.getText().toString();
                    pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
                    String session_id = pref.getString("session", null);
                    SQLiteDatabase mdb = dataHelper.getWritableDatabase();
                    mdb.execSQL("UPDATE users SET full_name = '" + UserName + "', email = '" + Email + "', Dob = '" + Dob + "', favcolor = '" + Favcolor + "'" +
                            "WHERE id = '" + session_id + "'");
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        Toast.makeText(getApplicationContext(), "UPDATED successfully",
                                Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        txt_cpass = findViewById(R.id.txt_cpass);
        txt_cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(Profile.this);
                inflater = LayoutInflater.from(Profile.this);
                view = inflater.inflate(R.layout.popup_password, null);


                txt_Pass = (TextInputLayout) view.findViewById(R.id.txt_Pass);
                txt_ConfirmPassword = (TextInputLayout) view.findViewById(R.id.txt_ConfirmPassword);
                pass_word = (EditText) view.findViewById(R.id.etx_password);
                confirmPass = (EditText) view.findViewById(R.id.etx_confirmPass);


                alertDialogBuilder.setView(view);
                dialog = alertDialogBuilder.create();
                dialog.show();
                saveButtonPassword=(Button)view. findViewById(R.id.saveButtonPassword);
                saveButtonPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validate1()) {

                            String Password = pass_word.getText().toString();

                            pref = getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
                            String session_id = pref.getString("session", null);
                            SQLiteDatabase mdb = dataHelper.getWritableDatabase();
                            mdb.execSQL("UPDATE users SET  password = '" + Password + "'" + "WHERE id = '" + session_id + "'");
                            cursor.moveToFirst();
                            if (cursor.getCount() > 0) {
                                Toast.makeText(getApplicationContext(), "UPDATED  Password successfully ",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Profile.this, Profile.class));
                            }

                        }
                    }
                });

            }


        });


    }
    private boolean validate() {
        boolean valid = false;
        String UserName = ext_fullname.getText().toString();
        String Email = etx_email.getText().toString();
        String Dob = etx_date.getText().toString();
        String Favcolor = etx_favclr.getText().toString();


        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            txt_fullname.setError("Please enter valid Username!");
        } else {
            valid = true;

        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            txt_email.setError("Please enter valid email!");
        } else {
            valid = true;

        }
//for dob
        if (Dob.isEmpty()) {
            valid = false;
            txt_date.setError("Please enter valid Username!");
        } else {
            valid = true;

        }
        //favcolor
        if (Favcolor.isEmpty()) {
            valid = false;
            txt_favclr.setError("Please enter valid Username!");
        } else {
            valid = true;

        }




        return valid;
    }
    private boolean validate1() {
        boolean valid1 = false;
        String Pass_word = pass_word.getText().toString();
        String ConPassword = confirmPass.getText().toString();
//Handling validation for Password field
        if (Pass_word.isEmpty()) {
            valid1 = false;
            txt_Pass.setError("Please enter valid password!");
        } else if (Pass_word.length() < 8) {
            valid1 = false;
            txt_Pass.setError("Password is to short, min 8 char!");
        } else {
            valid1 = true;
            txt_Pass.setError(null);

        }
        //Handling validation for Confirm Password field
        if (ConPassword.isEmpty() || !ConPassword.equals(Pass_word)) {
            valid1 = false;
            txt_ConfirmPassword.setError("Confirm Password is not the same!");
        } else {
            valid1 = true;
            txt_ConfirmPassword.setError(null);
        }


        return valid1;
    }
}
