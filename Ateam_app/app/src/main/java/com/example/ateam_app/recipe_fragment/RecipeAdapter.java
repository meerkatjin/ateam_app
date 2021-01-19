package com.example.ateam_app.recipe_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements OnRecipeItemClickListener{
    ArrayList<RecipeItem> items;
    Context context;
    static OnRecipeItemClickListener listener;

    public RecipeAdapter(ArrayList<RecipeItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public RecipeAdapter(Context context, ArrayList<RecipeItem> items) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.fragment_recipe_item, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int position) {
        RecipeItem item = items.get(position);
        viewHolder.setItem(item);
    }

    public void addItem(RecipeItem item){
        items.add(item);
    }

    public void setItems(ArrayList<RecipeItem> items){
        this.items = items;
    }

    public RecipeItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, RecipeItem item){
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void removeAllItem(){

        items.clear();
    }
    public void setOnItemClicklistener(OnRecipeItemClickListener listener) {
        this.listener = listener; }






    @Override
    public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_id;
        TextView recipe_nm_ko;
        TextView sumry;
        TextView nation_nm;
        TextView ty_nm;
        TextView cooking_time;
        TextView calorie;
        TextView qnt;
        TextView level_nm;
        TextView irdnt_code;
        ImageView img_url;
        LinearLayout recipe_item_layout;

        public ViewHolder(View itemView, final OnRecipeItemClickListener listener) {
            super(itemView);
            recipe_id = itemView.findViewById(R.id.recipe_id);
            recipe_nm_ko = itemView.findViewById(R.id.recipe_nm_ko);
            sumry = itemView.findViewById(R.id.sumry);
            level_nm = itemView.findViewById(R.id.level_nm);
            img_url = itemView.findViewById(R.id.img_url);
            recipe_item_layout = itemView.findViewById(R.id.recipe_item_layout);


        }
        public void setItem(RecipeItem item){
            recipe_nm_ko.setText(item.getRecipe_nm_ko());
            sumry.setText(item.getSumry());
            level_nm.setText(item.getLevel_nm());


        }
    }


}
