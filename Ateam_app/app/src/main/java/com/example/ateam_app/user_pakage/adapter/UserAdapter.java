package com.example.ateam_app.user_pakage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
        implements OnUserItemClickListener{

    OnUserItemClickListener listener;

    private Context context;
    private ArrayList<UserDTO> dtos;

    public UserAdapter(Context context, ArrayList<UserDTO> dtos){
        this.context = context;
        this.dtos = dtos;
    }

    // 화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View userView = inflater.inflate(R.layout.usercardview, parent, false);

        return new ViewHolder(userView, listener);
    }

    // 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO dto = dtos.get(position);
        holder.setItem(dto);
    }

    public UserDTO getItem(int position) {return dtos.get(position);}

    public void addDTO(UserDTO dto){dtos.add(dto);}

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    @Override
    public void onItenClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItenClick(holder,view,position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_pro_img;
        TextView user_id;
        TextView user_email;
        TextView user_nm;
        TextView user_grade;
        TextView user_type;

        public ViewHolder(@NonNull View itemView, OnUserItemClickListener listener) {
          super(itemView);
          user_pro_img = itemView.findViewById(R.id.user_pro_img);
          user_id = itemView.findViewById(R.id.user_id);
          user_email = itemView.findViewById(R.id.user_email);
          user_nm = itemView.findViewById(R.id.user_nm);
          user_grade = itemView.findViewById(R.id.user_grade);
          user_type = itemView.findViewById(R.id.user_type);

          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(listener != null){
                      listener.onItenClick(ViewHolder.this, itemView, getAdapterPosition());
                  }
              }
          });
        }

        public void setItem(UserDTO dto){
            Glide.with(itemView).load(dto.getUser_pro_img()).into(user_pro_img);

            user_id.append(String.valueOf(dto.getUser_id()));
            user_email.append(dto.getUser_email());
            user_nm.append(dto.getUser_nm());
            user_grade.append(dto.getUser_grade());
            user_type.append(dto.getUser_type());
        }
    }
}
