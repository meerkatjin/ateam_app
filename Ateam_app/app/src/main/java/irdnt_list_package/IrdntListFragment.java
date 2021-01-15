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

    Button btnIrdntSortType, btnIrdntSortDate, btnIrdntSortName;
    IrdntListAdapter adapter;
    IrdntListDTO dto;
    ArrayList<IrdntListDTO> items;
    RecyclerView irdntRecyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);
        Context context = rootView.getContext();
        irdntRecyclerView = rootView.findViewById(R.id.irdntRecyclerView);
        layoutManager = new LinearLayoutManager(context);
        irdntRecyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items);

        adapter.addItem(new IrdntListDTO("양파", "야채", "~ 2021.02.14"));
        irdntRecyclerView.setAdapter(adapter);

        btnIrdntSortType = rootView.findViewById(R.id.btnIrdntSortType);
        btnIrdntSortDate = rootView.findViewById(R.id.btnIrdntSortDate);
        btnIrdntSortName = rootView.findViewById(R.id.btnIrdntSortName);

        //종류별 버튼 클릭 시 종류별로 재료 정렬
        btnIrdntSortType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //유통기한별 버튼 클릭 시 유통기한 순로 재료 정렬
        btnIrdntSortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //이름별 버튼 클릭시 이름 순으로 재료 정렬(가나다 순)
        btnIrdntSortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }//onCreateView()

    public static IrdntListFragment newInstance() {
        return new IrdntListFragment();
    }

}//class