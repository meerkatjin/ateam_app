package com.example.ateam_app.recipe_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ateam_app.R;

import java.util.ArrayList;

public class RecipeSubAdapter extends RecyclerView.Adapter<RecipeSubAdapter.ViewHolder> implements OnRecipeSubItemClickListener{
    ArrayList<RecipeSubItem> items;
    Context context;
    static OnRecipeSubItemClickListener listener;

    public RecipeSubAdapter(Context context, ArrayList<RecipeSubItem> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.activity_recipe_sub_item, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSubAdapter.ViewHolder viewHolder, int position) {
        RecipeSubItem item = items.get(position);

        viewHolder.setItem(item);
    }





    public void addItem(RecipeSubItem item){
        items.add(item);
    }

    public void setItems(ArrayList<RecipeSubItem> items){
        this.items = items;
    }

    public RecipeSubItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, RecipeSubItem item){
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return  items.size()  ;
    }

    public void removeAllItem(){
        items.clear();
    }

    public void setOnItemClicklistener(OnRecipeSubItemClickListener listener) {
        this.listener = listener;
    }






    @Override
    public void onItemClick(RecipeSubAdapter.ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder,view,position);

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_id;
        TextView cooking_no;
        TextView cooking_dc;
        ImageView img_url_im;
        TextView step_tip;
        LinearLayout recipe_item_layout;

        public ViewHolder(@NonNull View itemView, OnRecipeSubItemClickListener listener) {
            super(itemView);
            recipe_id = itemView.findViewById(R.id.recipe_id);
            cooking_no = itemView.findViewById(R.id.cooking_no);
            cooking_dc = itemView.findViewById(R.id.cooking_dc);
            img_url_im = itemView.findViewById(R.id.img_url_im);
            step_tip = itemView.findViewById(R.id.step_tip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                itemView, position);
                    }
                }
            });


        }
        public void setItem(RecipeSubItem item) {
            recipe_id.setText(String.valueOf(item.getRecipe_id()));
            cooking_no.setText(item.getCooking_no());
            cooking_dc.setText(item.getCooking_dc());
            //img_url_im.setText(item.getNation_nm());
            step_tip.setText(item.getStep_tip());


            //Glide.with(itemView).load(item.getStre_step_image_url()).into(img_url_im);

        }


    }


}