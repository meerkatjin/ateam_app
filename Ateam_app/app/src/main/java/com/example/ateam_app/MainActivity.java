package com.example.ateam_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.ateam_app.manage_tip_package.ManageTipFragment;
import com.example.ateam_app.recipe_fragment.RecipeFragment;
import com.example.ateam_app.user_pakage.fragment.UserInfoChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.nio.BufferUnderflowException;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "main:MainActivity";
    Toolbar toolbar;

    MainFragment mainFragment;
    IrdntListFragment irdntListFragment;
    CamFragment camFragment;
    RecipeFragment recipeFragment;
    ManageTipFragment manageTipFragment;
    UserInfoChangeFragment userInfoChangeFragment;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //측면 메뉴 호출 (Navigation Drawer)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navi_drawer_open, R.string.navi_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_view);
        irdntListFragment = new IrdntListFragment();
        camFragment = new CamFragment();
        recipeFragment = new RecipeFragment();
        manageTipFragment = new ManageTipFragment();

        //하단 메뉴 (Bottom Navigation View)
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tabMain:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                        return true;
                    case R.id.tabIrdntList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, irdntListFragment).commit();
                        return true;
                    case R.id.tabCam:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, camFragment).commit();
                        return true;
                    case R.id.tabRecipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, recipeFragment).commit();
                        return true;
                    case R.id.tabManageTip:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, manageTipFragment).commit();
                        return true;

                }//switch-case
                return false;
            }//onNavigationItemSelected()
        });//bottomNavigationView.setOnNavigationItemSelectedListener()

    }//onCreate()

    //측면 메뉴(Navigation Drawer) 설정
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.nav_userInfoChange) {
            Toast.makeText(this, "회원정보 수정", Toast.LENGTH_SHORT).show();
            onFragmentSelected(0);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFragment).commit();
        } else if (id == R.id.nav_admin) {
            Toast.makeText(this, "관리자 메뉴", Toast.LENGTH_SHORT).show();
            onFragmentSelected(2);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void onFragmentSelected(int index) {
        Fragment curFragment = null;
        if(index == 0){
            curFragment = userInfoChangeFragment;
        }else if(index == 2){

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }


    //검색
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {

                
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                System.out.println(text);
                return false;
            }
        });

        return true;
    }

    long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        //뒤로 버튼을 한 번 누렀을 때 종료하시겠습니까? 알림
        //2초 안에 두 번을 눌렀을 때 앱 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }//if

    }//onBackPressed()

    public void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }

}//class