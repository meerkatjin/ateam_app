package com.example.ateam_app.RecipiFragment;

import android.view.View;

public interface OnRecipeItemClickListener {
    public void onItemClick(RecipeAddapter.ViewHolder holder, View view, int position);
}
