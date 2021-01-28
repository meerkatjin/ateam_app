package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ateam_app.recipe_fragment.RecipeAtask;
import com.example.ateam_app.recipe_fragment.RecipeItem;
import com.example.ateam_app.recipe_fragment.RecipeSubAdapter;
import com.example.ateam_app.recipe_fragment.RecipeSubAtask;
import com.example.ateam_app.recipe_fragment.RecipeSubItem;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class RecipeSubActivity extends AppCompatActivity {
    Button btnRecipeSub;
    RecipeSubAtask recipeSubAtask;
    RecipeSubAdapter adapter;
    ArrayList<RecipeSubItem> items;
    RecyclerView recyclerView;
    Intent intent;
    RecyclerView.LayoutManager mLayoutManager;
    TextView recipe_nm_ko;
    TextView level_nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);
        items = new ArrayList<>();
        adapter = new RecipeSubAdapter(this, items);
        intent = this.getIntent();
        recyclerView = findViewById(R.id.recipe_item_recyclerview);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recipe_nm_ko = findViewById(R.id.recipe_nm_ko);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recipe_nm_ko.setText(intent.getStringExtra("recipe_nm_ko"));
        //level_nm.setText(intent.getStringExtra("level_nm"));
        //레시피 아이디 값 넘겨주자

        int recipe_id = intent.getIntExtra("recipe_id" , 1);
        if(isNetworkConnected(this) == true) {
                recipeSubAtask = new RecipeSubAtask(items, recipe_id, adapter);
                recipeSubAtask.execute();
            }


        /*else {
            int recipe_id_mf = intent.getIntExtra("recipe_id_mf", 2);
            recipeSubAtask = new RecipeSubAtask(items, recipe_id_mf, adapter);
            recipeSubAtask.execute();
        }
*/

        //버튼 누르면 뒤로 돌아가기


        btnRecipeSub = findViewById(R.id.btnRecipeSub);
        btnRecipeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }


        });//setOnclick

    }

}