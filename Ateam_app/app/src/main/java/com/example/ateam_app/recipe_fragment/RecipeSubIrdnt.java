package com.example.ateam_app.recipe_fragment;

public class RecipeSubIrdnt {
    private int recipe_id;
    private String irdnt_nm;
    private String irdnt_cpcty;
    private String irdnt_ty_nm;

    public RecipeSubIrdnt() {}

    public RecipeSubIrdnt(int recipe_id, String irdnt_nm, String irdnt_cpcty, String irdnt_ty_nm) {
        this.recipe_id = recipe_id;
        this.irdnt_nm = irdnt_nm;
        this.irdnt_cpcty = irdnt_cpcty;
        this.irdnt_ty_nm = irdnt_ty_nm;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getIrdnt_nm() {
        return irdnt_nm;
    }

    public void setIrdnt_nm(String irdnt_nm) {
        this.irdnt_nm = irdnt_nm;
    }

    public String getIrdnt_cpcty() {
        return irdnt_cpcty;
    }

    public void setIrdnt_cpcty(String irdnt_cpcty) {
        this.irdnt_cpcty = irdnt_cpcty;
    }

    public String getIrdnt_ty_nm() {
        return irdnt_ty_nm;
    }

    public void setIrdnt_ty_nm(String irdnt_ty_nm) {
        this.irdnt_ty_nm = irdnt_ty_nm;
    }
}
