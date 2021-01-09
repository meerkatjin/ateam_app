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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ateam_app.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    String state;

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
                    joinErrMessage("이메일을 입력하셔야합니다!");
                }else if(pw == null){
                    joinErrMessage("비밀번호를 입력하셔야합니다!");
                }else if(pw_cf == null && !pw_cf.equals(pw)){
                    joinErrMessage("비밀번호가 일치하지 않습니다!");
                }else if(name == null){
                    joinErrMessage("이름을 입력하셔야합니다!");
                }else if(addr == null){
                    joinErrMessage("주소를 입력하셔야합니다!");
                }else if(phone_no == null){
                    joinErrMessage("전화번호를 입력하셔야합니다!");
                }else{  // 모든 조건을 충족하였으니 에러 없음 회원가입 수행
                   joinConfirmMessage(email, pw, name, addr, phone_no);//정말 회원가입 하시겠습니까? 예 누르면 JoinInsert 실행
                }
            }//onClick()
        });//btnJoinConfirm.setOnClickListener()

        btnJoinCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()

    //회원가입 입력이 잘못되었을때 띄우는 다이얼로그 메소드
    private void joinErrMessage(String str){
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

    //회원가입 입력이 정상적일때 한번더 가입여부를 물어보고
    //가입 진행을 하면 db에 회원 정보를 올리는 코드
    private void joinConfirmMessage(String email, String pw, String name, String addr, String phone_no){
        boolean check = false;

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("이대로 진행 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                JoinInsert joinInsert = new JoinInsert(email, pw, name, addr, phone_no);
                try {
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this, "삽입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입성공 !!!");
                    finish();
                }else{
                    Toast.makeText(JoinActivity.this, "삽입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "삽입실패 !!!");
                    finish();
                }
            }
        });//builder.setPositiveButton()

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });//builder.setNegativeButton()
    }

}