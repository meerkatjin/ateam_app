package com.example.ateam_app.user_pakage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.adapter.OnUserItemClickListener;
import com.example.ateam_app.user_pakage.adapter.UserAdapter;
import com.example.ateam_app.user_pakage.atask.GetUserList;
import com.example.ateam_app.user_pakage.atask.LoginSelect;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

//회원관리 프래그먼트
public class UserMnageFragment extends Fragment {

    GetUserList aTesk;
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
            aTesk = new GetUserList(dtos, adapter);
            aTesk.execute();
        }
        
       /* adapter.setOnItemClicklistener(new OnUserItemClickListener() {
            @Override
            public void onItenClick(UserAdapter.ViewHolder holder, View view, int position) {
                Toast.makeText(context, "눌렀읍니다.", Toast.LENGTH_SHORT).show();
            }
        });
*/
        return rootView;
    }
}