package com.example.ateam_app.MangeTipPackage;

public class ManagaeDTO {
    String manage_tip_sub;
    String manage_tip_context;

    int resId;

    public ManagaeDTO(String manage_tip_sub, String manage_tip_context, int resId) {
        this.manage_tip_sub = manage_tip_sub;
        this.manage_tip_context = manage_tip_context;
        this.resId = resId;
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

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
