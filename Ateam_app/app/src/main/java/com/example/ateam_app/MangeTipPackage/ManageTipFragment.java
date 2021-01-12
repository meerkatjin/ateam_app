package com.example.ateam_app.MangeTipPackage;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.MainFragment;
import com.example.ateam_app.R;

import java.util.ArrayList;


public class ManageTipFragment extends Fragment  {


    private ManageTipAddapter addapter;
    private ManagaeDTO dto;
    private ArrayList<ManagaeDTO> dtos;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager linearLayoutManager;


    public ManageTipFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

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

            ManagaeDTO dto = new ManagaeDTO("봄", "봄에는 벚꽃구경을 가자", R.drawable.ic_launcher_background);
            dtos.add(dto);
            dto = new ManagaeDTO("여름", "여름에는 피서지를 가자", R.drawable.ic_launcher_background);
            dtos.add(dto);

            addapter.notifyDataSetChanged();


        return viewGroup;
    }



}