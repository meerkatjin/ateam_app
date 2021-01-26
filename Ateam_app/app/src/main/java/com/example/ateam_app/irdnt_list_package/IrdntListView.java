package com.example.ateam_app.irdnt_list_package;

import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.recipe_fragment.RecipeItem;

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

public class IrdntListView extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "main:IrdntListView";
    ArrayList<IrdntListDTO> items;
    IrdntListAdapter adapter;
    ProgressDialog progressDialog;
    Long user_id;
    String content_ty;
    int tabSelected;

    public IrdntListView(ArrayList<IrdntListDTO> items, IrdntListAdapter adapter, ProgressDialog progressDialog, Long user_id, int tabSelected) {
        this.items = items;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
        this.user_id = user_id;
        this.tabSelected = tabSelected;
    }

    public IrdntListView(ArrayList<IrdntListDTO> items, IrdntListAdapter adapter, ProgressDialog progressDialog, Long user_id, int tabSelected, String content_ty) {
        this.items = items;
        this.adapter = adapter;
        this.progressDialog = progressDialog;
        this.user_id = user_id;
        this.tabSelected = tabSelected;
        this.content_ty = content_ty;
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

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            builder.addTextBody("user_id", String.valueOf(user_id), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = null;
            if (tabSelected > 10) {
                builder.addTextBody("content_ty", content_ty, ContentType.create("Multipart/related", "UTF-8"));
                postURL = ipConfig + "/ateamappspring/irdntListType";
            } else if (tabSelected == 2) {
                postURL = ipConfig + "/ateamappspring/irdntListDate";
            } else if (tabSelected == 3) {
                postURL = ipConfig + "/ateamappspring/irdntListName";
            }

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
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        Log.d("IrdntListFragment", "List Select Complete!!!");

        adapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                items.add(readMessage(reader));
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

    public IrdntListDTO readMessage(JsonReader reader) throws IOException {

        String content_nm = "", content_ty = "", shelf_life_end = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("content_nm")) {
                content_nm = reader.nextString();
            } else if (readStr.equals("content_ty")) {
                content_ty = reader.nextString();
            } else if (readStr.equals("shelf_life_end")) {
                shelf_life_end = reader.nextString();
            }  else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new IrdntListDTO(content_nm, content_ty, shelf_life_end);


    }

}