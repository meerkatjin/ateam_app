package com.example.ateam_app.user_pakage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.atask.JoinInsert;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class JoinActivity extends AppCompatActivity {

    String state;

    EditText user_email, user_pw, user_pw_cf, user_nm;
    Button btnJoinConfirm, btnJoinCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_pw_cf = findViewById(R.id.user_pw_cf);
        user_nm = findViewById(R.id.user_nm);
        btnJoinConfirm = findViewById(R.id.btnJoinConfirm);
        btnJoinCancle = findViewById(R.id.btnJoinCancle);

        btnJoinConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinActivate();
            }//onClick()
        });//btnJoinConfirm.setOnClickListener()

        btnJoinCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreate()

    //회원가입 실행 메소드
    private void joinActivate(){
        String email = user_email.getText().toString().trim();
        String pw = user_pw.getText().toString().trim();
        String pw_cf = user_pw_cf.getText().toString().trim();
        String name = user_nm.getText().toString().trim();

        boolean check = false;  //유효성검사 결과를 리턴해줌

        UserDTO dto = null;

        if(email.equals("")){  //회원가입 정보 입력을 확인함
            joinErrMessage("이메일을 입력하셔야합니다!");
            user_email.requestFocus();
        }else if(pw.equals("")){
            joinErrMessage("비밀번호를 입력하셔야합니다!");
            user_pw.requestFocus();
        }else if(pw_cf.equals("") || !pw_cf.equals(pw)){    //일치하지 않으면 비밀번호 처음부터 작성하게만듬
            joinErrMessage("비밀번호가 일치하지 않습니다!");
            user_pw_cf.setText("");
            user_pw.setText("");
            user_pw.requestFocus();
        }else if(name.equals("")){
            joinErrMessage("이름을 입력하셔야합니다!");
            user_nm.requestFocus();
        } else{  // 모든 조건을 충족하였으니 에러 없음 회원가입 수행
            dto = new UserDTO(email, pw, name);
            check = joinFormatCheck(dto);

            //정말 회원가입 하시겠습니까? 예 누르면 JoinInsert 실행
            if(check) joinConfirmMessage(dto);
        }
    }//joinActivate()

    //유효성 검사를 실행하여 통과 여부를 반환함
    private boolean joinFormatCheck(UserDTO dto) {
        String regexEmail = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        //비밀번호 5-10자리 대문자 소문자 숫자 포함
        String regexPw = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{5,10}$";
        String regexNm = "^[가-힣\\w]{2,10}$";

        int trueCheck = 0;

        if(dto.getUser_email().trim().matches(regexEmail)) {
            trueCheck++;
        }else{
            joinErrMessage("이메일 입력 형식이 잘못되었습니다!");
            user_email.setText("");
            user_email.requestFocus();
            return false;
        }

        if(dto.getUser_pw().trim().matches(regexPw)){
            trueCheck++;
        }else{
            joinErrMessage("비밀번호 입력 형식이 잘못되었습니다!\n" +
                    "비밀번호는 5-10자리이며 대문자, 소문자, 숫자가\n" +
                    "모두 포함 되어야 합니다! ");
            user_pw.setText("");
            user_pw_cf.setText("");
            user_pw.requestFocus();
            user_pw_cf.requestFocus();
            return false;
        }

        if(dto.getUser_nm().trim().matches(regexNm)){
            trueCheck++;
        }else{
            joinErrMessage("이름 형식이 잘못되었습니다!");
            user_nm.setText("");
            user_nm.requestFocus();
            return false;
        }

        if(trueCheck == 3) return true;

        return false;
    }//joinFormatCheck()

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
                dialog.dismiss();
            }
        });//builder.setPositiveButton()

        AlertDialog dialog = builder.create();
        dialog.show();
    }//loginErrMessage()

    //회원가입 입력이 정상적일때 한번더 가입여부를 물어보고
    //가입 진행을 하면 db에 회원 정보를 올리는 코드
    private void joinConfirmMessage(UserDTO dto){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("이대로 진행 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isNetworkConnected(JoinActivity.this)) {
                    JoinInsert joinInsert = new JoinInsert(dto);
                    try {
                        state = joinInsert.execute().get().trim();
                        Log.d("main:joinact0 : ", state);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("1")) {
                        Toast.makeText(JoinActivity.this, "회원가입을 환영합니다 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입성공 !!!");
                        finish();
                    } else {
                        Toast.makeText(JoinActivity.this, "화원가입 실패하였습니다 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:joinact", "삽입실패 !!!");
                        finish();
                    }
                }
            }
        });//builder.setPositiveButton()

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });//builder.setNegativeButton()

        AlertDialog dialog = builder.create();
        dialog.show();
    }//joinConfirmMessage()

}