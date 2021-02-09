package com.example.ateam_app.recipe_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class RecipeSubIrdntAdapter extends RecyclerView.Adapter<RecipeSubIrdntAdapter.ViewHolder> {
    ArrayList<RecipeSubIrdnt> items;
    Context context;

    public RecipeSubIrdntAdapter(ArrayList<RecipeSubIrdnt> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeSubIrdntAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.recipe_irdnts, viewGroup, false);

        return new RecipeSubIrdntAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSubIrdntAdapter.ViewHolder holder, int position) {
        RecipeSubIrdnt recipeSubIrdnt = items.get(position);
        holder.setItem(recipeSubIrdnt);
    }

    public void addItem(RecipeSubIrdnt item){
        items.add(item);
    }

    public void setItems(ArrayList<RecipeSubIrdnt> items){
        this.items = items;
    }

    public RecipeSubIrdnt getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, RecipeSubIrdnt item){
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView irdnt_nm, irdnt_cpcty, irdnt_ty_nm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            irdnt_nm = itemView.findViewById(R.id.irdnt_nm);
            irdnt_cpcty = itemView.findViewById(R.id.irdnt_cpcty);
            irdnt_ty_nm = itemView.findViewById(R.id.irdnt_ty_nm);
        }

        public void setItem(RecipeSubIrdnt item) {
            irdnt_nm.setText(item.getIrdnt_nm());
            irdnt_cpcty.setText(item.getIrdnt_cpcty());
            irdnt_ty_nm.setText(item.getIrdnt_ty_nm());
        }
    }
}
