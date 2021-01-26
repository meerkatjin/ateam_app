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
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

<<<<<<< HEAD:Ateam_app/app/src/main/java/com/example/ateam_app/recipe_fragment/Mainfragment_Recipe_Atask.java
public class Mainfragment_Recipe_Atask extends AsyncTask<Void, Void, Void> {
    ArrayList<RecipeItem> myRecipeArrayList;

    int recipe_id;
    RecipeItem recipeItem;


=======
public class IrdntListView extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:IrdntListView";
    ArrayList<IrdntListDTO> items;
    IrdntListAdapter adapter;
    ProgressDialog progressDialog;
    Long user_id;

    public IrdntListView(ArrayList<IrdntListDTO> items, IrdntListAdapter adapter, ProgressDialog progressDialog, Long user_id) {
        this.items = items;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
        this.user_id = user_id;
    }
>>>>>>> fbda0319cefdbd7f4453f9a014988c7be63c113c:Ateam_app/app/src/main/java/com/example/ateam_app/irdnt_list_package/IrdntListView.java

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;


    public Mainfragment_Recipe_Atask() {

    }

    public Mainfragment_Recipe_Atask(RecipeItem recipeItem, int recipe_id) {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
<<<<<<< HEAD:Ateam_app/app/src/main/java/com/example/ateam_app/recipe_fragment/Mainfragment_Recipe_Atask.java


        myRecipeArrayList.clear();

=======
        items.clear();
>>>>>>> fbda0319cefdbd7f4453f9a014988c7be63c113c:Ateam_app/app/src/main/java/com/example/ateam_app/irdnt_list_package/IrdntListView.java

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
<<<<<<< HEAD:Ateam_app/app/src/main/java/com/example/ateam_app/recipe_fragment/Mainfragment_Recipe_Atask.java
            builder.addTextBody("recipe_id", String.valueOf(recipe_id), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/recipeInfoMf";
=======
            builder.setCharset(Charset.forName("UTF-8"));
            builder.addTextBody("user_id", String.valueOf(user_id), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/ateamappspring/irdntList";
>>>>>>> fbda0319cefdbd7f4453f9a014988c7be63c113c:Ateam_app/app/src/main/java/com/example/ateam_app/irdnt_list_package/IrdntListView.java

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
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            Log.d("222", e.getMessage());
        }finally {
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
        return new RecipeItem(recipe_id, recipe_nm_ko, sumry, nation_nm, ty_nm, cooking_time, calorie, qnt, level_nm, irdnt_code, img_url);


    }




}
