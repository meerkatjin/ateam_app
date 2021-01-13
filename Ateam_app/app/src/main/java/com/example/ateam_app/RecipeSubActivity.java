package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ateam_app.recipi_fragment.RecipeDTO;

public class RecipeSubActivity extends AppCompatActivity {
    Button btnRecipeSub;
    RecipeDTO dto;
    ImageView img_url;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);
        intent = getIntent();
        img_url = findViewById(R.id.img_url);

        //버튼 누르면 뒤로 돌아가기
        btnRecipeSub = findViewById(R.id.btnRecipeSub);
        btnRecipeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//setOnclick

        img_url.setImageResource(intent.getIntExtra("img_url", 0));


    }


}