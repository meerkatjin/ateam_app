package irdnt_list_package;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class IrdntListAdapter extends RecyclerView.Adapter<IrdntListAdapter.ViewHolder> {
    private ArrayList<IrdntListDTO> items = new ArrayList<>();

    @NonNull
    @Override
    public IrdntListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IrdntListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView irdnt_nm, irdnt_ty_code, due_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
