package com.example.ateam_app.irdnt_list_package;

import java.io.Serializable;

public class IrdntListDTO implements Serializable {
    String content_nm, content_ty, shelf_life_start, shelf_life_end;
    int content_list_id;
    String image_name, image_path;

    public IrdntListDTO() {}

    public IrdntListDTO(int content_list_id, String content_nm, String content_ty, String shelf_life_start, String shelf_life_end, String image_name, String image_path) {
        this.content_nm = content_nm;
        this.content_ty = content_ty;
        this.shelf_life_start = shelf_life_start;
        this.shelf_life_end = shelf_life_end;
        this.content_list_id = content_list_id;
        this.image_name = image_name;
        this.image_path = image_path;
    }

    public IrdntListDTO(int content_list_id, String content_nm, String content_ty, String shelf_life_end) {
        this.content_list_id = content_list_id;
        this.content_nm = content_nm;
        this.content_ty = content_ty;
        this.shelf_life_end = shelf_life_end;
    }

    public String getShelf_life_start() {
        return shelf_life_start;
    }

    public void setShelf_life_start(String shelf_life_start) {
        this.shelf_life_start = shelf_life_start;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
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
