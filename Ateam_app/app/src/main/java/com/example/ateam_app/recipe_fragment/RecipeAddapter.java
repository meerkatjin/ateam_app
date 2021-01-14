package com.example.ateam_app.recipe_fragment;

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

public class RecipeAddapter extends RecyclerView.Adapter<RecipeAddapter.ViewHolder> implements OnRecipeItemClickListener{
    private ArrayList<RecipeDTO> items = new ArrayList<RecipeDTO>();
    static OnRecipeItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.fragment_recipe_item, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAddapter.ViewHolder viewHolder, int position) {
        RecipeDTO item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnItemClicklistener(OnRecipeItemClickListener listener) {
        this.listener = listener; }






    @Override
    public void onItemClick(RecipeAddapter.ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_nm_ko;     //레시피 제목
        TextView sumry;            //간략소개
        TextView level_nm;         //난이도
        ImageView img_url;             //대표 이미지 주소
        LinearLayout recipe_item_layout;
        TextView recipe_id;              //키값
        public ViewHolder(View itemView, final OnRecipeItemClickListener listener) {
            super(itemView);
            recipe_nm_ko = itemView.findViewById(R.id.recipe_nm_ko);
            sumry = itemView.findViewById(R.id.sumry);
            level_nm = itemView.findViewById(R.id.level_nm);
            img_url = itemView.findViewById(R.id.img_url);
            recipe_item_layout = itemView.findViewById(R.id.recipe_item_layout);
            recipe_id = itemView.findViewById(R.id.recipe_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);//
                    }
                }
            });
        }
        public void setItem(RecipeDTO item){
            recipe_nm_ko.setText(item.getRecipe_nm_ko());
            sumry.setText(item.getSumry());
            level_nm.setText(item.getLevel_nm());
            img_url.setImageResource(item.getImg_url());

        }
    }

    public void addItem(RecipeDTO item){
        items.add(item);
    }

    public void setItems(ArrayList<RecipeDTO> items){
        this.items = items;
    }

    public RecipeDTO getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, RecipeDTO item){
        items.set(position, item);
    }


}
