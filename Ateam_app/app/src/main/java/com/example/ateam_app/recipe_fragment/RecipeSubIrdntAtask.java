package com.example.ateam_app.recipe_fragment;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class RecipeSubIrdntAtask extends AsyncTask<Void, Void, Void> {
    ArrayList<RecipeSubIrdnt> irdntItems;
    int recipe_id;
    RecipeSubIrdntAdapter adapter;

    public RecipeSubIrdntAtask(ArrayList<RecipeSubIrdnt> irdntItems, int recipe_id, RecipeSubIrdntAdapter adapter) {
        this.irdntItems = irdntItems;
        this.recipe_id = recipe_id;
        this.adapter = adapter;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        irdntItems.clear();

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("recipe_id", String.valueOf(recipe_id), ContentType.create("Multipart/related", "UTF-8"));
            //builder.addTextBody("passwd", passwd, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/recipeIrdnt";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();


            readJsonStream(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                irdntItems.add(readMessage(reader));
            }
            reader.endArray();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            Log.d("222", e.getMessage());
        }finally {
            reader.close();
        }
    }
    public RecipeSubIrdnt readMessage(JsonReader reader) throws IOException {

        int recipe_id = 0;
        String irdnt_nm = "";
        String irdnt_cpcty = "";
        String irdnt_ty_nm = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("recipe_id")) {
                recipe_id = Integer.parseInt(reader.nextString());
            } else if (readStr.equals("irdnt_nm")) {
                irdnt_nm = reader.nextString();
            } else if (readStr.equals("irdnt_cpcty")) {
                irdnt_cpcty = reader.nextString();
            } else if (readStr.equals("irdnt_ty_nm")) {
                irdnt_ty_nm = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("RecipeSub : ", recipe_id + ", " + irdnt_nm);

        return new RecipeSubIrdnt(recipe_id, irdnt_nm, irdnt_cpcty, irdnt_ty_nm);
    }
}
