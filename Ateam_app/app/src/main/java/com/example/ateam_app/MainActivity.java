package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    IrdntListFragment irdntListFragment;
    CamFragment camFragment;
    RecipeFragment recipeFragment;
    ManageTipFragment manageTipFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}