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
import android.widget.EditText;
import android.widget.Toast;


import com.example.ateam_app.manage_tip_package.ManageTipFragment;
import com.example.ateam_app.recipe_fragment.RecipeFragment;
import com.example.ateam_app.user_pakage.LoginActivity;
import com.example.ateam_app.user_pakage.atask.UserDelete;
import com.example.ateam_app.user_pakage.dto.UserDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.concurrent.ExecutionException;

import com.example.ateam_app.irdnt_list_package.IrdntListFragment;

import static com.example.ateam_app.user_pakage.LoginActivity.loginDTO;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "main:MainActivity";
    Toolbar toolbar;

    static final int USERINFO_CODE = 1004;
    String deleteState;
    MainFragment mainFragment;
    IrdntListFragment irdntListFragment;
    CamFragment camFragment;
    RecipeFragment recipeFragment;
    ManageTipFragment manageTipFragment;
    Intent loginIntent; //로그인 엑티비티에서 로그인한 회원의 데이터 받아옴(비밀번호 빼고)

    BottomNavigationView bottomNavigationView;
    int bottomNavi = 1; //하단 네비게이션 바 선택점 저장

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
        loginDTO = (UserDTO) loginIntent.getSerializableExtra("loginDTO");
        Log.d(TAG, "getLogin: userid : " + loginDTO.getUser_id()
                + ", user_email : " + loginDTO.getUser_email()
                + ", user_nm : " + loginDTO.getUser_nm()
                + ", user_addr : " + loginDTO.getUser_addr()
                + ", user_pro_img : " + loginDTO.getUser_pro_img()
                + ", user_phone_no : " + loginDTO.getUser_phone_no()
                + ", user_grade : " + loginDTO.getUser_grade()
                + ", user_type : " + loginDTO.getUser_type());

        //하단 메뉴 (Bottom Navigation View)
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tabMain:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFragment).commit();
                        bottomNavi = 1;
                        return true;
                    case R.id.tabIrdntList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, irdntListFragment).commit();
                        bottomNavi = 2;
                        return true;
                    case R.id.tabCam:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, camFragment).commit();
                        bottomNavi = 3;
                        return true;
                    case R.id.tabRecipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, recipeFragment).commit();
                        bottomNavi = 4;
                        return true;
                    case R.id.tabManageTip:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, manageTipFragment).commit();
                        bottomNavi = 5;
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
            intent.putExtra("loginDTO", loginDTO);
            startActivityForResult(intent, USERINFO_CODE);

        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
            logoutMessage();

        } else if (id == R.id.nav_withdrawal){
            Toast.makeText(this, "회원탈퇴", Toast.LENGTH_SHORT).show();
            withdrawalMessage();
        } else if (id == R.id.nav_admin) {
            Toast.makeText(this, "관리자 메뉴", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }//onNavigationItemSelected()

    //회원탈퇴
    private void withdrawalMessage() {
        EditText withdrawalText = new EditText(this);
        withdrawalText.setInputType(0x00000081);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("회원탈퇴");

        //일반 회원일때
        if(loginDTO.getUser_type().equals("nomal")) {
            builder.setMessage("비밀번호를 입력해주세요!");
            builder.setView(withdrawalText);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long id = loginDTO.getUser_id();
                    String pw = withdrawalText.getText().toString();
                    String type = loginDTO.getUser_type();

                    UserDTO deleteDto = new UserDTO();
                    deleteDto.setUser_id(id);
                    deleteDto.setUser_pw(pw);
                    deleteDto.setUser_type(type);
                    UserDelete userDelete = new UserDelete(deleteDto);
                    try{
                        deleteState = userDelete.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(deleteState.equals("1")){
                        Toast.makeText(MainActivity.this, "회원탈퇴 하였습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "화원탈퇴 실패하였습니다 !!!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {    //소셜 회원일때
            builder.setMessage("정말 탈퇴하시겠습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("탈퇴", "아이디 : " + loginDTO.getUser_id() + ", 타입 : " + loginDTO.getUser_type());
                    UserDelete userDelete = new UserDelete(loginDTO);
                    try{
                        deleteState = userDelete.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            int result = errorResult.getErrorCode();

                            if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onNotSignedUp() {
                            Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onSuccess(Long result) {
                            if(deleteState.equals("1")){
                                Toast.makeText(MainActivity.this, "회원탈퇴 하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "화원탈퇴 실패하였습니다 !!!", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        }
                    });
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }//withdrawalMessage()

    private void logoutMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 로그아웃하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //로그아웃시 가져온 회원 데이터를 null로 초기화하여 로그아웃
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent logout = new Intent();
                        loginDTO = null;
                        logout.putExtra("logout", loginDTO);
                        setResult(RESULT_OK, logout);
                        finish();
                    }
                });
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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                if (bottomNavi == 1) {
                    //Toast.makeText(MainActivity.this, "통합 검색 : " + searchText, Toast.LENGTH_SHORT).show();


                } else if (bottomNavi == 2) {
                    //Toast.makeText(MainActivity.this, "재료 검색 : " + searchText, Toast.LENGTH_SHORT).show();

                } else if (bottomNavi == 3) {
                    Toast.makeText(MainActivity.this, "여기서는 검색을 지원하지 않습니다.", Toast.LENGTH_SHORT).show();

                } else if (bottomNavi == 4) {
                    //Toast.makeText(MainActivity.this, "레시피 검색 : " + searchText, Toast.LENGTH_SHORT).show();

                } else if (bottomNavi == 5) {
                    //Toast.makeText(MainActivity.this, "팁 검색 : " + searchText, Toast.LENGTH_SHORT).show();

                }

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