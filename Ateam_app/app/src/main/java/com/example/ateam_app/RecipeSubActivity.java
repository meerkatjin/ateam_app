package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeSubActivity extends AppCompatActivity {
    Button btnRecipeSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);

        btnRecipeSub = findViewById(R.id.btnRecipeSub);
        btnRecipeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}