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
import com.example.ateam_app.R;
import com.example.ateam_app.RecipeSubActivity;
import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;


import java.util.ArrayList;


public class RecipeFragment extends Fragment {
    public static RecipeItem selItem = null;
    RecipeAtask recipeAtask;
    RecipeAdapter adapter;
    ArrayList<RecipeItem> items;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;



    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recipe, container, false);

        items = new ArrayList<>();

        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setAdapter(adapter);

        if (viewGroup instanceof RecyclerView) {
            Context context = viewGroup.getContext();
            RecyclerView mRecyclerView = (RecyclerView) viewGroup;
            mRecyclerView.setHasFixedSize(true);


            // use a linear layout manager

            mLayoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(mLayoutManager);


            // specify an adapter (see also next example)

            adapter = new RecipeAdapter(context,items);

            mRecyclerView.setAdapter(adapter);

        }


    /*    if(isNetworkConnected(context) == true){
            recipeAtask = new RecipeAtask(items, adapter, progressDialog);
            recipeAtask.execute();
        }*/



        //activity = (MainActivity) getActivity();

        //dto = new RecipeDTO("김치찜", "김치찜이 짜다", "어렵다", R.drawable.ic_launcher_background);
        //dtos.add(dto);

        //o
        //addapter.notifyDataSetChanged();
        /*if(isNetworkConnected(context) == true){
            recipeAtask = new RecipeAtask(recipeItemArrayList, adapter, progressDialog);
            recipeAtask.execute();
        }else {
            Toast.makeText(context, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }*/
//x
       /* adapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
            @Override
            public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
                RecipeItem item = adapter.getItem(position);
                Intent intent = new Intent(getContext(), RecipeSubActivity.class);
                intent.putExtra("img_url", item.getImg_url());
                startActivity(intent);



            }


        });*/




        return viewGroup;
    }



}