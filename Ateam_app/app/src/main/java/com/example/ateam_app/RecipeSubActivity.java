package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ateam_app.recipe_fragment.RecipeAtask;
import com.example.ateam_app.recipe_fragment.RecipeItem;
import com.example.ateam_app.recipe_fragment.RecipeSubAtask;
import com.example.ateam_app.recipe_fragment.RecipeSubItem;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class RecipeSubActivity extends AppCompatActivity {
    Button btnRecipeSub;
    RecipeSubAtask recipeSubAtask;
    ArrayList<RecipeSubItem> items;
    RecipeSubItem item;
   // RecipeDTO dto;
    ImageView img_url_im;
    Intent intent;
    TextView sumry;
    TextView recipe_id;
    TextView cooking_no;
    TextView cooking_dc;
    ImageView stre_step_image_url;
    TextView step_tip;
    public static RecipeSubItem recipeSubItem = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_sub);
        intent = getIntent();
        items = new ArrayList<>();
        img_url_im = findViewById(R.id.img_url_im);
        sumry = findViewById(R.id.sumry);
        recipe_id = (TextView)findViewById(R.id.recipe_id);
        cooking_no = (TextView)findViewById(R.id.cooking_no);
        cooking_dc = (TextView) findViewById(R.id.cooking_dc);
        stre_step_image_url = (ImageView) findViewById(R.id.stre_step_image_url_im);
        step_tip = (TextView) findViewById(R.id.step_tip);
       // String rrrr = intent.getStringExtra("recipe_id");
        int recipe_id = intent.getIntExtra("recipe_id" , 1);

        //recipe_id.setText(intent.getStringExtra("recipe_id"));
    //    int i_recipe_id = Integer.parseInt( recipe_id.getText().toString() );
        if(isNetworkConnected(this) == true) {
            recipeSubAtask = new RecipeSubAtask(items, recipe_id);
            recipeSubAtask.execute();
            setItem(item);
        }


        //버튼 누르면 뒤로 돌아가기


        btnRecipeSub = findViewById(R.id.btnRecipeSub);
        btnRecipeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }


        });//setOnclick






    }

    public void setItem(RecipeSubItem item) {
        if (item != null) {
            if (item.getCooking_no() != null) {
                cooking_no.setText(item.getCooking_no());
            } else {
                cooking_no.setText("");
            }

            if (item.getCooking_dc() != null) {
                cooking_dc.setText(item.getCooking_dc());
            } else {
                cooking_dc.setText("");
            }

            //if (item.getStre_step_image_url() != null) {
            //Glide.with(this).load(item.getStre_step_image_url()).into(img_url_im);
            if (item.getStep_tip() != null) {
                step_tip.setText(item.getStep_tip());
            } else {
                step_tip.setText("");
            }
        }
    }
}