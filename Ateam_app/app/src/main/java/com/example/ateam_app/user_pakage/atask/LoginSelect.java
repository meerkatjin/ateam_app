package com.example.ateam_app.user_pakage.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.ateam_app.user_pakage.dto.UserDTO;

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

import static com.example.ateam_app.user_pakage.LoginActivity.loginDTO;
import static com.example.ateam_app.Common.CommonMethod.ipConfig;

public class LoginSelect extends AsyncTask<Void, Void, Void> {
    private String email, pw;

    public LoginSelect(String email, String pw){
        this.email = email;
        this.pw = pw;
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

            // 문자열 및 데이터 추가
            builder.addTextBody("user_email", email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_pw", pw, ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/ateamappspring/appLogin";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            // 하나의 오브젝트 가져올때
            loginDTO = readMessage(inputStream);

            inputStream.close();
            
        }catch (Exception e){
            Log.d("main:loginselect", e.getMessage());
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

    private UserDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        int user_id = 0;
        String  user_email = "", user_nm = "", user_addr = "", user_pro_img = "",
                user_phone_no = "", user_grade = "";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("user_id")){
                user_id = Integer.parseInt(reader.nextString());
            }else if(readStr.equals("user_email")){
                user_email = reader.nextString();
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
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", user_email + ", " + user_nm + ", " + user_addr + ", " + user_phone_no);
        return new UserDTO(user_id, user_email, user_nm, user_addr, user_pro_img, user_phone_no, user_grade);
    }//readMessage()
}
