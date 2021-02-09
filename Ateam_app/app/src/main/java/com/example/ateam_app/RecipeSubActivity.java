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
import com.example.ateam_app.recipe_fragment.RecipeSubIrdnt;
import com.example.ateam_app.recipe_fragment.RecipeSubIrdntAdapter;
import com.example.ateam_app.recipe_fragment.RecipeSubIrdntAtask;
import com.example.ateam_app.recipe_fragment.RecipeSubItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class RecipeSubActivity extends AppCompatActivity {
    FloatingActionButton btnRecipeSub;
    RecipeSubAtask recipeSubAtask;
    RecipeSubIrdntAtask recipeSubIrdntAtask;
    RecipeSubAdapter adapter;
    RecipeSubIrdntAdapter recipeSubIrdntAdapter;
    ArrayList<RecipeSubItem> items;
    ArrayList<RecipeSubIrdnt> irdntItems;
    RecyclerView recyclerView, recipe_irdnt_recyclerview;
    Intent intent;
    RecyclerView.LayoutManager mLayoutManager, layoutManager;
    TextView recipe_nm_ko, nation_nm, cooking_time, calorie, level_nm, qnt, cooking_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);
        items = new ArrayList<>();
        irdntItems = new ArrayList<>();
        adapter = new RecipeSubAdapter(this, items);
        recipeSubIrdntAdapter = new RecipeSubIrdntAdapter(irdntItems, this);

        intent = this.getIntent();
        recyclerView = findViewById(R.id.recipe_item_recyclerview);
        recipe_irdnt_recyclerview = findViewById(R.id.recipe_irdnt_recyclerview);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recipe_nm_ko = findViewById(R.id.recipe_nm_ko);
        nation_nm = findViewById(R.id.nation_nm);
        cooking_time = findViewById(R.id.cooking_time);
        calorie = findViewById(R.id.calorie);
        level_nm = findViewById(R.id.level_nm);
        qnt = findViewById(R.id.qnt);
        cooking_no = findViewById(R.id.cooking_no);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recipe_irdnt_recyclerview.setLayoutManager(layoutManager);
        recipe_irdnt_recyclerview.setAdapter(recipeSubIrdntAdapter);

        recipe_nm_ko.setText(intent.getStringExtra("recipe_nm_ko"));
        nation_nm.setText(intent.getStringExtra("nation_nm"));
        cooking_time.setText(intent.getStringExtra("cooking_time"));
        calorie.setText(intent.getStringExtra("calorie"));
        if (intent.getStringExtra("level_nm").equals("초보환영")) {
            level_nm.setText("★☆☆ (EASY)");
        } else if (intent.getStringExtra("level_nm").equals("보통")) {
            level_nm.setText("★★☆ (NORMAL)");
        } else {
            level_nm.setText("★★★ (HARD)");
        }
        qnt.setText(intent.getStringExtra("qnt"));

        //레시피 아이디 값 넘겨주자

        int recipe_id = intent.getIntExtra("recipe_id" , 1);
        if(isNetworkConnected(this) == true) {
            recipeSubIrdntAtask = new RecipeSubIrdntAtask(irdntItems, recipe_id, recipeSubIrdntAdapter);
            recipeSubIrdntAtask.execute();
        }
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