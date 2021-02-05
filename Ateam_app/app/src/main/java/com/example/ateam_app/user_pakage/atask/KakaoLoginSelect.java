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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.ateam_app.common.CommonMethod.ipConfig;
import static com.example.ateam_app.user_pakage.LoginActivity.loginDTO;

public class KakaoLoginSelect extends AsyncTask<Void, Void, UserDTO> {
    private UserDTO dto;

    public KakaoLoginSelect(UserDTO dto){
        this.dto = dto;
    }

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected UserDTO doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            // 문자열 및 데이터 추가
            builder.addTextBody("user_id", String.valueOf(dto.getUser_id()), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_email", dto.getUser_email(), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_nm", dto.getUser_nm(), ContentType.create("Multipart/related", "UTF-8"));
            if(dto.getUser_pro_img() != null) {
                builder.addTextBody("user_pro_img", dto.getUser_pro_img(), ContentType.create("Multipart/related", "UTF-8"));
            }
            builder.addTextBody("user_type", dto.getUser_type(), ContentType.create("Multipart/related", "UTF-8"));

            String postURL = ipConfig + "/ateamappspring/appKakaoLogin";

            //전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            loginDTO = readMessage(inputStream);

            inputStream.close();
        }catch (Exception e){
            Log.d("main:kakaologinselect", e.getMessage());
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
        return loginDTO;
    }

    private UserDTO readMessage(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        long user_id = 0;
        String  user_email = "", user_nm = "", user_pro_img = "",
                user_grade = "", user_type = "";

        reader.beginObject();
        while (reader.hasNext()){
            String readStr = reader.nextName();
            if(readStr.equals("user_id")){
                user_id = reader.nextInt();
            }else if(readStr.equals("user_email")){
                user_email = reader.nextString();
            }else if(readStr.equals("user_nm")){
                user_nm = reader.nextString();
            }else if(readStr.equals("user_pro_img")){
                user_pro_img = reader.nextString();
            }else if(readStr.equals("user_grade")){
                user_grade = reader.nextString();
            }else if(readStr.equals("user_type")){
                user_type = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        Log.d("main:loginselect : ", user_email + ", " + user_nm);
        return new UserDTO(user_id, user_email, user_nm, user_pro_img, user_grade, user_type);
    }//readMessage()
}
