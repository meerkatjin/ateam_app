package com.example.ateam_app;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UserInfoChangeFragment extends Fragment {

    MainActivity activity;
    EditText user_email, user_pw, user_pw_cf,user_nm, user_addr, user_phone_no;
    Button btnInfoChange, btnInfoChangeCancel, btnImgUpload, btnCameraUpload;
    ImageView user_pro_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        user_email = rootView.findViewById(R.id.user_email);
        user_pw = rootView.findViewById(R.id.user_pw);
        user_pw_cf = rootView.findViewById(R.id.user_pw_cf);
        user_nm = rootView.findViewById(R.id.user_nm);
        user_addr = rootView.findViewById(R.id.user_addr);
        user_phone_no = rootView.findViewById(R.id.user_phone_no);
        user_pro_img = rootView.findViewById(R.id.user_pro_img);

        btnInfoChange = rootView.findViewById(R.id.btnInfoChange);
        btnInfoChangeCancel = rootView.findViewById(R.id.btnInfoChageCancle);
        btnImgUpload = rootView.findViewById(R.id.btnImgUpload);
        btnCameraUpload = rootView.findViewById(R.id.btnCameraUpload);

        //회원정보수정 눌렀을 때
        btnInfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeMessage();
            }
        });

        //회원정보수정 취소 눌렀을 때
        btnInfoChangeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelMessage();
            }
        });

        //이미지 올리기 버튼 눌렀을 때 카메라 앨범 페이지로
        btnImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //사진찍기 버튼 눌렀을 때
        btnCameraUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    //수정눌렀을 때 대화상자 팝업
    private void showChangeMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("안내");
        builder.setMessage("정말 수정하시겠습니까???");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        //예 버튼 ,,일단 토스트만 띄움
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "회원정보가 수정되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });

        //아니오 버튼
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //회원정보란에 정보그대로

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //취소눌렀을 때 대화상자 팝업
    private void showCancelMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("안내");
        builder.setMessage("정말 취소하시겠습니까???");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        //예 버튼
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        //아니오 버튼
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //회원정보란에 정보그대로
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}