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

    EditText user_email, user_pw, user_pw_cf, user_nm, user_addr, user_phone_no;
    Button btnJoinConfirm, btnJoinCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_pw_cf = findViewById(R.id.user_pw_cf);
        user_nm = findViewById(R.id.user_nm);
        user_addr = findViewById(R.id.user_addr);
        user_phone_no = findViewById(R.id.user_phone_no);

        btnJoinConfirm = findViewById(R.id.btnJoinConfirm);
        btnJoinCancle = findViewById(R.id.btnJoinCancle);

        btnJoinConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_email.getText().toString();
                String pw = user_pw.getText().toString();
                String pw_cf = user_pw_cf.getText().toString();
                String name = user_nm.getText().toString();
                String addr = user_addr.getText().toString();
                String phone_no = user_phone_no.getText().toString();

                if(email == null){  //회원가입 정보 입력을 확인함
                    loginErrMessage("이메일을 입력하셔야합니다!");
                }else if(pw == null){
                    loginErrMessage("비밀번호를 입력하셔야합니다!");
                }else if(pw_cf == null && !pw_cf.equals(pw)){
                    loginErrMessage("비밀번호가 일치하지 않습니다!");
                }else if(name == null){
                    loginErrMessage("이름을 입력하셔야합니다!");
                }else if(addr == null){
                    loginErrMessage("주소를 입력하셔야합니다!");
                }else if(phone_no == null){
                    loginErrMessage("전화번호를 입력하셔야합니다!");
                }else{  // 모든 조건을 충족하였으니 에러 없음 회원가입 수행
                    //정말 회원가입 하시겠습니까? 예 누르면
                    //JoinInsert 실행
                }
            }//onClick()
        });//btnJoinConfirm.setOnClickListener()
    }//onCreate()

    private void loginErrMessage(String str){
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
    }//loginErrMessage()


}