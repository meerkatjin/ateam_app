package com.example.ateam_app;

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

public class SearchIrdnt extends AsyncTask<Void, Void, Void> {
    String searchText;
    IrdntListAdapter irdntListAdapter;
    ArrayList<IrdntListDTO> irdntListItems;
    ProgressDialog progressDialog;
    Long user_id;

    //재료 검색
    public SearchIrdnt(String searchText, ArrayList<IrdntListDTO> irdntListItems, IrdntListAdapter irdntListAdapter, ProgressDialog progressDialog, Long user_id) {
        this.searchText = searchText;
        this.irdntListItems = irdntListItems;
        this.irdntListAdapter = irdntListAdapter;
        this.progressDialog = progressDialog;
        this.user_id = user_id;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {
        irdntListItems.clear();

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));
            builder.addTextBody("searchText", searchText, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_id", String.valueOf(user_id), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/ateamappspring/searchIrdnt";

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

        irdntListAdapter.notifyDataSetChanged();
    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                irdntListItems.add(readMessage(reader));
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
        int content_list_id = 0;

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("content_list_id")) {
                content_list_id = reader.nextInt();
            } else if (readStr.equals("content_nm")) {
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
        return new IrdntListDTO(content_list_id, content_nm, content_ty, shelf_life_end);


    }
}
