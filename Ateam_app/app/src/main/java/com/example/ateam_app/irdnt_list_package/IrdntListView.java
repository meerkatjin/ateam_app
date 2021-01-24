package com.example.ateam_app.irdnt_list_package;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class IrdntListView extends AsyncTask<Void, Void, Void> {
    ArrayList<IrdntListDTO> items;
    IrdntListAdapter adapter;
    ProgressDialog progressDialog;

    public IrdntListView(ArrayList<IrdntListDTO> items, IrdntListAdapter adapter, ProgressDialog progressDialog) {
        this.items = items;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        items.clear();

        String postURL = ipConfig + "/ateamappspring/irdntList";

        return null;
    }
}
