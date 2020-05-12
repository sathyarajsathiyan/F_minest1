package com.example.minest1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class Settings extends Dashboard {
    Switch switch1;
    //1.Notifcation channnel
    //2.notification Bulider
    //3.Notification Manager
    private  static  final String CHANNEL_ID = "Minest";
    private  static  final String  CHANNEL_NAME= "Minest";
    private  static  final String  CHANNEL_DESC= "Minest Notification";
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription(CHANNEL_DESC);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }


        switch1=findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){

                    Toast.makeText(Settings.this, "NOtification turn on", Toast.LENGTH_SHORT).show();
                    dispalyNotificatio();
                }
                else {
                    Toast.makeText(Settings.this, " NOtification turn Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private  void dispalyNotificatio(){
        String message="heelo";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification).setContentTitle("Wardore")
                .setContentText("Your Closet is Ready")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        // notificationManagerCompat.notify(1,builder.build());
        Intent intent = new Intent(Settings.this,DashbordMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message",message);

        PendingIntent pendingIntent=PendingIntent.getActivity(Settings.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());






    }

    @Override
    public void onBackPressed() {
        count++;
                if(count>=1){
                    Intent intent= new Intent(Settings.this,DashbordMain.class);
                    startActivity(intent);
                    finish();
                    super.onBackPressed();
                }


    }
}
