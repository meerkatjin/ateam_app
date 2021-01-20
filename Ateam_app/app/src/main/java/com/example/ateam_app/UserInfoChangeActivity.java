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

        //이메일 수정못하게
        user_email.setEnabled(false);

        //보내온 값 파싱
        mainIntent = getIntent();
        UserDTO loginDTO = (UserDTO) mainIntent.getSerializableExtra("loginDTO");
        //Log.d("세라수정", "onCreate: email" + loginDTO.getUser_email());

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
                email = user_email.getText().toString();
                pw = user_pw.getText().toString();
                name = user_nm.getText().toString();
                addr = user_addr.getText().toString();
                phone = user_phone_no.getText().toString();

                UserInfoUpdate userInfoUpdate = new UserInfoUpdate(email, pw, name, addr, img, phone);
                userInfoUpdate.execute();

                Toast.makeText(UserInfoChangeActivity.this, "수정되었습니다!", Toast.LENGTH_SHORT).show();

                Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
                showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   // 이 엑티비티 플래그를 사용하여 엑티비티를 호출하게 되면 새로운 태스크를 생성하여 그 태스크안에 엑티비티를 추가하게 됩니다. 단, 기존에 존재하는 태스크들중에 생성하려는 엑티비티와 동일한 affinity(관계, 유사)를 가지고 있는 태스크가 있다면 그곳으로 새 엑티비티가 들어가게됩니다.
                        Intent.FLAG_ACTIVITY_SINGLE_TOP | // 엑티비티를 호출할 경우 호출된 엑티비티가 현재 태스크의 최상단에 존재하고 있었다면 새로운 인스턴스를 생성하지 않습니다. 예를 들어 ABC가 엑티비티 스택에 존재하는 상태에서 C를 호출하였다면 여전히 ABC가 존재하게 됩니다.
                        Intent.FLAG_ACTIVITY_CLEAR_TOP); // 만약에 엑티비티스택에 호출하려는 엑티비티의 인스턴스가 이미 존재하고 있을 경우에 새로운 인스턴스를 생성하는 것 대신에 존재하고 있는 엑티비티를 포그라운드로 가져옵니다. 그리고 엑티비티스택의 최상단 엑티비티부터 포그라운드로 가져올 엑티비티까지의 모든 엑티비티를 삭제합니다.
                startActivity(showIntent);

                finish();
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