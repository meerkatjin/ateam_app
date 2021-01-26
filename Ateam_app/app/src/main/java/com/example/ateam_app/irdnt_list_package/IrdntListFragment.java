package com.example.ateam_app.irdnt_list_package;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.example.ateam_app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class IrdntListFragment extends Fragment {
    private static final String TAG = "IrdntListFragment";

    String state;
    IrdntListAdapter adapter;
    ArrayList<IrdntListDTO> items;
    RecyclerView irdntRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TabLayout irdnt_sort_tab, irdnt_sort_type_tab;
    Button btnInputTest, btnIrdntInsert, btnIrdntCancel;
    FrameLayout irdnt_input_frame;
    EditText content_nm;
    IrdntListView irdntListView;
    ProgressDialog progressDialog;
    String content_ty;

    Bundle extra;
    Long user_id;
    int tabSelected = 11;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_list, container, false);
        Context context = rootView.getContext();
        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items);

        extra = this.getArguments();
        if (extra != null) {
            extra = getArguments();
            user_id = extra.getLong("user_id");
        }

        //재료 탭
        irdnt_sort_tab = rootView.findViewById(R.id.irdnt_sort_tab);
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("종류별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("유통기한별"));
        irdnt_sort_tab.addTab(irdnt_sort_tab.newTab().setText("이름별"));
        //재료 탭(종류 세부)
        irdnt_sort_type_tab = rootView.findViewById(R.id.irdnt_sort_type_tab);
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("고기"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("수산물"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("채소"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("과일"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("유제품"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("곡류"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("조미료"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("음료/기타"));

        //DB에 있는 재료 리스트 가져오기
        irdntRecyclerView = rootView.findViewById(R.id.irdntRecyclerView);
        layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        irdntRecyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();
        adapter = new IrdntListAdapter(context, items);
        irdntRecyclerView.setAdapter(adapter);

        //재료 탭 선택 리스너
        irdnt_sort_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                //종류별 탭 선택 시 세부 탭 보여주기
                if (position == 0) {
                    irdnt_sort_type_tab.setVisibility(View.VISIBLE);


                    irdnt_sort_type_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            int position2 = tab.getPosition();

                            if (position2 == 0) {   //고기
                                tabSelected = 11;
                                content_ty = "고기";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 1) {    //수산물
                                tabSelected = 12;
                                content_ty = "수산물";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 2) {    //채소
                                tabSelected = 13;
                                content_ty = "채소";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 3) {    //과일
                                tabSelected = 14;
                                content_ty = "과일";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 4) {    //유제품
                                tabSelected = 15;
                                content_ty = "유제품";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 5) {    //곡류
                                tabSelected = 16;
                                content_ty = "곡류";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 6) {    //조미료
                                tabSelected = 17;
                                content_ty = "조미료";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            } else if (position2 == 7) {    //음료/기타
                                tabSelected = 18;
                                content_ty = "음료/기타";
                                if(isNetworkConnected(context) == true) {
                                    irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected, content_ty);
                                    irdntListView.execute();
                                }
                            }

                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                    //유통기한별 탭
                } else if (position == 1) {
                    tabSelected = 2;
                    irdnt_sort_type_tab.setVisibility(View.GONE);
                    if(isNetworkConnected(context) == true) {
                        irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected);
                        irdntListView.execute();
                    }

                    //이름별 탭
                } else if (position == 2) {
                    tabSelected = 3;
                    irdnt_sort_type_tab.setVisibility(View.GONE);
                    if(isNetworkConnected(context) == true) {
                        irdntListView = new IrdntListView(items, adapter, progressDialog, user_id, tabSelected);
                        irdntListView.execute();
                    }

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
                    state = insert.execute().get().trim();
                    Log.d("main:state : ", state);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (state.equals(name)) {
                    Toast.makeText(getActivity().getApplicationContext(), "추가되었습니다!", Toast.LENGTH_SHORT).show();
                    irdnt_input_frame.setVisibility(View.GONE);
                    btnInputTest.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "추가 실패했습니다", Toast.LENGTH_SHORT).show();
                    irdnt_input_frame.setVisibility(View.GONE);
                    btnInputTest.setVisibility(View.VISIBLE);
                }
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

}//class
