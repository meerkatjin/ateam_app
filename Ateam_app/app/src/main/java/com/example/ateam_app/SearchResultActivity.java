package com.example.ateam_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ateam_app.irdnt_list_package.atask.IrdntLifeEndListATask;
import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListView;
import com.example.ateam_app.irdnt_list_package.atask.IrdntNewContentListATask;
import com.example.ateam_app.recipe_fragment.OnRecipeItemClickListener;
import com.example.ateam_app.recipe_fragment.RecipeAdapter;
import com.example.ateam_app.recipe_fragment.RecipeAtask;
import com.example.ateam_app.recipe_fragment.RecipeItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class SearchResultActivity extends AppCompatActivity {
    TabLayout searchTab;
    RecyclerView searchRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    IrdntListView irdntListView;
    ArrayList<IrdntListDTO> irdntItems;
    IrdntListAdapter irdntListAdapter;
    IrdntLifeEndListATask irdntLifeEndListATask;
    IrdntNewContentListATask irdntNewContentListATask;

    RecipeAtask recipeAtask;
    ArrayList<RecipeItem> recipeItems;
    RecipeAdapter recipeAdapter;

    Intent intent;

    String searchText;
    Long user_id;

    ArrayList<Long> irdnt_ids, new_ids;
    boolean checkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchTab = findViewById(R.id.searchTab);
        searchTab.addTab(searchTab.newTab().setText("내 재료"));
        searchTab.addTab(searchTab.newTab().setText("레시피"));
        searchTab.addTab(searchTab.newTab().setText("관리 팁"));

        intent = getIntent();
        searchText = intent.getStringExtra("searchText");
        user_id = intent.getLongExtra("user_id", 0);

        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);

        irdntItems = new ArrayList<>();

        //유통기한 지난 재료의 아이디 가져오기
        if(isNetworkConnected(SearchResultActivity.this) == true) {
            irdntLifeEndListATask = new IrdntLifeEndListATask(user_id);
            irdntNewContentListATask = new IrdntNewContentListATask(user_id);
            try {
                irdnt_ids = irdntLifeEndListATask.execute().get();
                new_ids = irdntNewContentListATask.execute().get();
            } catch (ExecutionException e) {
                e.getMessage();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }

        irdntListAdapter = new IrdntListAdapter(getApplicationContext(), irdntItems,irdnt_ids, new_ids, checkMode);
        searchRecyclerView.setAdapter(irdntListAdapter);

        if (isNetworkConnected(this) == true) {
            irdntListView = new IrdntListView(searchText, irdntItems, irdntListAdapter, progressDialog, user_id);
            irdntListView.execute();
        }

        searchTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0) {
                    irdntItems = new ArrayList<>();
                    irdntListAdapter = new IrdntListAdapter(getApplicationContext(), irdntItems,irdnt_ids, new_ids, checkMode);
                    searchRecyclerView.setAdapter(irdntListAdapter);

                    if (isNetworkConnected(getApplicationContext()) == true) {
                        irdntListView = new IrdntListView(searchText, irdntItems, irdntListAdapter, progressDialog, user_id);
                        irdntListView.execute();
                    }
                } else if (position == 1) {
                    recipeItems = new ArrayList<>();
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipeItems);
                    searchRecyclerView.setAdapter(recipeAdapter);

                    if (isNetworkConnected(getApplicationContext()) == true) {
                        recipeAtask = new RecipeAtask(searchText, recipeItems, recipeAdapter, progressDialog);
                        recipeAtask.execute();
                    }

                    recipeAdapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
                        @Override
                        public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
                            RecipeItem item = recipeAdapter.getItem(position);
                            Intent intent = new Intent(getApplicationContext(), RecipeSubActivity.class);
                            intent.putExtra("recipe_id", item.getRecipe_id());
                            intent.putExtra("recipe_nm_ko", item.getRecipe_nm_ko());
                            intent.putExtra("sumry", item.getSumry());
                            intent.putExtra("nation_nm", item.getNation_nm());
                            intent.putExtra("ty_nm", item.getTy_nm());
                            intent.putExtra("cooking_time", item.getCooking_time());
                            intent.putExtra("calorie", item.getCalorie());
                            intent.putExtra("qnt", item.getQnt());
                            intent.putExtra("level_nm", item.getLevel_nm());
                            intent.putExtra("irdnt_code", item.getIrdnt_code());
                            //intent.putExtra("img_url", item.getRecipe_id());

                            startActivity(intent);
                        }
                    });
                } else if (position == 2) {

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}