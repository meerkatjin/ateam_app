package com.example.ateam_app.irdnt_list_package.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

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

public class IrdntNewContentListATask extends AsyncTask<Void, Void, ArrayList<Long>> {
    ArrayList<Long> ids;
    private long id;

    public IrdntNewContentListATask(long id) {
        this.id = id;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<Long> doInBackground(Void... voids) {
        String msg = "";
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("user_id", String.valueOf(id), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/getNewContentList";

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
            msg = stringBuilder.toString();

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

        if(msg.substring(msg.indexOf('[')+1,msg.indexOf(']')).trim().equals("")
                || msg.substring(msg.indexOf('[')+1,msg.indexOf(']')) == null){
            ids = null;
        }else{
            String temp[] = msg.substring(msg.indexOf('[')+1,msg.indexOf(']')).split(",");
            ids = new ArrayList<>();

            for(int i = 0; i < temp.length; i++){
                ids.add(Long.parseLong(temp[i]));
            }
        }
        return ids;
    }
}
