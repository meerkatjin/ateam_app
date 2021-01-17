package com.example.ateam_app.recipe_fragment;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RecipeDTO extends ArrayList<RecipeDTO> {

    String recipe_nm_ko;     //레시피 제목
    String sumry;            //간략소개
    String level_nm;         //난이도
    int img_url;             //대표 이미지 주소
    int recipe_id;           //레시피 코드
    String NATION_NM;
    String TY_NM;
    String COOKING_TIME;
    String CALORIE;
    String QNT;
    String IRDNT_CODE;
    
    //레시피 정보 가져오기
    public RecipeDTO(int recipe_id, String img_url, String sumry, String nation_nm, String ty_nm, String cooking_time, String calorie, String qnt, String level_nm, String irdnt_code) {
    }

    public String getNATION_NM() {
        return NATION_NM;
    }

    public void setNATION_NM(String NATION_NM) {
        this.NATION_NM = NATION_NM;
    }

    public String getTY_NM() {
        return TY_NM;
    }

    public void setTY_NM(String TY_NM) {
        this.TY_NM = TY_NM;
    }

    public String getCOOKING_TIME() {
        return COOKING_TIME;
    }

    public void setCOOKING_TIME(String COOKING_TIME) {
        this.COOKING_TIME = COOKING_TIME;
    }

    public String getCALORIE() {
        return CALORIE;
    }

    public void setCALORIE(String CALORIE) {
        this.CALORIE = CALORIE;
    }

    public String getQNT() {
        return QNT;
    }

    public void setQNT(String QNT) {
        this.QNT = QNT;
    }

    public String getIRDNT_CODE() {
        return IRDNT_CODE;
    }

    public void setIRDNT_CODE(String IRDNT_CODE) {
        this.IRDNT_CODE = IRDNT_CODE;
    }

    LinearLayout recipe_item_layout;

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public RecipeDTO(String recipe_nm_ko, String sumry, String level_nm, int img_url) {
        this.recipe_nm_ko = recipe_nm_ko;
        this.sumry = sumry;
        this.level_nm = level_nm;
        this.img_url = img_url;
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

    public String getLevel_nm() {
        return level_nm;
    }

    public void setLevel_nm(String level_nm) {
        this.level_nm = level_nm;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public LinearLayout getRecipe_item_layout() {
        return recipe_item_layout;
    }

    public void setRecipe_item_layout(LinearLayout recipe_item_layout) {
        this.recipe_item_layout = recipe_item_layout;
    }

    @NonNull
    @Override
    public Stream<RecipeDTO> stream() {
        return null;
    }
}
