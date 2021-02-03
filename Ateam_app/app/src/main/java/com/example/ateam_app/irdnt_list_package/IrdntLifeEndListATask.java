package com.example.ateam_app.irdnt_list_package;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;

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

public class IrdntLifeEndListATask extends AsyncTask<Void, Void, ArrayList<Long>> {

    ArrayList<Long> ids;
    private long id;

    public IrdntLifeEndListATask(long id) {
        this.id = id;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected ArrayList<Long> doInBackground(Void... voids) {

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            builder.addTextBody("user_id", String.valueOf(id), ContentType.create("Multipart/related", "UTF-8"));
            String postURL = ipConfig + "/ateamappspring/getLifeEndList";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            ids = readJsonStream(inputStream);

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

        return ids;
    }

    private ArrayList<Long> readJsonStream(InputStream inputStream) throws IOException {
        ArrayList<Long> longs = new ArrayList<>();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                longs.add(readMessage(reader));
            }
            reader.endArray();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }finally {
            reader.close();
        }

        return longs;
    }

    private Long readMessage(JsonReader reader) throws IOException{
        long user_id = 0;

        reader.beginObject();
        while (reader.hasNext()){
            user_id = reader.nextLong();
        }
        reader.endObject();
        return  user_id;
    }
}
