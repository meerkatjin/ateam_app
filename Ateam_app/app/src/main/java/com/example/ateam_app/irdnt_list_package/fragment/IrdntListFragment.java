package com.example.ateam_app.irdnt_list_package.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemCheckListener;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemClickListener;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemLongClickListener;
import com.example.ateam_app.irdnt_list_package.atask.IrdntConfirm;
import com.example.ateam_app.irdnt_list_package.atask.IrdntLifeEndListATask;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListDelete;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListInsert;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListView;
import com.example.ateam_app.irdnt_list_package.atask.IrdntNewContentListATask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class IrdntListFragment extends Fragment {

    String state_insert, state_delete;
    IrdntListAdapter adapter;
    ArrayList<Long> irdnt_ids, new_ids;
    ArrayList<IrdntListDTO> items;
    RecyclerView irdntRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TabLayout irdnt_sort_tab, irdnt_sort_type_tab;
    Button btnIrdntInsert, btnIrdntCancel;
    FloatingActionButton btnInputTest;
    FrameLayout irdnt_input_frame;
    EditText content_nm;
    IrdntListView irdntListView;
    IrdntLifeEndListATask irdntLifeEndListATask;
    IrdntNewContentListATask irdntNewContentListATask;
    ProgressDialog progressDialog;
    Toolbar irdnt_check_tool;

    CommonMethod common = new CommonMethod();
    Bundle extra;
    Long user_id;
    int tabSelected = 2;
    boolean checkMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);
        Context context = rootView.getContext();

        //회원 아이디 가져오기
        extra = this.getArguments();
        if (extra != null) {
            user_id = extra.getLong("user_id");
        }

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

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items, irdnt_ids, new_ids, checkMode);

        //DB에 있는 재료 리스트 가져오기
        irdntRecyclerView = rootView.findViewById(R.id.irdntRecyclerView);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        irdntRecyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items, irdnt_ids, new_ids, checkMode);
        irdntRecyclerView.setAdapter(adapter);

        if(isNetworkConnected(context)) {
            irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected);
            irdntListView.execute();
        }
