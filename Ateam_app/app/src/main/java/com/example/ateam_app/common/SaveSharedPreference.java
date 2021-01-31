package com.example.ateam_app.common;

import android.content.SharedPreferences;

import com.example.ateam_app.user_pakage.dto.UserDTO;

public class SaveSharedPreference {

    //사용할땐 getSharedPreferences("키값?", Activity.MODE_PRIVATE) 을 pref 쪽에 넣으면됨
    public static void setUserData(SharedPreferences pref, UserDTO dto) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("user_id", dto.getUser_id());
        editor.putString("user_email", dto.getUser_email());
        editor.putString("user_pw", dto.getUser_pw());
        editor.putString("user_addr", dto.getUser_addr());
        editor.putString("user_pro_img", dto.getUser_pro_img());
        editor.putString("user_grade", dto.getUser_grade());
        editor.putString("user_type", dto.getUser_type());
        editor.commit();
    }

    public static UserDTO getUserData(SharedPreferences pref) {
        UserDTO dto = null;
        if (pref != null) {
            dto = new UserDTO();
            dto.setUser_id(pref.getLong("user_id", 0));
            dto.setUser_email(pref.getString("user_email", ""));
            dto.setUser_pw(pref.getString("user_pw", null));
            dto.setUser_addr(pref.getString("user_addr",null));
            dto.setUser_pro_img(pref.getString("user_pro_img",""));
            dto.setUser_grade(pref.getString("user_grade","1"));
            dto.setUser_type(pref.getString("user_type","nomal"));
        }
        return dto;
    }

    public static void clearUserData(SharedPreferences pref){
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}