package com.example.ateam_app.user_pakage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.UserInfoChangeActivity;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    ArrayList<UserDTO> dtos;

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

        return new ViewHolder(userView);
    }

    // 데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDTO dto = dtos.get(position);
        holder.setItem(dto);

        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , UserInfoChangeActivity.class);
                intent.putExtra("loginDTO",dto);
                intent.putExtra("gradeCheck", 2);

                context.startActivity(intent);
            }
        });
    }

    public UserDTO getItem(int position) {return dtos.get(position);}

    public void addDTO(UserDTO dto){dtos.add(dto);}

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout user_layout;
        ImageView user_pro_img;
        TextView user_id;
        TextView user_email;
        TextView user_nm;
        TextView user_grade;
        TextView user_type;

        public ViewHolder(@NonNull View itemView) {
          super(itemView);
          user_layout = itemView.findViewById(R.id.user_layout);
          user_pro_img = itemView.findViewById(R.id.user_pro_img);
          user_id = itemView.findViewById(R.id.user_id);
          user_email = itemView.findViewById(R.id.user_email);
          user_nm = itemView.findViewById(R.id.user_nm);
          user_grade = itemView.findViewById(R.id.user_grade);
          user_type = itemView.findViewById(R.id.user_type);
        }

        public void setItem(UserDTO dto){
            Glide.with(itemView).load(dto.getUser_pro_img()).into(user_pro_img);

            user_id.setText("ID : " + dto.getUser_id());
            user_email.setText("Email : " + dto.getUser_email());
            user_nm.setText("Name : " + dto.getUser_nm());
            user_grade.setText("Grade : " + dto.getUser_grade());
            user_type.setText("Type : " + dto.getUser_type());
        }
    }
}
