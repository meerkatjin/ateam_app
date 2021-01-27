package com.example.ateam_app.recipe_fragment;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.ateam_app.irdnt_list_package.IrdntListAdapter;
import com.example.ateam_app.irdnt_list_package.IrdntListDTO;

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
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class Mainfragment_Recipe_Atask extends AsyncTask<Void, Void, Void> {

    RecipeItem recipeItem;
    ArrayList<RecipeItem> items;
    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;



    public Mainfragment_Recipe_Atask(RecipeItem recipeItem) {
        this.recipeItem = recipeItem;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        recipeItem = new RecipeItem();


        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

         //   builder.addTextBody("recipe_id", String.valueOf(recipe_id), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/recipeInfoMf";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            recipeItem = readMessage(inputStream);


            inputStream.close();
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

    public RecipeItem readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

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
        return new RecipeItem(recipe_id, recipe_nm_ko, sumry, nation_nm, ty_nm, cooking_time, calorie, qnt, level_nm, irdnt_code, img_url);

    }

}
