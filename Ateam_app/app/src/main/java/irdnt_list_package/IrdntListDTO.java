package irdnt_list_package;

public class IrdntListDTO {
    String irdnt_nm, irdnt_ty_code, due_date;

    public IrdntListDTO(String irdnt_nm, String irdnt_ty_code, String due_date) {
        this.irdnt_nm = irdnt_nm;
        this.irdnt_ty_code = irdnt_ty_code;
        this.due_date = due_date;
    }

    public String getIrdnt_nm() {
        return irdnt_nm;
    }

    public void setIrdnt_nm(String irdnt_nm) {
        this.irdnt_nm = irdnt_nm;
    }

    public String getIrdnt_ty_code() {
        return irdnt_ty_code;
    }

    public void setIrdnt_ty_code(String irdnt_ty_code) {
        this.irdnt_ty_code = irdnt_ty_code;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
