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

        return new ViewHolder(itemView);
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
        ImageView stre_step_image_url_im;
        TextView step_tip;
        LinearLayout recipe_item_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_id = itemView.findViewById(R.id.recipe_id);
            cooking_no = itemView.findViewById(R.id.cooking_no);
            cooking_dc = itemView.findViewById(R.id.cooking_dc);
            stre_step_image_url_im = itemView.findViewById(R.id.stre_step_image_url_im);
            step_tip = itemView.findViewById(R.id.step_tip);

        }
        public void setItem(RecipeSubItem item) {
            //recipe_id.setText(String.valueOf(item.getRecipe_id()));

                //cooking_no.setText(item.getCooking_no()+"번째 순서");

                //cooking_no.setText("");

                cooking_dc.setText(item.getCooking_dc());



                //cooking_dc.setText("");
                if (item.getStep_tip().trim().length() == 0) {
                    step_tip.setVisibility(View.GONE);
                } else {
                    step_tip.setVisibility(View.VISIBLE);
                }
                step_tip.setText(item.getStep_tip());

                //step_tip.setText("");


            if (item.getStre_step_image_url().trim().length() == 0) {
                stre_step_image_url_im.setVisibility(View.GONE);
            } else {
                stre_step_image_url_im.setVisibility(View.VISIBLE);
            }
            Glide.with(itemView).load(item.getStre_step_image_url()).into(stre_step_image_url_im);


        }

    }

}