package com.example.ateam_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ateam_app.MangeTipPackage.ManageTipFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    MainFragment mainFragment;
    IrdntListFragment irdntListFragment;
    CamFragment camFragment;
    RecipeFragment recipeFragment;
    ManageTipFragment manageTipFragment;

    Button btnManageTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navi_drawer_open, R.string.navi_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_view);
        irdntListFragment = new IrdntListFragment();
        camFragment = new CamFragment();
        recipeFragment = new RecipeFragment();
        manageTipFragment = new ManageTipFragment();

        btnManageTip = findViewById(R.id.btnManageTip);
        btnManageTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentChange(5);
            }
        });

    }

    public void onFragmentChange (int state) {
        if (state == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        } else if (state == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, irdntListFragment).commit();
        } else if (state == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, camFragment).commit();
        } else if (state == 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, recipeFragment).commit();
        } else if (state == 5) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, manageTipFragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}