package com.example.ateam_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    EditText user_email, user_pw, user_pw_cf, user_name, user_addr;
    Button btnJoinConfirm, btnJoinCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        checkDangerousPermissions();

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_pw_cf = findViewById(R.id.user_pw_cf);
        user_name = findViewById(R.id.user_name);
        user_addr = findViewById(R.id.user_addr);

        btnJoinConfirm = findViewById(R.id.btnJoinConfirm);
        btnJoinCancle = findViewById(R.id.btnJoinCancle);

        btnJoinConfirm.setOnClickListener(new View.OnClickListener() {
            boolean loginCheck = false;  //로그인 실수가 있는지 없는지 확인
            @Override
            public void onClick(View v) {
                String email = user_email.getText().toString();
                String pw = user_pw.getText().toString();
                String pw_cf = user_pw_cf.getText().toString();
                String name = user_name.getText().toString();
                String addr = user_addr.getText().toString();

                if(email == null){  //회원가입 정보 입력을 확인함
                    loginCheck = loginErrMessage("이메일을 입력하셔야합니다!");
                }else if(pw == null){
                    loginCheck = loginErrMessage("비밀번호를 입력하셔야합니다!");
                }else if(pw_cf == null && !pw_cf.equals(pw)){
                    loginCheck = loginErrMessage("비밀번호가 일치하지 않습니다!");
                }else if(name == null){
                    loginCheck = loginErrMessage("이름을 입력하셔야합니다!");
                }else if(addr == null){
                    loginCheck = loginErrMessage("주소를 입력하셔야합니다!");
                }else{  // 모든 조건을 충족하였으니 에러 없음 true
                    loginCheck = true;
                }
            }//onClick()
        });//btnJoinConfirm.setOnClickListener()
    }//onCreate()

    private boolean loginErrMessage(String str){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage(str);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });//builder.setPositiveButton()
        return false;
    }//loginErrMessage()

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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
}