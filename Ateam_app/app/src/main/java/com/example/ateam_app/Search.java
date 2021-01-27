package com.example.ateam_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;
import com.example.ateam_app.manage_tip_package.ManagaeDTO;
import com.example.ateam_app.manage_tip_package.ManageTipAddapter;
import com.example.ateam_app.recipe_fragment.RecipeAdapter;
import com.example.ateam_app.recipe_fragment.RecipeItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class Search extends AsyncTask<Void, Void, String> {
    String searchText;
    int bottomNavi;
    IrdntListAdapter irdntListAdapter;
    RecipeAdapter recipeAdapter;
    ManageTipAddapter manageTipAdapter;
    ArrayList<IrdntListDTO> irdntListDTO;
    ArrayList<RecipeItem> recipeDTO;
    ArrayList<ManagaeDTO> manageDTO;
    ProgressDialog progressDialog;

    public Search(String searchText, int bottomNavi) {
        this.searchText = searchText;
        this.bottomNavi = bottomNavi;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));



        } catch (Exception e) {

        } finally {

        }

        return null;
    }
}
