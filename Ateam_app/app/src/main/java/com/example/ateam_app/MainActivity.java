package com.example.ateam_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.ateam_app.common.SaveSharedPreference;
import com.example.ateam_app.firebase.AteamWorker;
import com.example.ateam_app.board_package.fragment.BoardFragment;
import com.example.ateam_app.recipe_fragment.RecipeFragment;
import com.example.ateam_app.user_pakage.LoginActivity;
import com.example.ateam_app.user_pakage.UserInfoChangeActivity;
import com.example.ateam_app.user_pakage.atask.UserDelete;
import com.example.ateam_app.user_pakage.dto.UserDTO;
import com.example.ateam_app.user_pakage.fragment.UserMnageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.example.ateam_app.irdnt_list_package.fragment.IrdntListFragment;

import static com.example.ateam_app.user_pakage.LoginActivity.loginDTO;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "main:MainActivity";
    Toolbar toolbar;

    static final int USERINFO_CODE = 1004;

    String deleteState; //회원 탈퇴 유효 여부
    MainFragment mainFragment;  //메인 프래그먼트
    IrdntListFragment irdntListFragment;    //냉장고 내부 목록 프래그먼트
    RecipeFragment recipeFragment;  //레시피 프래그먼트
    BoardFragment boardFragment;    //관리팁 프래그먼트(게시판으로 변경 예정)
    UserMnageFragment userMnageFragment;    //관리자 프래그먼트
    LoginActivity loginActivity;    //로그인 엑티비티

    Bundle bundle;

    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        String tokenID = FirebaseInstanceId.getInstance().getToken();
        super.onResume();
        loginDTO = SaveSharedPreference.getUserData(getSharedPreferences("userData", Activity.MODE_PRIVATE));
        loginActivity = new LoginActivity();
        SaveSharedPreference.setUserData
                (getSharedPreferences("userData", Activity.MODE_PRIVATE),
                        loginActivity.nomalLogin(loginDTO.getUser_email(), loginDTO.getUser_pw(), tokenID));
        loginDTO = SaveSharedPreference.getUserData(getSharedPreferences("userData", Activity.MODE_PRIVATE));

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView header_user_pro_img = headerView.findViewById(R.id.user_pro_img);
        TextView header_user_nm = headerView.findViewById(R.id.user_nm);
        TextView header_user_email = headerView.findViewById(R.id.user_email);
        //좌측 메뉴 배경색 변경
        headerView.setBackgroundColor(Color.BLACK);
        //프로필 이미지 띄우기
        Glide.with(this).load(loginDTO.getUser_pro_img()).error(R.drawable.thumb__ser).into(header_user_pro_img);
        header_user_nm.setText(loginDTO.getUser_nm() + " 님 반갑습니다!");
        header_user_email.setText(loginDTO.getUser_email());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();//
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //alarmCall();    //알람호출

        //측면 메뉴 호출 (Navigation Drawer)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle
                = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navi_drawer_open, R.string.navi_drawer_close);
        drawer.addDrawerListener(toggle);
        //햄버거 버튼 색상변경
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        }else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        }
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //소셜 로그인 유저는 회원정보 수정 불가하게 막음
        if(!loginDTO.getUser_type().equals("nomal")){
            navigationView.getMenu().findItem(R.id.nav_userInfoChange).setVisible(false);
        }

        //관리자 버튼 활성화
        if(loginDTO.getUser_grade().equals("2")){
            navigationView.getMenu().findItem(R.id.admin_item).setVisible(true);
        }

        bundle = new Bundle();
        bundle.putLong("user_id", loginDTO.getUser_id());

        mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, mainFragment)
                .commit();

        irdntListFragment = new IrdntListFragment();
        recipeFragment = new RecipeFragment();
        boardFragment = new BoardFragment();
        userMnageFragment = new UserMnageFragment();

        //하단 메뉴 (Bottom Navigation View)
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tabMain:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,R.anim.exit_to_left)
                                .replace(R.id.main_frame, mainFragment)
                                .commit();
                        return true;
                    case R.id.tabIrdntList:
                        bundle = new Bundle();
                        bundle.putLong("user_id", loginDTO.getUser_id());
                        irdntListFragment.setArguments(bundle);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,R.anim.exit_to_left)
                                .replace(R.id.main_frame, irdntListFragment)
                                .commit();
                        return true;
                    case R.id.tabRecipe:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,R.anim.exit_to_left)
                                .replace(R.id.main_frame, recipeFragment)
                                .commit();
                        return true;
                    case R.id.board:
                        bundle = new Bundle();
                        bundle.putSerializable("vo", loginDTO);
                        boardFragment.setArguments(bundle);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,R.anim.exit_to_right)
                                .replace(R.id.main_frame, boardFragment)
                                .commit();
                        return true;

                }//switch-case
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .commit();
                return true;
            }//onNavigationItemSelected()
        });//bottomNavigationView.setOnNavigationItemSelectedListener()

        //배터리 최적화 거부(이거 있어야 알람 원활하게 작동된다고 들었음)
        PowerManager pm= (PowerManager) getSystemService(Context.POWER_SERVICE);
        String packageName= getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm.isIgnoringBatteryOptimizations(packageName) ){

            } else {    // 메모리 최적화가 되어 있다면, 풀기 위해 설정 화면 띄움.
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent,0);
            }
        }
    }//onCreate()

    private void alarmCall() {  //사용하지 않게된 메소드
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest work = new PeriodicWorkRequest
                .Builder(AteamWorker.class,15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MAX_BACKOFF_MILLIS,
                        TimeUnit.DAYS)
                .build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "requstMessage",
                ExistingPeriodicWorkPolicy.KEEP,
                work);
    }

    //측면 메뉴(Navigation Drawer) 설정
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //측면메뉴의 회원정보수정 눌렀을때 수정페이지로 이동
        if (id == R.id.nav_userInfoChange) {
            Intent intent = new Intent(getApplicationContext(), UserInfoChangeActivity.class);
            intent.putExtra("loginDTO", loginDTO);
            startActivityForResult(intent, USERINFO_CODE);
        } else if (id == R.id.nav_logout) { //로그아웃
            logoutMessage();
        } else if (id == R.id.nav_withdrawal){  //회원탈퇴
            withdrawalMessage();
        } else if (id == R.id.nav_admin) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, userMnageFragment).commit();
            bottomNavigationView.setVisibility(View.GONE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }//onNavigationItemSelected()
    
