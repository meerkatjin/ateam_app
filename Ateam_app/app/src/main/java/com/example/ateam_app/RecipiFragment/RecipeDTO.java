package com.example.ateam_app.RecipiFragment;

public class RecipeDTO {
    String recipe_nm_ko;     //레시피 제목
    String sumry;            //간략소개
    String level_nm;         //난이도
    int img_url;             //대표 이미지 주소

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
}
