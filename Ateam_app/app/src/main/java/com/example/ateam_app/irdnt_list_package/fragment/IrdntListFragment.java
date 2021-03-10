package com.example.ateam_app.irdnt_list_package.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.irdnt_list_package.OnIrdntItemClickListener;
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
    private static final String TAG = "IrdntListFragment";

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

    CommonMethod common;
    Bundle extra;
    Long user_id;
    int tabSelected = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);
        Context context = rootView.getContext();

        common = new CommonMethod();

        //회원 아이디 가져오기
        extra = this.getArguments();
        if (extra != null) {
            extra = getArguments();
            user_id = extra.getLong("user_id");
        }

        //유통기한 지난 재료의 아이디 가져오기
        if(isNetworkConnected(context) == true) {
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
        adapter = new IrdntListAdapter(context, items, irdnt_ids, new_ids);

        //DB에 있는 재료 리스트 가져오기
        irdntRecyclerView = rootView.findViewById(R.id.irdntRecyclerView);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        irdntRecyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items, irdnt_ids, new_ids);
        irdntRecyclerView.setAdapter(adapter);

        if(isNetworkConnected(context) == true) {
            irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected);
            irdntListView.execute();
        }

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
                Bundle bundle = new Bundle();

                IrdntListDTO dto = adapter.getItem(position);
                bundle.putSerializable("IrdntListDTO", dto);
                bundle.putLong("user_id", user_id);

                ((MainActivity)getActivity()).replaceFragment(new IrdntDetailFragment(), bundle);
            }
        });

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

    //프래그먼트 갱신하는 메소드 굿굿
    public void replace(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }
}//class
