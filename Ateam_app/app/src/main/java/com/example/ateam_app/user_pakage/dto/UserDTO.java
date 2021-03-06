package com.example.ateam_app.user_pakage.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private long user_id;
    private String user_email,
                user_pw,
                user_nm,
                user_addr,
                user_pro_img,
                user_phone_no,
                user_grade,
                user_type;

    public UserDTO() { }

    //카카오 로그인정보 가져올때
    public UserDTO(long user_id, String user_email, String user_nm, String user_pro_img, String user_grade, String user_type){
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_nm = user_nm;
        this.user_pro_img = user_pro_img;
        this.user_grade = user_grade;
        this.user_type = user_type;
    }

    //로그인 후 모든 정보를 가져올때
    public UserDTO(long user_id, String user_email, String user_pw, String user_nm, String user_addr, String user_pro_img, String user_phone_no, String user_grade, String user_type) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
        this.user_addr = user_addr;
        this.user_pro_img = user_pro_img;
        this.user_phone_no = user_phone_no;
        this.user_grade = user_grade;
        this.user_type = user_type;
    }

    //데이터베이스에 회원가입 정보를 저장할때
    public UserDTO(String user_email, String user_pw, String user_nm) {
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getUser_nm() {
        return user_nm;
    }

    public void setUser_nm(String user_nm) {
        this.user_nm = user_nm;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getUser_pro_img() {
        return user_pro_img;
    }

    public void setUser_pro_img(String user_pro_img) {
        this.user_pro_img = user_pro_img;
    }

    public String getUser_phone_no() {
        return user_phone_no;
    }

    public void setUser_phone_no(String user_phone_no) {
        this.user_phone_no = user_phone_no;
    }

    public String getUser_grade() {
        return user_grade;
    }

    public void setUser_grade(String user_grade) {
        this.user_grade = user_grade;
    }
}
