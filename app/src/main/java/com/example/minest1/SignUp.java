package com.example.minest1;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUp extends AppCompatActivity {
        DataHelper dataHelper;
        Cursor cursor;
    TextInputEditText full_name,email,dob,favcolor,password,Conpassword;
    TextInputLayout txtFull_name ,txtEmail ,txt_Dob ,txt_favcolor ,txt_password ,txt_con_password;
    //EditText full_name,email,dob,favcolor,password,Conpassword;
Button btn_go;
TextView txt_login;
int year,month,day;
     DatePicker datePicker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        dataHelper = new DataHelper(this);

       /** full_name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        dob=(EditText)findViewById(R.id.dOb);
        favcolor=(EditText) findViewById(R.id.fav_color);
        password = (EditText) findViewById(R.id.password);
        Conpassword = (EditText) findViewById(R.id.Conpassword);**/

       full_name=(TextInputEditText) findViewById(R.id.full_name);
        email= (TextInputEditText) findViewById(R.id.email);
        dob=(TextInputEditText) findViewById(R.id.dOb);
        favcolor=(TextInputEditText)findViewById(R.id.fav_color);
        password=(TextInputEditText) findViewById(R.id.password);
        Conpassword=(TextInputEditText) findViewById(R.id.Conpassword);

        txtFull_name=(TextInputLayout) findViewById(R.id.txtFull_name);
        txtEmail=(TextInputLayout) findViewById(R.id.txtEmail);
        txt_Dob=(TextInputLayout)findViewById(R.id.txt_Dob);
        txt_favcolor=(TextInputLayout)findViewById(R.id.txt_favcolor);
        txt_password=(TextInputLayout) findViewById(R.id.txt_password);
        txt_con_password=(TextInputLayout)findViewById(R.id.txt_con_password);

        btn_go=(Button) findViewById(R.id.btn_go);
        txt_login=(TextView) findViewById(R.id.txt_Login);

        //  calendar

        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(dob);
            }
        });





            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(validate()){
                        String Full_name=full_name.getText().toString();
                        String Email=email.getText().toString();
                        String Dob=dob.getText().toString();
                        String Favcolor=favcolor.getText().toString();
                        String Password=password.getText().toString();


                        // Query check email
                        SQLiteDatabase db = dataHelper.getReadableDatabase();
                        cursor = db.rawQuery("SELECT id FROM users WHERE email = '" + Email + "'",null);
                        cursor.moveToFirst();
                        if (cursor.getCount()>0) {
                            //Email exists with email input provided so show error user already exist
                            Toast.makeText(getApplicationContext(), "Username already exists",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{

                            SQLiteDatabase query = dataHelper.getWritableDatabase();
                            query.execSQL("insert into users(full_name, email, Dob, favcolor, password) values('" +
                                    Full_name+ "','" +
                                    Email + "','" +
                                    Dob +"','"+
                                    Favcolor +"','"+
                                    Password + "')");
                            Toast.makeText(getApplicationContext(), "User created successfully! Please Login",
                                    Toast.LENGTH_LONG).show();

                            //User Logged in Successfully Launch You home screen activity
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }

                }
            });


            txt_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                   finish();
                  }
                });


    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Full_name = full_name.getText().toString();
        String Email = email.getText().toString();
        String Dob=dob.getText().toString();
        String Favcolor=favcolor.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = Conpassword.getText().toString();

        //Handling validation for UserName field
        if (Full_name.isEmpty()) {
            valid = false;
            txtFull_name.setError("Please enter valid Username!");
        } else {
            valid = true;
            txtFull_name.setError(null);
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            txtEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            txtEmail.setError(null);
        }
//for dob
        if (Dob.isEmpty()) {
            valid = false;
            txt_Dob.setError("Please enter valid Username!");
        } else {
            valid = true;
            txt_Dob.setError(null);
        }
        //favcolor
        if (Favcolor.isEmpty()) {
            valid = false;
            txt_favcolor.setError("Please enter valid Username!");

        } else {
            valid = true;
            txt_favcolor.setError(null);
        }
        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            txt_password.setError("Please enter valid password!");
        } else if (Password.length() < 8 ) {
            valid = false;
            txt_password.setError("Password is to short, min 8 char!");
        } else {
            valid = true;
            txt_password.setError(null);

        }
        //Handling validation for Confirm Password field
        if (ConfirmPassword.isEmpty() || !ConfirmPassword.equals(Password)) {
            valid = false;
            txt_con_password.setError("Confirm Password is not the same!");
        } else {
            valid = true;
            txt_con_password.setError(null);
        }

        return valid;
    }
    private void showDateDialog(final TextInputEditText dob ){
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                dob.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(SignUp.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}

