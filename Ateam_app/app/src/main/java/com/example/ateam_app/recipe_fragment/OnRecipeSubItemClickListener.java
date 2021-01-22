package com.example.ateam_app.recipe_fragment;

import android.view.View;

public interface OnRecipeSubItemClickListener {
    public void onItemClick(RecipeSubAdapter.ViewHolder holder, View view, int position);
}
