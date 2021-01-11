package com.example.ateam_app.MangeTipPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class ManageTipAddapter extends RecyclerView.Adapter<ManageTipAddapter.ViewHolder> {

    ManageTipInter listener;

    Context context;
    ArrayList<ManagaeDTO> dtos;

    public ManageTipAddapter(Context context, ArrayList<ManagaeDTO> dtos) {
        this.context = context;
        this.dtos = dtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater
                .from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fragment_manager_tip_item, parent, false);

        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ManagaeDTO dto = dtos.get(position);
        holder.setItem(dto);
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView manage_tip_sub;
        ImageView manage_tip_img;
        TextView manage_tip_context;
        SearchView manageTipSearch;


        public ViewHolder(@NonNull View itemView, ManageTipInter listener) {
            super(itemView);

            manage_tip_sub = itemView.findViewById(R.id.manage_tip_sub);
            manage_tip_img = itemView.findViewById(R.id.manage_tip_img);
            manage_tip_context = itemView.findViewById(R.id.manage_tip_context);
            manageTipSearch = itemView.findViewById(R.id.manageTipSearch);

        }

        public void setItem(ManagaeDTO dto){
            manage_tip_sub.setText(dto.getManage_tip_sub());
            manage_tip_context.setText(dto.getManage_tip_context());
            manage_tip_img.setImageResource(dto.getResId());

        }
    }
}
