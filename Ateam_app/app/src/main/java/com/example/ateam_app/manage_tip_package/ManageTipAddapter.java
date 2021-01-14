package com.example.ateam_app.manage_tip_package;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class ManageTipAddapter extends RecyclerView.Adapter<ManageTipAddapter.CustomViewHolder> {

    private ArrayList<ManagaeDTO> dtos;

    public ManageTipAddapter(ArrayList<ManagaeDTO> dtos) {
        this.dtos = dtos;
    }

    //생성주기
    @NonNull
    @Override
    public ManageTipAddapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_manager_tip_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }


    //추가 될 때 생명주기
    @Override
    public void onBindViewHolder(@NonNull ManageTipAddapter.CustomViewHolder holder, int position) {
        holder.manage_tip_img.setImageResource(dtos.get(position).getManage_tip_img());
        holder.manage_tip_sub.setText(dtos.get(position).getManage_tip_sub());
        holder.manage_tip_context.setText(dtos.get(position).getManage_tip_context());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curSub = holder.manage_tip_sub.getText().toString();
                Toast.makeText(v.getContext(), curSub, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != dtos ? dtos.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView manage_tip_img;
        protected TextView manage_tip_sub;
        protected TextView manage_tip_context;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.manage_tip_img = (ImageView) itemView.findViewById(R.id.manage_tip_img);
            this.manage_tip_context = (TextView) itemView.findViewById(R.id.manage_tip_context);
            this.manage_tip_sub = (TextView) itemView.findViewById(R.id.manage_tip_sub);

        }
    }//CustmViewHolder Class


}
