package com.example.ateam_app;

import android.app.PendingIntent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class IrdntListTypeFragment extends Fragment {
    TabLayout irdnt_type_tabs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list_type, container, false);

        //재료 리스트 종류별 탭
        irdnt_type_tabs = rootView.findViewById(R.id.irdnt_type_tabs);
        irdnt_type_tabs.addTab(irdnt_type_tabs.newTab().setText("고기"));
        irdnt_type_tabs.addTab(irdnt_type_tabs.newTab().setText("수산물"));
        irdnt_type_tabs.addTab(irdnt_type_tabs.newTab().setText("채소/과일"));
        irdnt_type_tabs.addTab(irdnt_type_tabs.newTab().setText("유제품"));
        irdnt_type_tabs.addTab(irdnt_type_tabs.newTab().setText("양념/기타"));

        irdnt_type_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0) {

                } else if (position == 1) {

                } else if (position == 2) {

                } else if (position == 3) {

                } else if (position == 4) {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}