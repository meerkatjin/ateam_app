package com.example.ateam_app.RecipiFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class RecipeAddapter extends RecyclerView.Adapter<RecipeAddapter.CustomViewHolder> {
    private ArrayList<RecipeDTO> dtos;

    public RecipeAddapter(ArrayList<RecipeDTO> dtos) {
        this.dtos = dtos;
    }

    //생성주기
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recipe_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    //추가 될 때 생명주기
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.recipe_nm_ko.setText(dtos.get(position).getRecipe_nm_ko());
        holder.sumry.setText(dtos.get(position).getSumry());
        holder.level_nm.setText(dtos.get(position).getLevel_nm());
        holder.img_url.setImageResource(dtos.get(position).getImg_url());

        //레시피 클릭하면 레시피 서브로 이동


    }

    @Override
    public int getItemCount() {
        return (null != dtos ? dtos.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView recipe_nm_ko;
        protected TextView sumry;
        protected TextView level_nm;
        protected ImageView img_url;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recipe_nm_ko  = (TextView) itemView.findViewById(R.id.recipe_nm_ko);
            this.sumry = (TextView) itemView.findViewById(R.id.sumry);
            this.level_nm = (TextView) itemView.findViewById(R.id.level_nm);
            this.img_url = (ImageView) itemView.findViewById(R.id.img_url);

        }
    }
}
