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
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.MainFragment;
import com.example.ateam_app.R;
import com.example.ateam_app.RecipeSubActivity;
import com.example.ateam_app.manage_tip_package.ManageTipFragment;

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

        if(isNetworkConnected(context) == true) {
            recipeAtask = new RecipeAtask(items, adapter, progressDialog);
            recipeAtask.execute();
        }else{
            //인터넷 아노디는
        }

        adapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
            @Override
            public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
               /* RecipeItem item = adapter.getItem(position);
               Intent intent = new Intent(getContext(), RecipeSubActivity.class);
                intent.putExtra("img_url", item.getImg_url());
                startActivity(intent);*/
            }
        });
        return viewGroup;
    }

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }





}