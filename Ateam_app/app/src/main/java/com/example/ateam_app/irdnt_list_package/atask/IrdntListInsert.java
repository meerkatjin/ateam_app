package com.example.ateam_app.irdnt_list_package;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

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
import java.nio.charset.Charset;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class IrdntListInsert extends AsyncTask<Void, Void, String> {
    //재료 추가 기능 수행
    private static final String TAG = "IrdntListInsert";
    private Long user_id;
    private String content_nm;

    public IrdntListInsert(String name, Long user_id) {
        content_nm = name;
        this.user_id = user_id;
    }

    String state = "";

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

            //상품 이름(ex. 양파, 감자, 당근 등)을 넘기면 자동으로 테이블에 정보 추가
            //회원 아이디 정보 넘겨야함
            builder.addTextBody("content_nm", content_nm, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_id", String.valueOf(user_id), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/ateamappspring/insert";

            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            state = stringBuilder.toString();

            inputStream.close();

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

        return state;
    }
}
