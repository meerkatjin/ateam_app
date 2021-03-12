package com.example.ateam_app.manage_tip_package;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;

import java.util.ArrayList;


public class ManageTipFragment extends Fragment  {


    private ManageTipAddapter addapter;
    private ManagaeDTO dto;
    private ArrayList<ManagaeDTO> dtos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

             ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_manage_tip, container, false);


            Context context = viewGroup.getContext();
            recyclerView = (RecyclerView) viewGroup.findViewById(R.id.manageTipRecycleView);

            mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);

            dtos = new ArrayList<>();

            addapter = new ManageTipAddapter(dtos);
            recyclerView.setAdapter(addapter);

            dto = new ManagaeDTO("봄", "봄에는 굽네 치킨먹으로 가자", R.drawable.ic_launcher_background);
            dtos.add(dto);
            dto = new ManagaeDTO("여름", "여름에는 미스터 피자먹으로 가자", R.drawable.ic_launcher_background);
            dtos.add(dto);
            dto = new ManagaeDTO("가을", "가을에는 맘스터치 햄버거 먹으로 가자", R.drawable.ic_launcher_background);
            dtos.add(dto);
            dto = new ManagaeDTO("여름", "겨울에는 집에 있자", R.drawable.ic_launcher_background);
            dtos.add(dto);
            addapter.notifyDataSetChanged();

            new CommonMethod().fragmentBackPress((MainActivity)getActivity(), requireActivity(), this, R.id.tabMain);

        return viewGroup;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public static ManageTipFragment newInstance() {
        return new ManageTipFragment();
    }
}