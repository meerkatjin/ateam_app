package com.example.ateam_app.RecipiFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.MangeTipPackage.ManagaeDTO;
import com.example.ateam_app.MangeTipPackage.ManageTipAddapter;
import com.example.ateam_app.R;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {
    private RecipeAddapter addapter;
    private RecipeDTO dto;
    private ArrayList<RecipeDTO> dtos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;




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

        addapter = new RecipeAddapter(dtos);
        recyclerView.setAdapter(addapter);


        dto = new RecipeDTO("김치찜", "김치찜이 짜다", "어렵다", R.drawable.ic_launcher_background);
        dtos.add(dto);
        addapter.notifyDataSetChanged();





        return viewGroup;
    }


}