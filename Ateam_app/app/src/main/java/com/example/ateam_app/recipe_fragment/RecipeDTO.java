package com.example.ateam_app.recipe_fragment;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RecipeDTO  {
    int recipe_id;
    String recipe_nm_ko;     //레시피 제목
    String sumry;            //간략소개
    String nation_nm;
    String ty_nm;
    String cooking_time;
    String calorie;
    String qnt;
    String level_nm;
    String irdnt_code;
    String img_url;

    public RecipeDTO(int recipe_id, String recipe_nm_ko, String sumry, String nation_nm, String ty_nm, String cooking_time, String calorie, String qnt, String level_nm, String irdnt_code, String img_url) {
        this.recipe_id = recipe_id;
        this.recipe_nm_ko = recipe_nm_ko;
        this.sumry = sumry;
        this.nation_nm = nation_nm;
        this.ty_nm = ty_nm;
        this.cooking_time = cooking_time;
        this.calorie = calorie;
        this.qnt = qnt;
        this.level_nm = level_nm;
        this.irdnt_code = irdnt_code;
        this.img_url = img_url;
    }



    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_nm_ko() {
        return recipe_nm_ko;
    }

    public void setRecipe_nm_ko(String recipe_nm_ko) {
        this.recipe_nm_ko = recipe_nm_ko;
    }

    public String getSumry() {
        return sumry;
    }

    public void setSumry(String sumry) {
        this.sumry = sumry;
    }

    public String getNation_nm() {
        return nation_nm;
    }

    public void setNation_nm(String nation_nm) {
        this.nation_nm = nation_nm;
    }

    public String getTy_nm() {
        return ty_nm;
    }

    public void setTy_nm(String ty_nm) {
        this.ty_nm = ty_nm;
    }

    public String getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(String cooking_time) {
        this.cooking_time = cooking_time;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public String getLevel_nm() {
        return level_nm;
    }

    public void setLevel_nm(String level_nm) {
        this.level_nm = level_nm;
    }

    public String getIrdnt_code() {
        return irdnt_code;
    }

    public void setIrdnt_code(String irdnt_code) {
        this.irdnt_code = irdnt_code;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
