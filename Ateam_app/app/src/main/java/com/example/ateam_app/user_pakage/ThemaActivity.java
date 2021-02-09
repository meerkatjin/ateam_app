package com.example.ateam_app.user_pakage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.common.SaveSharedPreference;

public class ThemaActivity extends AppCompatActivity {

    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thema);

        loginActivity.autoLoginBox(loginActivity.autoLoginCheck);
    }
}