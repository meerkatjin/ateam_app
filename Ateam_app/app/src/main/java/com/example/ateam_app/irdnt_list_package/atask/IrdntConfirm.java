package com.example.ateam_app.irdnt_list_package.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import com.example.ateam_app.irdnt_list_package.IrdntListDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class IrdntConfirm extends AsyncTask<Void, Void, String> {

    IrdntListDTO dto;

    public IrdntConfirm(IrdntListDTO dto) {
        this.dto = dto;
    }

    String check = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("content_list_id", String.valueOf(dto.getContent_list_id()), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("content_nm",dto.getContent_nm(), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("content_ty",dto.getContent_ty(), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("shelf_life_start",dto.getShelf_life_start(), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("shelf_life_end",dto.getShelf_life_end(), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/irdntConfirm";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 응답
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            check = stringBuilder.toString();

            inputStream.close();

        }catch (Exception e){
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

        return check;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
