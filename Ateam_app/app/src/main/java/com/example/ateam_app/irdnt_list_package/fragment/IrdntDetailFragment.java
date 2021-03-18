package com.example.ateam_app.irdnt_list_package.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.irdnt_list_package.atask.IrdntConfirm;
import com.example.ateam_app.irdnt_list_package.atask.IrdntListDelete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.ateam_app.common.CommonMethod.ipConfig;
import static com.example.ateam_app.common.CommonMethod.isNetworkConnected;

public class IrdntDetailFragment extends Fragment {
    IrdntListDTO dto;
    ImageView irdnt_image;
    EditText content_nm, shelf_life_start, shelf_life_end;
    Spinner content_ty;
    Button confirm, delete, cancel;

    CommonMethod common;
    Bundle extra;
    long user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_irdnt_detail, container, false);
        Context context = rootView.getContext();

        common = new CommonMethod();

        irdnt_image = rootView.findViewById(R.id.irdnt_image);
        content_nm = rootView.findViewById(R.id.content_nm);
        content_ty = rootView.findViewById(R.id.content_ty);
        shelf_life_start = rootView.findViewById(R.id.shelf_life_start);
        shelf_life_end = rootView.findViewById(R.id.shelf_life_end);
        confirm = rootView.findViewById(R.id.confirm);
        delete = rootView.findViewById(R.id.delete);
        cancel = rootView.findViewById(R.id.cancel);

        //내용물 리스트 프래그먼트에서 값 가져오기
        extra = this.getArguments();
        if(extra != null){
            extra = getArguments();
            dto = (IrdntListDTO) extra.getSerializable("IrdntListDTO");
            user_id = extra.getLong("user_id");
        }

        String[] spiner_data = getResources().getStringArray(R.array.irdnt_ty);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.irdnt_ty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        content_ty.setAdapter(adapter);

        String image = ipConfig + "/ateamiot/resources/" + dto.getImage_path();
        Glide.with(this).load(image).error(R.drawable.no_image).into(irdnt_image);
        content_nm.setText(dto.getContent_nm());
        if(dto.getContent_ty().trim().equals("")){  //내용물 타입이 없을때
            content_ty.setSelection(8);
        }else{
            int i = 0;
            for(String data : spiner_data){
                if(data.equals(dto.getContent_ty())){
                    content_ty.setSelection(i);
                    break;
                }
                i++;
            }
        }
        shelf_life_start.setText(dto.getShelf_life_start());
        if(dto.getShelf_life_end().trim().equals("")){  //유통기한 종료일이 없을때
            shelf_life_end.setText("정해지지 않음");
        }else{
            shelf_life_end.setText(dto.getShelf_life_end());
        }

        shelf_life_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick(context, shelf_life_start.getText().toString(), R.id.shelf_life_start, shelf_life_start);
            }
        });

        shelf_life_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick(context, shelf_life_end.getText().toString(), R.id.shelf_life_end, shelf_life_end);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.dialogMethod(context, "확인 안내", "해당 항목을 확인/수정 하시겠습니까?", "예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String check = "";
                                dto.setContent_nm(content_nm.getText().toString());
                                dto.setContent_ty((String) content_ty.getSelectedItem());
                                dto.setShelf_life_start(shelf_life_start.getText().toString());
                                dto.setShelf_life_end(shelf_life_end.getText().toString());
                                if(isNetworkConnected(context) == true) {
                                    IrdntConfirm irdntConfirm = new IrdntConfirm(dto);
                                    try {
                                        check = irdntConfirm.execute().get().trim();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(!check.equals("1")){
                                    Toast.makeText(context, "다시 시도해주십시오", Toast.LENGTH_SHORT).show();
                                }else{
                                    ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
                                }
                            }
                        }, "아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.dialogMethod(context, "삭제 안내", "해당 항목을 삭제 하시겠습니까?",
                        "예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(isNetworkConnected(context) == true) {
                                    IrdntListDelete delete = new IrdntListDelete(user_id, dto.getContent_list_id());
                                    try {
                                        delete.execute().get().trim();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
                            }
                        }, "아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.dialogMethod(context, "취소 안내", "작업을 취소 하시겠습니까?", "예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.tabIrdntList);
                            }
                        }, "아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });

        new CommonMethod().fragmentBackPress((MainActivity)getActivity(), requireActivity(), this, R.id.tabIrdntList);

        return rootView;
    }

    void datePick(Context context, String set_date, int property, EditText editText){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar targetCalendar = Calendar.getInstance(); //calender 타입으로 변환된 값을 받을것
            Calendar startCalender = Calendar.getInstance();
            Calendar minDate = Calendar.getInstance();
            Calendar maxDate = Calendar.getInstance();

            Date date = new Date();
            Date start = new Date();
            Date end = new Date();
            try {
                date = dateFormat.parse(set_date);
                start = dateFormat.parse(dto.getShelf_life_start());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            targetCalendar.setTime(date);
            startCalender.setTime(start);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editText.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                }
            },
                    targetCalendar.get(Calendar.YEAR),
                    targetCalendar.get(Calendar.MONTH),
                    targetCalendar.get(Calendar.DAY_OF_MONTH)
            );

            if(property == R.id.shelf_life_start){
                maxDate.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),startCalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
            } else if(property == R.id.shelf_life_end){
                minDate.set(startCalender.get(Calendar.YEAR),startCalender.get(Calendar.MONTH),startCalender.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
            }

            datePickerDialog.show();
        }
    }
}