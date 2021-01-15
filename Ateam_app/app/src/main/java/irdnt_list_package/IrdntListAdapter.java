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
    ArrayList<IrdntListDTO> items;
    Context context;


    public IrdntListAdapter(Context context, ArrayList<IrdntListDTO> items) {
        this.context = context;
        this.items = items;
    }

    //화면 연결
    @NonNull
    @Override
    public IrdntListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.irdntview, viewGroup, false);

        return new ViewHolder(itemView);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull IrdntListAdapter.ViewHolder holder, int position) {
        IrdntListDTO dto = items.get(position);
        holder.setItem(dto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout irdnt_layout;
        TextView irdnt_nm, irdnt_ty_code, due_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            irdnt_layout = itemView.findViewById(R.id.irdnt_layout);

            irdnt_nm = itemView.findViewById(R.id.irdnt_nm);
            due_date = itemView.findViewById(R.id.due_date);
        }

        public void setItem(IrdntListDTO dto) {
            irdnt_nm.setText(dto.getIrdnt_nm());
            irdnt_ty_code.setText(dto.getIrdnt_ty_code());
            due_date.setText(dto.getDue_date());
        }
    }

    public void addItem(IrdntListDTO dto) {
        items.add(dto);
    }

}
