package com.example.ateam_app;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeSub1Fragment extends Fragment {
    RecyclerView recipe_irdnt_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView nation_nm, cooking_time, calorie, qnt, level_nm;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recipe_sub1, container, false);
        Context context = rootView.getContext();

        nation_nm = rootView.findViewById(R.id.nation_nm);
        cooking_time = rootView.findViewById(R.id.cooking_time);
        calorie = rootView.findViewById(R.id.calorie);
        qnt = rootView.findViewById(R.id.qnt);
        level_nm = rootView.findViewById(R.id.level_nm);

        bundle = getArguments();
        if (bundle != null) {
            nation_nm.setText(bundle.getString("nation_nm"));
            cooking_time.setText(bundle.getString("cooking_time"));
            calorie.setText(bundle.getString("calorie"));
            qnt.setText(bundle.getString("qnt"));
            if (bundle.getString("level_nm").equals("초보환영")) {
                level_nm.setText("★☆☆ (EASY)");
            } else if (bundle.getString("level_nm").equals("보통")) {
                level_nm.setText("★★☆ (NORMAL)");
            } else {
                level_nm.setText("★★★ (HARD)");
            }
        }

        recipe_irdnt_recyclerView = rootView.findViewById(R.id.recipe_irdnt_recyclerView);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recipe_irdnt_recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

}