package com.example.ateam_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.number.ScientificNotation;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ateam_app.board_package.atask.Notice_Atask;
import com.example.ateam_app.board_package.dto.BoardDTO;
import com.example.ateam_app.board_package.fragment.BoardFragment;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.atask.IrdntLifeEndNumATask;
import com.example.ateam_app.irdnt_list_package.atask.IrdntNewContentNumATask;
import com.example.ateam_app.recipe_fragment.Mainfragment_Recipe_Atask;
import com.example.ateam_app.recipe_fragment.RecipeItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class MainFragment extends Fragment {
    public static RecipeItem main_recipe_item = null;
    CardView shelfLifeAlertBanner,newContentBenner, recipeRecommandBanner, manageTipBanner;
    TextView recipe_id, recipe_nm_ko, sumry, nation_nm, qnt, calorie, shelfLifeAlertText, newContentText, notice_more;
    ImageView img_url;
    LinearLayout linearMainFragment, notice_container;
    Bundle extra;

    String endLifeNum = "0";
    String newContentNum = "0";

    ArrayList<BoardDTO> noticeList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        //Log.d(TAG, "onCreateView: "+ recipeItem);
        shelfLifeAlertText = rootView.findViewById(R.id.shelfLifeAlertText);
        shelfLifeAlertBanner = rootView.findViewById(R.id.shelfLifeAlertBanner);
        newContentBenner = rootView.findViewById(R.id.newContentBenner);
        newContentText = rootView.findViewById(R.id.newContentText);
        recipeRecommandBanner = rootView.findViewById(R.id.recipeRecommandBanner);
        manageTipBanner = rootView.findViewById(R.id.manageTipBanner);
        recipe_nm_ko = rootView.findViewById(R.id.recipe_nm_ko_mf);
        img_url = rootView.findViewById(R.id.img_url_mf);
        sumry = rootView.findViewById(R.id.sumry_main);
        recipe_id = rootView.findViewById(R.id.recipe_id_mf);
        linearMainFragment = rootView.findViewById(R.id.linearMainFragment);
        calorie = rootView.findViewById(R.id.calorie);
        nation_nm = rootView.findViewById(R.id.nation_nm);
        qnt = rootView.findViewById(R.id.qnt);
        notice_container = rootView.findViewById(R.id.notice_container);
        notice_more = rootView.findViewById(R.id.notice_more);
        Context context = getContext();

        noticeList = new ArrayList<>();
        if(isNetworkConnected(context) == true) {
            Notice_Atask notice = new Notice_Atask(noticeList);
            Mainfragment_Recipe_Atask atask = new Mainfragment_Recipe_Atask();
            IrdntLifeEndNumATask endNum = new IrdntLifeEndNumATask(getUserId());
            IrdntNewContentNumATask newNum = new IrdntNewContentNumATask(getUserId());
            try {
                notice.execute().get();
                atask.execute().get();
                endLifeNum = endNum.execute().get().trim();
                newContentNum = newNum.execute().get().trim();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //메인 프래그먼트에서 추천 레시피 세팅
        //recipe_id.setText(main_recipe_item.getRecipe_id());
        recipe_nm_ko.setText(main_recipe_item.getRecipe_nm_ko());
        sumry.setText(main_recipe_item.getSumry());
        Glide.with(rootView).load(main_recipe_item.getImg_url()).into(img_url);
        calorie.setText(main_recipe_item.getCalorie());
        qnt.setText(main_recipe_item.getQnt());
        nation_nm.setText(main_recipe_item.getNation_nm());

        if(endLifeNum.equals("0")) shelfLifeAlertBanner.setVisibility(View.GONE);
        else shelfLifeAlertBanner.setVisibility(View.VISIBLE);

        if(newContentNum.equals("0")) newContentBenner.setVisibility(View.GONE);
        else newContentBenner.setVisibility(View.VISIBLE);

        shelfLifeAlertText
                .setText("유통기한이 임박한 재료 '"+endLifeNum+"'개가 냉장고 안에 있습니다!");
        newContentText
                .setText("확인이 필요한 내용물 '"+newContentNum+"'개가 냉장고 안에 있습니다!");

        //유통기한 알림 배너 클릭 시 유통기한별 정렬 재료 리스트로 이동
        shelfLifeAlertBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
            }
        });
        newContentBenner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
            }
        });

        //레시피 추천 배너 클릭 시 해당 요리 레시피의 상세 정보로 이동
        recipeRecommandBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(rootView.getContext(), "레시피 추천 배너", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), RecipeSubActivity.class);
                intent.putExtra("recipe_id", main_recipe_item.getRecipe_id());
                intent.putExtra("recipe_nm_ko", main_recipe_item.getRecipe_nm_ko());
                intent.putExtra("sumry", main_recipe_item.getSumry());
                intent.putExtra("nation_nm", main_recipe_item.getNation_nm());
                intent.putExtra("ty_nm", main_recipe_item.getTy_nm());
                intent.putExtra("cooking_time", main_recipe_item.getCooking_time());
                intent.putExtra("calorie", main_recipe_item.getCalorie());
                intent.putExtra("qnt", main_recipe_item.getQnt());
                intent.putExtra("level_nm", main_recipe_item.getLevel_nm());
                intent.putExtra("irdnt_code", main_recipe_item.getIrdnt_code());
                startActivity(intent);
            }
        });

//        manageTipBanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.board);
//            }
//        });

        notice_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.board);
            }
        });

        int i = 0;
        for(BoardDTO dto : noticeList){
            if(i < 5){
                set_notice_list(dto, i);
            }
            i++;
        }

        //2초안에 뒤로가기 두번 누르면 앱 종료
        new CommonMethod().mainFragmentBackPress((MainActivity)getActivity(), requireActivity(), this, context);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void set_notice_list(BoardDTO dto, int position){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        final TextView view = new TextView(getContext());
        view.setText(dto.getBoard_title());
        view.setTextSize(20);
        view.setLayoutParams(params);
        view.setMaxLines(1);
        view.setEllipsize(TextUtils.TruncateAt.END);
        view.setId(position + 1);
        int board_no = dto.getBoard_no();
        
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("board_no", board_no);
                ((MainActivity)getActivity()).gotoNotice(new BoardFragment(), bundle);
            }
        });

        notice_container.addView(view);
    }

    //회원 아이디 가져오기
    public long getUserId(){
        extra = this.getArguments();
        if (extra != null) {
            extra = getArguments();
            return extra.getLong("user_id");
        }else {
            return 0;
        }
    }
}