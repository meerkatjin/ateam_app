package com.example.ateam_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.recipe_fragment.RecipeItem;

import java.util.ArrayList;

public class RecipeSub1Adapter extends RecyclerView.Adapter<RecipeSub1Adapter.ViewHolder> {
    Context context;
    ArrayList<RecipeItem> items;

    public RecipeSub1Adapter (Context context, ArrayList<RecipeItem> items) {
        this.context = context;
        this.items = items;
    }

    //화면 연결
    @NonNull
    @Override
    public RecipeSub1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.recipe_irdnts, viewGroup, false);

        return new ViewHolder(itemView);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull RecipeSub1Adapter.ViewHolder holder, int position) {
        RecipeItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView irdnt_nm, irdnt_cpcty, irdnt_ty_nm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(RecipeItem item) {
        }

    }

}
