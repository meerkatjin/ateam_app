package irdnt_list_package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        holder.irdnt_nm.setText(items.get(position).getIrdnt_nm());
        holder.content_ty.setText(items.get(position).getContent_ty());
        holder.due_date.setText(items.get(position).getDue_date());

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
        protected TextView irdnt_nm, content_ty, due_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            irdnt_nm = (TextView) itemView.findViewById(R.id.irdnt_nm);
            content_ty = (TextView) itemView.findViewById(R.id.content_ty);
            due_date = (TextView) itemView.findViewById(R.id.due_date);
        }

        public void setItem(IrdntListDTO dto) {
            irdnt_nm.setText(dto.getIrdnt_nm());
            content_ty.setText(dto.getContent_ty());
            due_date.setText(dto.getDue_date());
        }
    }
}
