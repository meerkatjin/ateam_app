package com.example.ateam_app.user_pakage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.UserInfoChangeActivity;
import com.example.ateam_app.user_pakage.adapter.OnUserItemClickListener;
import com.example.ateam_app.user_pakage.adapter.UserAdapter;
import com.example.ateam_app.user_pakage.atask.GetUserListATask;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

//회원관리 프래그먼트
public class UserMnageFragment extends Fragment {

    GetUserListATask aTesk;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserDTO> dtos;
    UserAdapter adapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_user_manage, container, false);
        Context context = rootView.getContext();
        dtos = new ArrayList<>();
        adapter = new UserAdapter(context,dtos);
        recyclerView = rootView.findViewById(R.id.user_manage_recyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(context)){
            aTesk = new GetUserListATask(dtos, adapter);
            aTesk.execute();
        }
        
       adapter.setOnItemClicklistener(new OnUserItemClickListener() {
            @Override
            public void onItenClick(UserAdapter.ViewHolder holder, View view, int position) {
                UserDTO dto = adapter.getItem(position);
                Intent intent = new Intent(getContext(), UserInfoChangeActivity.class);
                intent.putExtra("loginDTO",dto);
                intent.putExtra("gradeCheck", 2);

                startActivity(intent);
            }
        });
        return rootView;
    }
}