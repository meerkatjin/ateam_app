package com.example.ateam_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemClickListener;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemLongClickListener;
import com.example.ateam_app.irdnt_list_package.atask.IrdntConfirm;
import com.example.ateam_app.irdnt_list_package.atask.IrdntLifeEndListATask;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListDelete;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListView;
import com.example.ateam_app.irdnt_list_package.atask.IrdntNewContentListATask;
import com.example.ateam_app.irdnt_list_package.fragment.IrdntDetailFragment;
import com.example.ateam_app.irdnt_list_package.fragment.IrdntListFragment;
import com.example.ateam_app.recipe_fragment.OnRecipeItemClickListener;
import com.example.ateam_app.recipe_fragment.RecipeAdapter;
import com.example.ateam_app.recipe_fragment.RecipeAtask;
import com.example.ateam_app.recipe_fragment.RecipeItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class SearchFragment extends Fragment {

    TabLayout searchTab;
    RecyclerView searchRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    IrdntListView irdntListView;
    ArrayList<IrdntListDTO> irdntItems;
    IrdntListAdapter irdntListAdapter;
    IrdntLifeEndListATask irdntLifeEndListATask;
    IrdntNewContentListATask irdntNewContentListATask;

    RecipeAtask recipeAtask;
    ArrayList<RecipeItem> recipeItems;
    RecipeAdapter recipeAdapter;

    Toolbar irdnt_check_tool;

    Intent intent;

    String searchText;
    Long user_id;

    ArrayList<Long> irdnt_ids, new_ids;

    boolean checkMode = false;
    int position = 0;

    Bundle extra;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search, container, false);
        Context context = rootView.getContext();

        searchTab = rootView.findViewById(R.id.searchTab);
        searchTab.addTab(searchTab.newTab().setText("내 재료"));
        searchTab.addTab(searchTab.newTab().setText("레시피"));

