package com.example.ateam_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ateam_app.user_pakage.atask.UserInfoUpdate;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class UserInfoChangeActivity extends AppCompatActivity {

    EditText user_email, user_pw, user_nm, user_addr, user_pro_img, user_phone_no;
    Button btnInfoChange, btnInfoChangeCancel;

    String email, pw, name, addr, img, phone;
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        btnInfoChange = findViewById(R.id.btnInfoChange);
        btnInfoChangeCancel = findViewById(R.id.btnInfoChangeCancel);

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_nm = findViewById(R.id.user_nm);
        user_addr = findViewById(R.id.user_addr);
        user_phone_no = findViewById(R.id.user_phone_no);

        //보내온 값 파싱
        mainIntent = getIntent();
        UserDTO loginDTO = (UserDTO) mainIntent.getSerializableExtra("loginDTO");
        Log.d("세라수정", "onCreate: email" + loginDTO.getUser_email());

        email = loginDTO.getUser_email();
        pw = loginDTO.getUser_pw();
        name = loginDTO.getUser_nm();
        addr = loginDTO.getUser_addr();
        phone = loginDTO.getUser_phone_no();

        user_email.setText(email);
        user_pw.setText(pw);
        user_nm.setText(name);
        user_addr.setText(addr);
        user_phone_no.setText(phone);

        //수정버튼 누름
        btnInfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoChangeDialog();
            }
        });

        //수정취소버튼 누름
        btnInfoChangeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoChangeCancelDialog();
            }
        });
    }

    //수정버튼 눌렀을떄 정말 수정할것인지 대화상자팝업
    private void userInfoChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 수정하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Toast.makeText(UserInfoChangeActivity.this, "수정되었습니다!", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //수정취소  버튼 눌렀을때 정말로 취소할것인지 대화상자 팝업
    private void userInfoChangeCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 취소하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}