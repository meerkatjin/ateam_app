package com.example.ateam_app.irdnt_list_package;

public class IrdntListDTO {
    String content_nm, content_ty, shelf_life_end;
    int content_list_id;

    public IrdntListDTO() {}

    public IrdntListDTO(int content_list_id, String content_nm, String content_ty, String shelf_life_end) {
        this.content_list_id = content_list_id;
        this.content_nm = content_nm;
        this.content_ty = content_ty;
        this.shelf_life_end = shelf_life_end;
    }

    public String getContent_nm() {
        return content_nm;
    }

    public void setContent_nm(String content_nm) {
        this.content_nm = content_nm;
    }

    public String getContent_ty() {
        return content_ty;
    }

    public void setContent_ty(String content_ty) {
        this.content_ty = content_ty;
    }

    public String getShelf_life_end() {
        return shelf_life_end;
    }

    public void setShelf_life_end(String shelf_life_end) {
        this.shelf_life_end = shelf_life_end;
    }

    public int getContent_list_id() {
        return content_list_id;
    }

    public void setContent_list_id(int content_list_id) {
        this.content_list_id = content_list_id;
    }
}
