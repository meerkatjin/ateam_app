package com.example.ateam_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserInfoChangeActivity extends AppCompatActivity {

    Button btnInfoChange, btnInfoChangeCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        btnInfoChange = findViewById(R.id.btnInfoChange);
        btnInfoChangeCancel = findViewById(R.id.btnInfoChangeCancel);

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
}