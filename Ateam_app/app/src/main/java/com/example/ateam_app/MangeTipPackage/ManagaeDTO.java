package com.example.ateam_app.MangeTipPackage;

public class ManagaeDTO {
    String manage_tip_sub;
    String manage_tip_context;

    int manage_tip_img;

    public ManagaeDTO(String manage_tip_sub, String manage_tip_context, int manage_tip_img) {
        this.manage_tip_sub = manage_tip_sub;
        this.manage_tip_context = manage_tip_context;
        this.manage_tip_img = manage_tip_img;
    }

    public String getManage_tip_sub() {
        return manage_tip_sub;
    }

    public void setManage_tip_sub(String manage_tip_sub) {
        this.manage_tip_sub = manage_tip_sub;
    }

    public String getManage_tip_context() {
        return manage_tip_context;
    }

    public void setManage_tip_context(String manage_tip_context) {
        this.manage_tip_context = manage_tip_context;
    }

    public int getManage_tip_img() {
        return manage_tip_img;
    }

    public void setManage_tip_img(int manage_tip_img) {
        this.manage_tip_img = manage_tip_img;
    }


}
