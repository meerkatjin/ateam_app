package com.example.ateam_app.recipe_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.R;
import com.example.ateam_app.RecipeSubActivity;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {
    RecipeAddapter addapter;
     RecipeDTO dto;
     ArrayList<RecipeDTO> dtos;
     RecyclerView recyclerView;
     RecyclerView.LayoutManager mLayoutManager;



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

        Context context = viewGroup.getContext();
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recipeRecycleView);

        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        dtos = new ArrayList<>();

        addapter = new RecipeAddapter();

        //activity = (MainActivity) getActivity();

        //dto = new RecipeDTO("김치찜", "김치찜이 짜다", "어렵다", R.drawable.ic_launcher_background);
        //dtos.add(dto);
        addapter.addItem(new RecipeDTO("오", "어렵다", "힘들다", R.drawable.ic_launcher_foreground));
        recyclerView.setAdapter(addapter);
        //addapter.notifyDataSetChanged();

        addapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
            @Override
            public void onItemClick(RecipeAddapter.ViewHolder holder, View view, int position) {
                RecipeDTO item = addapter.getItem(position);
                Intent intent = new Intent(getContext(), RecipeSubActivity.class);
                intent.putExtra("img_url", item.getImg_url());
                startActivity(intent);



            }


        });




        return viewGroup;
    }



}