package com.example.ateam_app.recipe_fragment;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RecipeAtask extends AsyncTask<Void, Void, Void> {
    ArrayList<RecipeItem> myRecipeArrayList;
    RecipeAdapter adapter;
    ProgressDialog progressDialog;

    public RecipeAtask(ArrayList<RecipeItem> myRecipeArrayList, RecipeAdapter adapter, ProgressDialog progressDialog) {
        this.myRecipeArrayList = myRecipeArrayList;
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
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("RecipeFragment", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        myRecipeArrayList.clear();
        String result = "";
        String postURL = ipConfig + "/app/recipeInfo";
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);

            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            String jsonStr = stringBuilder.toString();

            inputStream.close();*/

        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
            e.printStackTrace();
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myRecipeArrayList.add(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public RecipeItem readMessage(JsonReader reader) throws IOException {
        int recipe_id = 0;
        String recipe_nm_ko = "";
        String sumry = "";
        String nation_nm = "";
        String ty_nm = "";
        String cooking_time = "";
        String calorie = "";
        String qnt = "";
        String level_nm = "";
        String irdnt_code = "";
        String img_url = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("recipe_id")) {
                recipe_id = Integer.parseInt(reader.nextString());
            } else if (readStr.equals("recipe_nm_ko")) {
                recipe_nm_ko = reader.nextString();
            } else if (readStr.equals("sumry")) {
                sumry = reader.nextString();
            } else if (readStr.equals("nation_nm")) {
                nation_nm  = reader.nextString();
            } else if (readStr.equals("ty_nm")) {
                ty_nm  = reader.nextString();
            } else if (readStr.equals("cooking_time")) {
                cooking_time  = reader.nextString();
            } else if (readStr.equals("calorie")) {
                calorie  = reader.nextString();
            } else if (readStr.equals("qnt")) {
                qnt  = reader.nextString();
            } else if (readStr.equals("level_nm")) {
                level_nm  = reader.nextString();
            } else if (readStr.equals("irdnt_code")) {
                irdnt_code  = reader.nextString();
            } else if (readStr.equals("img_url")) {
                img_url  = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("listselect:myitem", recipe_id + "," + irdnt_code);
        return new RecipeItem(recipe_id, recipe_nm_ko, sumry, nation_nm, ty_nm, cooking_time, calorie, qnt, level_nm, irdnt_code, img_url);

    }

}//class