//        intent = getIntent();
//        searchText = intent.getStringExtra("searchText");
//        user_id = intent.getLongExtra("user_id", 0);

        extra = this.getArguments();
        if (extra != null) {
            searchText = extra.getString("searchText");
            user_id = extra.getLong("user_id");
        }

        ((MainActivity)getActivity()).bottomNavigationView.setVisibility(View.GONE);

        searchRecyclerView = rootView.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);

        irdntItems = new ArrayList<>();

        //유통기한 지난 재료의 아이디 가져오기
        if(isNetworkConnected(context)) {
            irdntLifeEndListATask = new IrdntLifeEndListATask(user_id);
            irdntNewContentListATask = new IrdntNewContentListATask(user_id);
            try {
                irdnt_ids = irdntLifeEndListATask.execute().get();
                new_ids = irdntNewContentListATask.execute().get();
            } catch (ExecutionException e) {
                e.getMessage();
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }

        irdntListAdapter = new IrdntListAdapter(context, irdntItems,irdnt_ids, new_ids, checkMode);
        searchRecyclerView.setAdapter(irdntListAdapter);

        if (isNetworkConnected(context) == true) {
            irdntListView = new IrdntListView(searchText, irdntItems, irdntListAdapter, progressDialog, user_id);
            irdntListView.execute();
        }

        //툴바만들기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            irdnt_check_tool = rootView.findViewById(R.id.irdnt_check_tool);
            irdnt_check_tool.inflateMenu(R.menu.check_toolbar);
        }

        //툴바 아이템 클릭 이벤트
        irdnt_check_tool.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ArrayList<IrdntListDTO> list = new ArrayList<>();
                for(int i = 0; i < irdntListAdapter.getItemCount(); i++){
                    IrdntListDTO dto = irdntListAdapter.getItem(i);
                    if(dto.isCheck()) list.add(dto);
                }
                switch (item.getItemId()){
                    case R.id.delete_irdnt:
                        new CommonMethod().dialogMethod(context, "삭제 안내", "선택한 항목을 삭제 하시겠습니까?",
                                "예",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for(IrdntListDTO dto : list){
                                            if(isNetworkConnected(context) == true) {
                                                IrdntListDelete delete = new IrdntListDelete(user_id, dto.getContent_list_id());
                                                try {
                                                    delete.execute().get().trim();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        checkMode = setCheckMode(context, false);
                                        new CommonMethod().replace(getFragmentManager().beginTransaction(), SearchFragment.this);
                                    }
                                }, "아니오",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        return true;
                    case R.id.confirm_irdnt:
                        new CommonMethod().dialogMethod(context, "확인 안내", "선택한 항목을 확인 처리 하시겠습니까?",
                                "예",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for(IrdntListDTO dto : list){
                                            if(isNetworkConnected(context) == true) {
                                                IrdntConfirm irdntConfirm = new IrdntConfirm(dto);
                                                try {
                                                    irdntConfirm.execute().get().trim();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        Toast.makeText(context, "설정 되었습니다.", Toast.LENGTH_SHORT).show();
                                        checkMode = setCheckMode(context, false);
                                        new CommonMethod().replace(getFragmentManager().beginTransaction(), SearchFragment.this);
                                    }
                                }, "아니오",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        return  true;
                }
                return false;
            }
        });

        searchTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();

                if (position == 0) {
                    irdntItems = new ArrayList<>();
                    irdntListAdapter = new IrdntListAdapter(context, irdntItems,irdnt_ids, new_ids, checkMode);
                    searchRecyclerView.setAdapter(irdntListAdapter);

                    if (isNetworkConnected(context)) {
                        irdntListView = new IrdntListView(searchText, irdntItems, irdntListAdapter, progressDialog, user_id);
                        irdntListView.execute();
                    }

                } else if (position == 1) {
                    recipeItems = new ArrayList<>();
                    recipeAdapter = new RecipeAdapter(context, recipeItems);
                    searchRecyclerView.setAdapter(recipeAdapter);

                    if (isNetworkConnected(context)) {
                        recipeAtask = new RecipeAtask(searchText, recipeItems, recipeAdapter, progressDialog);
                        recipeAtask.execute();
                    }

                    recipeAdapter.setOnItemClicklistener(new OnRecipeItemClickListener() {
                        @Override
                        public void onItemClick(RecipeAdapter.ViewHolder holder, View view, int position) {
                            RecipeItem item = recipeAdapter.getItem(position);
                            Intent intent = new Intent(context, RecipeSubActivity.class);
                            intent.putExtra("recipe_id", item.getRecipe_id());
                            intent.putExtra("recipe_nm_ko", item.getRecipe_nm_ko());
                            intent.putExtra("sumry", item.getSumry());
                            intent.putExtra("nation_nm", item.getNation_nm());
                            intent.putExtra("ty_nm", item.getTy_nm());
                            intent.putExtra("cooking_time", item.getCooking_time());
                            intent.putExtra("calorie", item.getCalorie());
                            intent.putExtra("qnt", item.getQnt());
                            intent.putExtra("level_nm", item.getLevel_nm());
                            intent.putExtra("irdnt_code", item.getIrdnt_code());
                            //intent.putExtra("img_url", item.getRecipe_id());

                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(checkMode){
            irdnt_check_tool.setVisibility(View.VISIBLE);
            searchTab.setVisibility(View.GONE);
        } else {
            irdnt_check_tool.setVisibility(View.GONE);
            searchTab.setVisibility(View.VISIBLE);
        }

        //재료 클릭시 디테일로 가게됨
        irdntListAdapter.setOnItemClickListener(new OnIrdntItemClickListener() {
            @Override
            public void onItemClick(IrdntListAdapter.ViewHolder holder, View view, int position) {
                IrdntListDTO dto = irdntListAdapter.getItem(position);
                dto.setUser_id(user_id);
                if(checkMode && holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                }else if(checkMode && !holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(true);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("IrdntListDTO", dto);
                    bundle.putInt("mode", 1);
                    bundle.putString("searchText", searchText);
                    ((MainActivity)getActivity()).replaceFragment(new IrdntDetailFragment(), bundle);
                }
            }
        });

        //롱클릭시 이벤트
        irdntListAdapter.setOnItemLongClickListener(new OnIrdntItemLongClickListener() {
            @Override
            public void onItemLongClick(IrdntListAdapter.ViewHolder holder, View view, int position) {
                if(!checkMode) {
                    checkMode = setCheckMode(context, true);
                }else{
                    checkMode = setCheckMode(context, false);
                }
            }
        });

        //백버튼 눌렀을때 이벤트
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(checkMode){
                    checkMode = setCheckMode(context, false);
                } else {
                    ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabMain);
                    ((MainActivity)getActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(SearchFragment.this, onBackPressedCallback);

        return rootView;
    }

    public boolean setCheckMode(Context context,boolean checkMode){
        irdntListAdapter = new IrdntListAdapter(context, irdntItems, irdnt_ids, new_ids, checkMode);
        new CommonMethod().replace(getFragmentManager().beginTransaction(), SearchFragment.this);
        irdntListAdapter.notifyDataSetChanged(); // adapter 갱신
        return checkMode;
    }
}