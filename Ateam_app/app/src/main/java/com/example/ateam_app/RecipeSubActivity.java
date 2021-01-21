package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ateam_app.recipe_fragment.RecipeSubItem;

public class RecipeSubActivity extends AppCompatActivity {
    Button btnRecipeSub;
   // RecipeDTO dto;
    ImageView img_url_im;
    Intent intent;
    TextView sumry;
    public static RecipeSubItem recipeSubItem = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);
        intent = getIntent();
        img_url_im = findViewById(R.id.img_url_im);
        sumry = findViewById(R.id.sumry);
        //버튼 누르면 뒤로 돌아가기
        btnRecipeSub = findViewById(R.id.btnRecipeSub);
        btnRecipeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//setOnclick

        //img_url.setImageResource(intent.getIntExtra("img_url", 0));
        sumry.setText(intent.getStringExtra("sumry"));
        //recipe_content.setText(intent.getStringExtra("sumry"));
        //recipe_content.setText(intent.getStringExtra("nation_nm"));
        //recipe_content.setText(intent.getStringExtra("sumry"));
        //recipe_content.setText(intent.getStringExtra("sumry"));
        //recipe_content.setText(intent.getStringExtra("sumry"));

    }


}