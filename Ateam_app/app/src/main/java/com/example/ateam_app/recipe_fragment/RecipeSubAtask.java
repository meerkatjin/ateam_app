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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class RecipeSubAtask extends AsyncTask<Void, Void, Void> {
    ArrayList<RecipeSubItem> myRecipeSubItems;
    int recipe_id;
    RecipeSubAdapter adapter;
    ProgressDialog progressDialog;
    RecipeSubItem recipeSubItem;
    public RecipeSubAtask(ArrayList<RecipeSubItem> myRecipeSubItems, int recipe_id) {
        this.myRecipeSubItems = myRecipeSubItems;
        this.recipe_id = recipe_id;
    }

    public RecipeSubAtask(ArrayList<RecipeSubItem> myRecipeSubItems, int recipe_id, RecipeSubAdapter adapter) {
        this.myRecipeSubItems = myRecipeSubItems;
        this.recipe_id = recipe_id;
        this.adapter = adapter;

    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;



    @Override
    protected Void doInBackground(Void... voids) {
        myRecipeSubItems.clear();

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("recipe_id", String.valueOf(recipe_id), ContentType.create("Multipart/related", "UTF-8"));
            //builder.addTextBody("passwd", passwd, ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/recipeIng";

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
            Log.d("RecipeSub:RecipeSub", e.getMessage());
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


        Log.d("RecipeFragment", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }
   public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                myRecipeSubItems.add(readMessage(reader));
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
    public RecipeSubItem readMessage(JsonReader reader) throws IOException {

        int recipe_id = 0;
        int cooking_no = 0;
        String cooking_dc = "";
        String stre_step_image_url = "";
        String step_tip = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("recipe_id")) {
                recipe_id = Integer.parseInt(reader.nextString());
            } else if (readStr.equals("cooking_no")) {
                cooking_no = Integer.parseInt(reader.nextString());
            } else if (readStr.equals("cooking_dc")) {
                cooking_dc = reader.nextString();
            } else if (readStr.equals("stre_step_image_url")) {
                stre_step_image_url  = reader.nextString();
            } else if (readStr.equals("step_tip")) {
                step_tip  = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("RecipeSub : ", recipe_id + ", " + cooking_no);
        return new RecipeSubItem(recipe_id, cooking_no, cooking_dc, stre_step_image_url, step_tip);


    }
}
