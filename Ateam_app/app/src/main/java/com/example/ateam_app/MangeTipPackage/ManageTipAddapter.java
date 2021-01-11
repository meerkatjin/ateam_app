package com.example.ateam_app.MangeTipPackage;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ateam_app.R;

import java.util.ArrayList;

public class ManageTipAddapter extends BaseAdapter {

         Context context;
    ArrayList<ManagaeDTO> dtos;
    Point size;
    LayoutInflater inflater;

    public ManageTipAddapter(Context context, ArrayList<ManagaeDTO> dtos, Point size) {
        this.context = context;
        this.dtos = dtos;
        this.size = size;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ManageTipAddapter(ManagaeDTO dto) {

    }

    //addDTO
    public void addDto(ManagaeDTO dto){

        dtos.add(dto);
    }


    @Override
    public int getCount() {

        return dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
