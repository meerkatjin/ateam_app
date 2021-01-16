package com.example.ateam_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.ateam_app.manage_tip_package.ManageTipFragment;
import com.example.ateam_app.recipe_fragment.RecipeFragment;
import com.example.ateam_app.user_pakage.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import irdnt_list_package.IrdntListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "main:MainActivity";
    Toolbar toolbar;

    MainFragment mainFragment;
    IrdntListFragment irdntListFragment;
    CamFragment camFragment;
    RecipeFragment recipeFragment;
    ManageTipFragment manageTipFragment;
    Intent loginIntent; //로그인 엑티비티에서 로그인한 회원의 데이터 받아옴(비밀번호 빼고)

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

        //로그인 데이터 받는곳
        loginIntent = getIntent();
        int user_id = loginIntent.getIntExtra("user_id", 0);
        String user_email = loginIntent.getStringExtra("user_email");
        String user_nm = loginIntent.getStringExtra("user_nm");
        String user_addr = loginIntent.getStringExtra("user_addr");
        String user_pro_img = loginIntent.getStringExtra("user_pro_img");
        String user_phone_no = loginIntent.getStringExtra("user_phone_no");
        String user_grade = loginIntent.getStringExtra("user_grade");
        Log.d(TAG, "getLogin: userid : " + user_id
                + ", user_email : " + user_email
                + ", user_nm : " + user_nm
                + ", user_addr : " + user_addr
                + ", user_pro_img : " + user_pro_img
                + ", user_phone_no : " + user_phone_no
                + ", user_grade : " + user_grade);

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

        //측면메뉴의 회원정보수정 눌렀을때 수정페이지로 이동
        if (id == R.id.nav_userInfoChange) {
            Intent intent = new Intent(getApplicationContext(), UserInfoChangeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
            logoutMessage();

        } else if (id == R.id.nav_admin) {
            Toast.makeText(this, "관리자 메뉴", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }//onNavigationItemSelected()

    private void logoutMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 로그아웃하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

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
    }//onCreateOptionsMenu()

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
    }//replaceFragment()

}//class