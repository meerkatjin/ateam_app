package com.example.ateam_app;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.recipe_fragment.RecipeSubAdapter;
import com.example.ateam_app.recipe_fragment.RecipeSubAtask;

public class RecipeSub2Fragment extends Fragment {
    RecipeSubAtask recipeSubAtask;
    RecipeSubAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_sub2, container, false);
        Context context = rootView.getContext();



        return rootView;
    }
}