package com.example.minest1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    public TextView appBarTxt;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private float lastTranslate = 0.0f;
    private ImageView imgLeftToolbar;
    private boolean isOpenOrClose = false;
    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int layout;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void setContentView(int layout) {
        View parentView = LayoutInflater.from(Dashboard.this).inflate(R.layout.activity_dashboard, null);
        FrameLayout frame = (FrameLayout) parentView.findViewById(R.id.frame);
        getLayoutInflater().inflate(layout, frame, true);
        super.setContentView(parentView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //appBarTxt = (TextView) findViewById(R.id.app_bar_txt);
        imgLeftToolbar = (ImageView) findViewById(R.id.img_left_toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setUpNavigationView();
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer) {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (drawerView.getWidth() * slideOffset);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    findViewById(R.id.app_bar_main_layout).setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    findViewById(R.id.app_bar_main_layout).startAnimation(anim);
                    lastTranslate = moveFactor;
                }
            }
        };

        drawer.setDrawerListener(drawerToggle);

        imgLeftToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenOrClose)
                    drawer.closeDrawers();
                else
                    drawer.openDrawer(GravityCompat.START);
                drawer.setDrawerListener(drawerToggle);
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View view) {
                isOpenOrClose = true;

            }

            @Override
            public void onDrawerClosed(View view) {
                isOpenOrClose = false;

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }


    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Dashboard.this, DashbordMain.class));
                        finish();
                        break;
                    case R.id.nav_outfits:
                        startActivity(new Intent(Dashboard.this, Outfits.class));
                        finish();
                        break;

                    case R.id.nav_today:
                        startActivity(new Intent(Dashboard.this, DashbordMain.class));
                        finish();
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Dashboard.this, Profile.class));
                        finish();
                        break;
                    case R.id.nav_closet:
                        startActivity(new Intent(Dashboard.this, VirtualCloset.class));
                        finish();


                        break;

                    case R.id.nav_setting:
                        startActivity(new Intent(Dashboard.this, Settings.class));
                        finish();
                        break;
                    case R.id.nav_rate:
                        Toast.makeText(Dashboard.this, "pelease Rate us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(Dashboard.this, "version 1.0", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        startActivity(new Intent(Dashboard.this, DashbordMain.class));
                        finish();
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                return true;
            }
        });
    }


    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