//        setSuport

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
                for(int i = 0; i < adapter.getItemCount(); i++){
                    IrdntListDTO dto = adapter.getItem(i);
                    if(dto.isCheck()) list.add(dto);
                }
                switch (item.getItemId()){
                    case R.id.delete_irdnt:
                        common.dialogMethod(context, "삭제 안내", "선택한 항목을 삭제 하시겠습니까?",
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
                                        common.replace(getFragmentManager().beginTransaction(), IrdntListFragment.this);
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
                        common.dialogMethod(context, "확인 안내", "선택한 항목을 확인 처리 하시겠습니까?",
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
                                        common.replace(getFragmentManager().beginTransaction(), IrdntListFragment.this);
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

        //재료 탭
        irdnt_sort_tab = rootView.findViewById(R.id.irdnt_sort_tab);

        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("유통기한별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("종류별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("이름별"));
        //재료 탭(종류 세부)
        irdnt_sort_type_tab = rootView.findViewById(R.id.irdnt_sort_type_tab);

        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("고기").setIcon(R.drawable.meat));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("수산물").setIcon(R.drawable.fish));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("채소").setIcon(R.drawable.vegetable));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("과일").setIcon(R.drawable.fruit));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("유제품").setIcon(R.drawable.milk));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("곡류").setIcon(R.drawable.rice));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("조미료/주류").setIcon(R.drawable.beer));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("음료/기타").setIcon(R.drawable.can));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("미분류").setIcon(R.drawable.unkown));

        //재료 탭 선택 리스너
        irdnt_sort_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                //유통기한별 탭
                if (position == 0) {
                    tabChange(context, 2);
                    //종류별 탭 선택 시 세부 탭 보여주기
                } else if (position == 1) {
                    irdnt_sort_type_tab.setVisibility(View.VISIBLE);
                    tabChange(context, 11, "고기");
                    irdnt_sort_type_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            int position2 = tab.getPosition();

                            if (position2 == 0) {   //고기
                                tabChange(context, 11, "고기");
                            } else if (position2 == 1) {    //수산물
                                tabChange(context, 12, "수산물");
                            } else if (position2 == 2) {    //채소
                                tabChange(context, 13, "채소");
                            } else if (position2 == 3) {    //과일
                                tabChange(context, 14, "과일");
                            } else if (position2 == 4) {    //유제품
                                tabChange(context, 15, "유제품");
                            } else if (position2 == 5) {    //곡류
                                tabChange(context, 15, "곡류");
                            } else if (position2 == 6) {    //조미료/주류
                                tabChange(context, 17, "조미료/주류");
                            } else if (position2 == 7) {    //음료/기타
                                tabChange(context, 18, "음료/기타");
                            } else if (position2 == 8) {    //미분류
                                tabChange(context, 19, "미분류");
                            }
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                    //이름별 탭
                } else if (position == 2) {
                    tabChange(context,3);
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //재료 추가 버튼 (임시, 실제는 IoT로 구현)
        btnInputTest = rootView.findViewById(R.id.btnInputTest);
        btnInputTest.bringToFront();
        irdnt_input_frame = rootView.findViewById(R.id.irdnt_input_frame);
        btnInputTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irdnt_input_frame.setVisibility(View.VISIBLE);
                btnInputTest.setVisibility(View.GONE);
            }
        });

        //재료추가 메뉴 (임시)
        btnIrdntInsert = rootView.findViewById(R.id.btnIrdntInsert);
        btnIrdntCancel = rootView.findViewById(R.id.btnIrdntCancel);
        content_nm = rootView.findViewById(R.id.content_nm);
        //추가 버튼
        btnIrdntInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //빈칸일 경우 Toast로 알려주고 입력
                if (content_nm.getText().toString().trim().equals("")) {
                    Toast.makeText(rootView.getContext(), "재료이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    content_nm.requestFocus();
                    //값을 입력했을 때 추가 메소드 실행
                }  else {
                    String name = content_nm.getText().toString().trim();

                    irdntInsertConfirm(name, user_id);
                }
            }
        });

        //추가 취소 버튼
        btnIrdntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irdnt_input_frame.setVisibility(View.GONE);
                btnInputTest.setVisibility(View.VISIBLE);
            }
        });

        //재료 클릭시 디테일로 가게됨
        adapter.setOnItemClickListener(new OnIrdntItemClickListener() {
            @Override
            public void onItemClick(IrdntListAdapter.ViewHolder holder, View view, int position) {
                IrdntListDTO dto = adapter.getItem(position);
                if(checkMode && holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);
                }else if(checkMode && !holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(true);
                }else{
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("IrdntListDTO", dto);
                    bundle.putLong("user_id", user_id);

                    ((MainActivity)getActivity()).replaceFragment(new IrdntDetailFragment(), bundle);
                }
            }
        });

        //롱클릭시 이벤트
        adapter.setOnItemLongClickListener(new OnIrdntItemLongClickListener() {
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
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        if(checkMode){
            irdnt_check_tool.setVisibility(View.VISIBLE);
            irdnt_sort_tab.setVisibility(View.GONE);
        } else {
            irdnt_check_tool.setVisibility(View.GONE);
            irdnt_sort_tab.setVisibility(View.VISIBLE);
        }

        return rootView;
    }//onCreateView()

    //재료 추가 메소드 -> IrdntListInsert로 이동
    private void irdntInsertConfirm(String name, Long user_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("재료 추가");
        builder.setMessage("재료를 추가하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IrdntListInsert insert = new IrdntListInsert(name, user_id);
                try {
                    state_insert = insert.execute().get().trim();
                    Log.d("main:state : ", state_insert);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (state_insert.equals(name)) {
                    Toast.makeText(getActivity().getApplicationContext(), "추가되었습니다!", Toast.LENGTH_SHORT).show();
                    irdnt_input_frame.setVisibility(View.GONE);
                    btnInputTest.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "추가 실패했습니다", Toast.LENGTH_SHORT).show();
                    irdnt_input_frame.setVisibility(View.GONE);
                    btnInputTest.setVisibility(View.VISIBLE);
                }

                common.replace(getFragmentManager().beginTransaction(), IrdntListFragment.this);
                adapter.notifyDataSetChanged(); // adapter 갱신
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(), "추가 취소했습니다", Toast.LENGTH_SHORT).show();
                irdnt_input_frame.setVisibility(View.GONE);
                btnInputTest.setVisibility(View.VISIBLE);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static IrdntListFragment newInstance() {
        return new IrdntListFragment();
    }

    public void tabChange(Context context, int tabSelected, String content_ty){
        if(isNetworkConnected(context) == true) {
            irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
            irdntListView.execute();
        }
    }

    public void tabChange(Context context, int tabSelected){
        irdnt_sort_type_tab.setVisibility(View.GONE);
        if(isNetworkConnected(context) == true) {
            if(isNetworkConnected(context) == true) {
                irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected);
                irdntListView.execute();
            }
        }
    }

    public boolean setCheckMode(Context context,boolean checkMode){
        adapter = new IrdntListAdapter(context, items, irdnt_ids, new_ids, checkMode);
        common.replace(getFragmentManager().beginTransaction(), IrdntListFragment.this);
        adapter.notifyDataSetChanged(); // adapter 갱신
        return checkMode;
    }
}//class
