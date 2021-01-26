package com.example.ateam_app;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ateam_app.irdnt_list_package.IrdntListFragment;
import com.example.ateam_app.manage_tip_package.ManageTipFragment;
import com.example.ateam_app.recipe_fragment.Mainfragment_Recipe_Atask;
import com.example.ateam_app.recipe_fragment.RecipeAdapter;
import com.example.ateam_app.recipe_fragment.RecipeAtask;
import com.example.ateam_app.recipe_fragment.RecipeFragment;
import com.example.ateam_app.recipe_fragment.RecipeItem;

import java.util.ArrayList;
import java.util.Random;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class MainFragment extends Fragment {
    CardView shelfLifeAlertBanner, recipeRecommandBanner, manageTipBanner;
    Random rnd;
    Mainfragment_Recipe_Atask atask;
    TextView recipe_id;
    ImageView img_url;
    TextView sumry;
    RecipeItem recipeItem;
    ArrayList<RecipeItem> recipeItems;
    RecipeFragment recipeFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        Context context = getContext();
        shelfLifeAlertBanner = rootView.findViewById(R.id.shelfLifeAlertBanner);
        recipeRecommandBanner = rootView.findViewById(R.id.recipeRecommandBanner);
        manageTipBanner = rootView.findViewById(R.id.manageTipBanner);
        atask = new Mainfragment_Recipe_Atask();
        recipeItems = new ArrayList<>();


        img_url = rootView.findViewById(R.id.img_url);
        sumry = rootView.findViewById(R.id.sumry_main);

        //랜덤 레시피
        rnd = new Random();
        int recipe_id = rnd.nextInt(500);

      /*  if(isNetworkConnected(context) == true) {
            atask = new Mainfragment_Recipe_Atask(recipeItem, recipe_id);
            atask.execute();

        }*/

        //sumry.setText(recipeItem.getSumry());

        //유통기한 알림 배너 클릭 시 유통기한별 정렬 재료 리스트로 이동
        shelfLifeAlertBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(rootView.getContext(), "유통기한 알림배너", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(IrdntListFragment.newInstance());
            }
        });

        //레시피 추천 배너 클릭 시 해당 요리 레시피의 상세 정보로 이동
        recipeRecommandBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "레시피 추천 배너", Toast.LENGTH_SHORT).show();


            }
        });

        //냉장고 관리 팁 배너는 클릭 시 관리 팁 프래그먼트로 이동
        manageTipBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "냉장고 관리 팁 배너", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).replaceFragment(ManageTipFragment.newInstance());
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }


}