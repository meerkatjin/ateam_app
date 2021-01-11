package com.example.ateam_app.MangeTipPackage;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageTipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageTipFragment extends Fragment  {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    ManageTipAddapter adapter;
    ArrayList<ManagaeDTO> dtos;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageTipFragment() {
        // Required empty public constructor
    }

    public static ManageTipFragment newInstance(String param1, String param2) {
        ManageTipFragment fragment = new ManageTipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_manage_tip, container, false);
        recyclerView = viewGroup.findViewById(R.id.manageTipRecycleView);

        return viewGroup;
    }



}