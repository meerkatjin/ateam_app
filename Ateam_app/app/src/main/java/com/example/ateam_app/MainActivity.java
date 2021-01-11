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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_view);
        irdntListFragment = new IrdntListFragment();
        camFragment = new CamFragment();
        recipeFragment = new RecipeFragment();
        manageTipFragment = new ManageTipFragment();
    }

    public void onFragmentChange (int state) {
        if (state == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
        } else if (state == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, irdntListFragment).commit();
        } else if (state == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, camFragment).commit();
        } else if (state == 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, recipeFragment).commit();
        } else if (state == 5) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, manageTipFragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}