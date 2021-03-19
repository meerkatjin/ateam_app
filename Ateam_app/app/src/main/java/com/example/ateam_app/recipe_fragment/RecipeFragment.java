package com.example.ateam_app.recipe_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.RecipeSubActivity;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListView;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;


import java.util.ArrayList;


public class RecipeFragment extends Fragment {
    RecipeAtask recipeAtask;
    RecipeAdapter adapter;
    ArrayList<RecipeItem> items;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recipe, container, false);
        Context context = viewGroup.getContext();
        items = new ArrayList<>();
        adapter = new RecipeAdapter(context,items);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recipeRecycleView);
        mLayoutManager = new LinearLayoutManager(context,
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(context)) {
            recipeAtask = new RecipeAtask(items, adapter, progressDialog);
            recipeAtask.execute();
        }

      adapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
            @Override
            public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
               RecipeItem item = adapter.getItem(position);
               Intent intent = new Intent(getContext(), RecipeSubActivity.class);
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

        //백버튼시 메인 프래그먼트로 이동함
        new CommonMethod().fragmentBackPress((MainActivity)getActivity(),requireActivity(), this, R.id.tabMain);

        return viewGroup;
    }

   /* public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }*/


    public void recipeInfo() {


    }


}