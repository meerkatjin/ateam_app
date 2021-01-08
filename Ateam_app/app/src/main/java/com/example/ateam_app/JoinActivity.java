package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    EditText user_email, user_pw, user_pw_cf, user_name, user_addr;
    Button btnJoinConfirm, btnJoinCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_pw);
        user_pw_cf = findViewById(R.id.user_pw_cf);
        user_name = findViewById(R.id.user_name);
        user_addr = findViewById(R.id.user_addr);

        btnJoinConfirm = findViewById(R.id.btnJoinConfirm);
        btnJoinCancle = findViewById(R.id.btnJoinCancle);

        btnJoinConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_email.getText().toString();
                String pw = user_pw.getText().toString();
                String pw_cf = user_pw_cf.getText().toString();
                String name = user_name.getText().toString();
                String addr = user_addr.getText().toString();

                if(id == null){

                }else if(pw == null){

                }else if(pw_cf == null && !pw_cf.equals(pw)){

                }else if(name == null){

                }else if(addr == null){

                }
            }
        });
    }
}