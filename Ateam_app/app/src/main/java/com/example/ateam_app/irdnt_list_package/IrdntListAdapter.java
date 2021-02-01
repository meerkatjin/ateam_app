package com.example.ateam_app.irdnt_list_package;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public IrdntListAdapter(Context context, ArrayList<IrdntListDTO> items) {
        this.context = context;
        this.items = items;
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
        holder.setItem(item);
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

        public ViewHolder(@NonNull View itemView, OnIrdntItemClickListener listener) {
            super(itemView);

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

        public void setItem(IrdntListDTO dto) {
            //content_list_id.setText(dto.getContent_list_id());
            content_nm.setText(dto.getContent_nm());
            content_ty.setText(dto.getContent_ty());
            shelf_life_end.setText(dto.getShelf_life_end());
        }
    }
}
