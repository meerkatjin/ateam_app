package com.example.ateam_app.irdnt_list_package;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class IrdntListAdapter extends RecyclerView.Adapter<IrdntListAdapter.ViewHolder> {
    private ArrayList<IrdntListDTO> items;

    public IrdntListAdapter(ArrayList<IrdntListDTO> items) {
        this.items = items;
    }

    //화면 연결
    @NonNull
    @Override
    public IrdntListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.irdntview, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull IrdntListAdapter.ViewHolder holder, int position) {
        holder.content_nm.setText(items.get(position).getContent_nm());
        holder.content_ty.setText(items.get(position).getContent_ty());
        holder.shelf_life_end.setText(items.get(position).getShelf_life_end());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView content_nm, content_ty, shelf_life_end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            content_nm = (TextView) itemView.findViewById(R.id.content_nm);
            content_ty = (TextView) itemView.findViewById(R.id.content_ty);
            shelf_life_end = (TextView) itemView.findViewById(R.id.shelf_life_end);
        }

        public void setItem(IrdntListDTO dto) {
            content_nm.setText(dto.getContent_nm());
            content_ty.setText(dto.getContent_ty());
            shelf_life_end.setText(dto.getShelf_life_end());
        }
    }
}
