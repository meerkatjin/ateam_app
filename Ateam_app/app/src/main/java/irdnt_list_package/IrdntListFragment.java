package irdnt_list_package;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ateam_app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class IrdntListFragment extends Fragment {

    IrdntListAdapter adapter;
    IrdntListDTO dto;
    ArrayList<IrdntListDTO> items;
    RecyclerView irdntRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TabLayout irdnt_sort_tab, irdnt_sort_type_tab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);
        Context context = rootView.getContext();

        irdnt_sort_tab = rootView.findViewById(R.id.irdnt_sort_tab);
        irdnt_sort_type_tab = rootView.findViewById(R.id.irdnt_sort_type_tab);
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("종류별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("유통기한별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("이름별"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("고기"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("수산물"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("채소"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("과일"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("유제품"));

        irdnt_sort_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if (position == 0) {
                    irdnt_sort_type_tab.setVisibility(View.VISIBLE);
                    irdnt_sort_type_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });
                } else if (position == 1) {
                    irdnt_sort_type_tab.setVisibility(View.GONE);
                } else if (position == 2) {
                    irdnt_sort_type_tab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        irdntRecyclerView = (RecyclerView) rootView.findViewById(R.id.irdntRecyclerView);
        layoutManager = new LinearLayoutManager(context);
        irdntRecyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(items);

        dto = new IrdntListDTO("양파", "야채", "~ 2021.01.28");
        items.add(dto);
        dto = new IrdntListDTO("돼지고기", "고기", "~ 2021.01.22");
        items.add(dto);
        dto = new IrdntListDTO("우유", "유제품", "~ 2021.01.19");
        items.add(dto);
        dto = new IrdntListDTO("청양고추", "야채", "~ 2021.01.27");
        items.add(dto);
        dto = new IrdntListDTO("콜라", "음료", "~ 2021.08.02");
        items.add(dto);
        dto = new IrdntListDTO("훈제오리", "고기", "~ 2021.04.13");
        items.add(dto);
        irdntRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();



        return rootView;
    }//onCreateView()

    public static IrdntListFragment newInstance() {
        return new IrdntListFragment();
    }

}//class