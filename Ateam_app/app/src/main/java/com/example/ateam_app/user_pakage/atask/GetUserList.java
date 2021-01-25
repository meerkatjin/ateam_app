package com.example.ateam_app.user_pakage.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.ateam_app.user_pakage.adapter.UserAdapter;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class GetUserList extends AsyncTask<Void, Void, Void> {
    ArrayList<UserDTO> dtos;
    UserAdapter adapter;

    public GetUserList(ArrayList<UserDTO> dtos, UserAdapter adapter){
        this.dtos = dtos;
        this.adapter = adapter;
    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String postURL = ipConfig + "/ateamappspring/getUserList";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);
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
        return null;
    }

    private void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                dtos.add(readMessage(reader));
            }
            reader.endArray();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }finally {
            reader.close();
        }
    }

    //리스트 가져올때
    private UserDTO readMessage(JsonReader reader) throws IOException {
        long user_id = 0;
        String  user_email = "", user_nm = "", user_pw = "",user_addr = "", user_pro_img = "",
                user_phone_no = "", user_grade = "", user_type = "";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("user_id")){
                user_id = reader.nextLong();
            }else if(readStr.equals("user_email")){
                user_email = reader.nextString();
            }else if(readStr.equals("user_pw")) {
                user_pw = reader.nextString();
            }else if(readStr.equals("user_nm")){
                user_nm = reader.nextString();
            }else if(readStr.equals("user_addr")){
                user_addr = reader.nextString();
            }else if(readStr.equals("user_pro_img")){
                user_pro_img = reader.nextString();
            }else if(readStr.equals("user_phone_no")){
                user_phone_no = reader.nextString();
            }else if(readStr.equals("user_grade")){
                user_grade = reader.nextString();
            }else if(readStr.equals("user_type")){
                user_type = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:getUser : ", user_email + ", " + user_nm + ", " + user_addr + ", " + user_phone_no);
        return new UserDTO(user_id, user_email, user_pw, user_nm, user_addr, user_pro_img, user_phone_no, user_grade, user_type);
    }

}
