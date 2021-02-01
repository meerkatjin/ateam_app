package com.example.ateam_app.user_pakage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.common.SaveSharedPreference;
import com.example.ateam_app.user_pakage.atask.KakaoLoginSelect;
import com.example.ateam_app.user_pakage.atask.LoginSelect;
import com.example.ateam_app.user_pakage.dto.UserDTO;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    // 로그인이 성공하면 static 로그인DTO 변수에 담아서
    // 어느곳에서나 접근할 수 있게 한다
    public static UserDTO loginDTO = null;
    static final int MAIN_CODE = 1001;   //메인 엑티비티로 로그인값을 넘기기위한 요청코드

    EditText user_email, user_pw;
    Button btnLogin, btnJoin;
    CheckBox autoLoginCheck;

    private SessionCallback sessionCallback;

    @Override
    protected void onStart() {
        super.onStart();

        autoLoginCheck = findViewById(R.id.autoLoginCheck);

        boolean loginChack = SaveSharedPreference.getAutoLogin
                                (getSharedPreferences("autoLogin", Activity.MODE_PRIVATE));

        if(loginChack){
            autoLoginCheck.setChecked(true);
            loginDTO = SaveSharedPreference.getUserData
                    (getSharedPreferences("userData",Activity.MODE_PRIVATE));
            if(loginDTO.getUser_id() != 0){
                if(CommonMethod.isNetworkConnected(LoginActivity.this)){
                    loginEvent();
                }else{
                    Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            autoLoginCheck.setChecked(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        getHashKey();
        checkDangerousPermissions();

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);
        autoLoginCheck = findViewById(R.id.autoLoginCheck);

        //자동로그인 체크 이벤트
        autoLoginCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId() == R.id.autoLoginCheck){
                    if(isChecked){
                        SaveSharedPreference.setAutoLogin
                                (getSharedPreferences("autoLogin", Activity.MODE_PRIVATE), true);
                    }else{
                        SaveSharedPreference.setAutoLogin
                                (getSharedPreferences("autoLogin", Activity.MODE_PRIVATE), false);
                    }
                }
            }
        });

        //로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(user_email.getText().toString().length() != 0 &&
                        user_pw.getText().toString().length() !=0){
                    String email = user_email.getText().toString();
                    String pw = user_pw.getText().toString();

                   if(CommonMethod.isNetworkConnected(LoginActivity.this)){
                       LoginSelect loginSelect = new LoginSelect(email, pw);
                       try {
                           loginSelect.execute().get();
                       } catch (ExecutionException e) {
                           e.getMessage();
                       } catch (InterruptedException e) {
                           e.getMessage();
                       }

                       if(loginDTO != null){
                           loginEvent();
                       }else {
                           Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                           user_email.setText(""); user_pw.setText("");
                           user_email.requestFocus();
                       }
                   }
                }else{
                    Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);*/
            }
        });

        //회원가입 버튼
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    //로그아웃되면 메인엑티비티에서 회원정보를 null로 초기화해서 로그인 엑티비티로 돌아온다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == MAIN_CODE){
                loginDTO = (UserDTO) data.getSerializableExtra("logout");
            }
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                //카카오 로그인성공시
                @Override
                public void onSuccess(MeV2Response result) {
                    Log.d("kakaoTest:", "email: " + result.getKakaoAccount().getEmail() + ", name: " + result.getNickname() + ", id: " + result.getId() + ", image : " + result.getProfileImagePath());
                    UserDTO KakaoLoginDTO = new UserDTO();
                    KakaoLoginDTO.setUser_id(result.getId());
                    KakaoLoginDTO.setUser_email(result.getKakaoAccount().getEmail());
                    KakaoLoginDTO.setUser_nm(result.getNickname());
                    if(result.getProfileImagePath() != null){
                        KakaoLoginDTO.setUser_pro_img(result.getProfileImagePath());
                    }
                    KakaoLoginDTO.setUser_type("kakao");
                    if(CommonMethod.isNetworkConnected(getApplicationContext())) {
                        KakaoLoginSelect kakaoLoginSelect = new KakaoLoginSelect(KakaoLoginDTO);
                        try {
                            kakaoLoginSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.getMessage();
                        } catch (InterruptedException e) {
                            e.getMessage();
                        }

                        if (loginDTO != null) {
                            loginEvent();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패 !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //위험권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //헤시키 가져오기 로그켓에 KeyHash 입력
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

    private void loginEvent(){
        Toast.makeText(LoginActivity.this, "로그인 되었습니다 !!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("loginDTO", loginDTO);
        startActivityForResult(intent, MAIN_CODE);
        user_email.setText(""); user_pw.setText("");
        finish();
    }
}