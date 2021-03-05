package com.example.ateam_app.irdnt_list_package;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;
import com.example.ateam_app.recipe_fragment.RecipeItem;

import java.util.ArrayList;

public class IrdntListAdapter extends RecyclerView.Adapter<IrdntListAdapter.ViewHolder> implements OnIrdntItemClickListener {
    Context context;
    ArrayList<IrdntListDTO> items;
    static OnIrdntItemClickListener listener;
    ArrayList<Long> irdnt_ids, new_ids;

    public IrdntListAdapter(Context context, ArrayList<IrdntListDTO> items, ArrayList<Long> irdnt_ids, ArrayList<Long> new_ids) {
        this.context = context;
        this.items = items;
        this.irdnt_ids = irdnt_ids;
        this.new_ids = new_ids;
    }

    //화면 연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.irdntview, viewGroup, false);

        return new ViewHolder(itemView, listener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull IrdntListAdapter.ViewHolder holder, int position) {
        IrdntListDTO item = items.get(position);

        holder.irdnt_layout.setBackgroundColor(Color.parseColor("#D9D9D9"));
        holder.shelf_life_end.setTextColor(Color.parseColor("#FF4444"));
        if(irdnt_ids == null && new_ids == null){
            holder.setItem(item);
        }if(irdnt_ids != null){
            holder.setItem(item, irdnt_ids, 0);
        }if(new_ids != null){
            holder.setItem(item, new_ids, 1);
        }
    }

    public IrdntListDTO getItem(int position) { return items.get(position); }

    public void setOnItemClickListener(OnIrdntItemClickListener listener) {
        this.listener = listener;
    }

    public void addItem(IrdntListDTO item){
        items.add(item);
    }

    public void setItems(ArrayList<IrdntListDTO> items){
        this.items = items;
    }

    public void setItem(int position, IrdntListDTO item){
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return (items != null ? items.size() : 0);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView content_list_id, content_nm, content_ty, shelf_life_end;
        LinearLayout irdnt_layout;

        public ViewHolder(@NonNull View itemView, OnIrdntItemClickListener listener) {
            super(itemView);

            irdnt_layout = itemView.findViewById(R.id.irdnt_layout);
            content_list_id = itemView.findViewById(R.id.content_list_id);
            content_nm = itemView.findViewById(R.id.content_nm);
            content_ty = itemView.findViewById(R.id.content_ty);
            shelf_life_end = itemView.findViewById(R.id.shelf_life_end);

            //재료리스트에서 재료를 눌렀을 때 삭제 메소드를 실행할 지 알림
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, items.get(position).getContent_nm() + ", " + items.get(position).getContent_ty(), Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, itemView, position);
                    }
                }
            });
        }

        public void setItem(IrdntListDTO dto, ArrayList<Long> ids, int mode) {
            if (mode == 0){
                for (Long id : ids) {
                    if(id == dto.getContent_list_id()){
                        irdnt_layout.setBackgroundResource(R.drawable.redcard_background_drawer);
                        shelf_life_end.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }else{
                for (Long id : ids) {
                    if(id == dto.getContent_list_id()){
                        irdnt_layout.setBackgroundColor(Color.parseColor("#f4dc42"));
                        shelf_life_end.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }
            }
            //content_list_id.setText(dto.getContent_list_id());
            content_nm.setText(dto.getContent_nm());
            content_ty.setText(dto.getContent_ty());
            shelf_life_end.setText(dto.getShelf_life_end());
        }
        public void setItem(IrdntListDTO dto) {
            //content_list_id.setText(dto.getContent_list_id());
            content_nm.setText(dto.getContent_nm());
            content_ty.setText(dto.getContent_ty());
            shelf_life_end.setText(dto.getShelf_life_end());
        }
    }
}
