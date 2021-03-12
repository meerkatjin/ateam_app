package com.example.ateam_app.irdnt_list_package;

import android.view.View;
import android.widget.CompoundButton;

public interface OnIrdntItemCheckListener {
    void onItemCheck(IrdntListAdapter.ViewHolder holder, View view, int position, CompoundButton buttonView, boolean isChecked);
}
