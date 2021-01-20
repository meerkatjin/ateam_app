package irdnt_list_package;

import android.app.AlertDialog;
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

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.user_pakage.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class IrdntListFragment extends Fragment {
    private static final String TAG = "IrdntListFragment";

    String state;
    IrdntListAdapter adapter;
    IrdntListDTO dto;
    ArrayList<IrdntListDTO> items;
    RecyclerView irdntRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    TabLayout irdnt_sort_tab, irdnt_sort_type_tab;
    Button btnInputTest, btnIrdntInsert, btnIrdntCancel;
    FrameLayout irdnt_input_frame;
    EditText content_nm;

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
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("곡류"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("조미료"));
        irdnt_sort_type_tab.addTab(irdnt_sort_type_tab.newTab().setText("음료/기타"));

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

        dto = new IrdntListDTO("양파", "채소", "2021-01-28");
        items.add(dto);
        dto = new IrdntListDTO("돼지고기", "고기", "2021-01-22");
        items.add(dto);
        dto = new IrdntListDTO("우유", "유제품", "2021-01-19");
        items.add(dto);
        dto = new IrdntListDTO("청양고추", "채소", "2021-01-27");
        items.add(dto);
        dto = new IrdntListDTO("콜라", "음료/기타", "2021-08-02");
        items.add(dto);
        dto = new IrdntListDTO("훈제오리", "고기", "2021-04-13");
        items.add(dto);
        irdntRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        btnInputTest = rootView.findViewById(R.id.btnInputTest);
        irdnt_input_frame = rootView.findViewById(R.id.irdnt_input_frame);
        btnInputTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irdnt_input_frame.setVisibility(View.VISIBLE);
                btnInputTest.setVisibility(View.GONE);
            }
        });

        btnIrdntInsert = rootView.findViewById(R.id.btnIrdntInsert);
        btnIrdntCancel = rootView.findViewById(R.id.btnIrdntCancel);
        content_nm = rootView.findViewById(R.id.content_nm);
        btnIrdntInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content_nm.getText().toString().trim().equals("")) {
                    Toast.makeText(rootView.getContext(), "재료이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    content_nm.requestFocus();
                }  else {
                    String name = content_nm.getText().toString().trim();

                    irdntInsertConfirm(name);
                }
            }
        });

        btnIrdntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irdnt_input_frame.setVisibility(View.GONE);
                btnInputTest.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }//onCreateView()

    private void irdntInsertConfirm(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("재료 추가");
        builder.setMessage("재료를 추가하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IrdntListInsert insert = new IrdntListInsert(name);
                try {
                    state = insert.execute().get().trim();
                    Log.d("main:irdntFragment : ", state);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (state.equals("1")) {
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