/****************************************탈퇴, 로그아웃 블록 시작**************************************************/
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
        }//if

        AlertDialog dialog = builder.create();
        dialog.show();
    }//withdrawalMessage()

    //회원 로그아웃
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
                        SaveSharedPreference.clearUserData(getSharedPreferences("userData", Activity.MODE_PRIVATE));
                        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                        loginDTO = null;
                        startActivity(logout);
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
    }//logoutMessage()
/****************************************탈퇴, 로그아웃 블록 끝******************************************/
    
    //검색
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("검색어를 입력하세요.");
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setBackgroundColor(Color.GRAY);

        //검색창 활성화 및 축소
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                intent.putExtra("searchText", searchText.trim());
                intent.putExtra("user_id", loginDTO.getUser_id());
                startActivity(intent);

                return true;
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

//    @Override
//    public void onBackPressed() {
//        //super.onBackPressed();
//        if(backmode == 1){  //1이면 그냥 뒤로가기 모드
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, mainFragment).commit();
//            bottomNavigationView.setVisibility(View.VISIBLE);
//            backmode = 0;
//        } else if(backmode == 2){
//            new CommonMethod().dialogMethod(this, "취소 안내", "작업을 취소 하시겠습니까?",
//                    "예",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
//                            backmode = 0;
//                        }
//                    }, "아니오",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//        } else{
//            //뒤로 버튼을 한 번 누렀을 때 종료하시겠습니까? 알림
//            //2초 안에 두 번을 눌렀을 때 앱 종료
//            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
//                backKeyPressedTime = System.currentTimeMillis();
//                Toast.makeText(this, "뒤로 버튼을 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
//            } else {
//                ActivityCompat.finishAffinity(this);
//                System.exit(0);
//            }//if
//        }
//    }//onBackPressed()

    //프래그먼트에서 프래그먼트로 이동시키는 메소드
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,R.anim.exit_to_left)
                .replace(R.id.main_frame, fragment).commit();
    }

    //프래그먼트에서 프래그먼트로 이동시 데이터도 가져가는 메소드
    public void replaceFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        replaceFragment(fragment);
    }
}//class