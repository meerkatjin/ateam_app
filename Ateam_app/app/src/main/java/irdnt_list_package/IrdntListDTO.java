package irdnt_list_package;

public class IrdntListDTO {
    String irdnt_nm, content_ty, due_date;

    public IrdntListDTO(String irdnt_nm, String content_ty, String due_date) {
        this.irdnt_nm = irdnt_nm;
        this.content_ty = content_ty;
        this.due_date = due_date;
    }

    public String getIrdnt_nm() {
        return irdnt_nm;
    }

    public void setIrdnt_nm(String irdnt_nm) {
        this.irdnt_nm = irdnt_nm;
    }

    public String getContent_ty() {
        return content_ty;
    }

    public void setContent_ty(String content_ty) {
        this.content_ty = content_ty;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
