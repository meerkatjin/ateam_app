package com.example.ateam_app.common;

import android.content.SharedPreferences;

import com.example.ateam_app.user_pakage.dto.UserDTO;

public class SaveSharedPreference {
    //사용할땐 getSharedPreferences("키값?", Activity.MODE_PRIVATE) 을 pref 쪽에 넣으면됨
    //키값 : "userData" 로그인시 유저의 데이터
    //키값 : "autoLogin" 자동로그인 체크 여부

    //유저 데이터 세팅
    public static void setUserData(SharedPreferences pref, UserDTO dto) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("user_id", dto.getUser_id());
        editor.putString("user_email", dto.getUser_email());
        editor.putString("user_pw", dto.getUser_pw());
        editor.putString("user_nm", dto.getUser_nm());
        editor.putString("user_addr", dto.getUser_addr());
        editor.putString("user_pro_img", dto.getUser_pro_img());
        editor.putString("user_phone_no", dto.getUser_phone_no());
        editor.putString("user_grade", dto.getUser_grade());
        editor.putString("user_type", dto.getUser_type());
        editor.commit();
    }

    //유저 데이터 가져오기
    public static UserDTO getUserData(SharedPreferences pref) {
        UserDTO dto = null;
        if (pref != null) {
            dto = new UserDTO();
            dto.setUser_id(pref.getLong("user_id", 0));
            dto.setUser_email(pref.getString("user_email", null));
            dto.setUser_pw(pref.getString("user_pw", null));
            dto.setUser_nm(pref.getString("user_nm", null));
            dto.setUser_addr(pref.getString("user_addr",null));
            dto.setUser_pro_img(pref.getString("user_pro_img",null));
            dto.setUser_phone_no(pref.getString("user_phone_no",null));
            dto.setUser_grade(pref.getString("user_grade",null));
            dto.setUser_type(pref.getString("user_type",null));
        }
        return dto;
    }

    //유저 데이터 클리어
    public static void clearUserData(SharedPreferences pref){
        pref.edit().clear().commit();
    }

    //자동로그인 세팅
    public static void setAutoLogin(SharedPreferences pref, boolean checkAuto){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("autoLogin", checkAuto);
        editor.commit();
    }

    //자동로그인 체크여부 가져오기
    public static boolean getAutoLogin(SharedPreferences pref){
        return pref.getBoolean("autoLogin", false);
    }
}