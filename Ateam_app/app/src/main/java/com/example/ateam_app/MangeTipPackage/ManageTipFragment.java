package com.example.ateam_app.MangeTipPackage;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ateam_app.MainFragment;
import com.example.ateam_app.R;

import java.util.ArrayList;


public class ManageTipFragment extends Fragment  {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ManageTipAddapter adapter;
    ArrayList<ManagaeDTO> dtos;
    ManagaeDTO dto;
    Point size;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_manage_tip, container, false);
        recyclerView = viewGroup.findViewById(R.id.manageTipRecycleView);

        return viewGroup;
    }



}