package com.example.ateam_app.user_pakage.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class UserInfoUpdate extends AsyncTask<Void, Void, String> {

    // 데이터베이스에 삽입결과 0보다크면 삽입성공, 같거나 작으면 실패
    String state = "";

    private long user_id;
    private String user_email, user_pw, user_nm, user_addr, user_pro_img, user_phone_no, user_grade, user_type, imageRealPathU;

    public UserInfoUpdate(long user_id, String user_email, String user_pw,
                          String user_nm, String user_addr, String user_pro_img,
                          String user_phone_no, String user_grade, String user_type,
                          String imageRealPathU) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
        this.user_addr = user_addr;
        this.user_pro_img = user_pro_img;
        this.user_phone_no = user_phone_no;
        this.user_grade = user_grade;
        this.user_type = user_type;
        this.imageRealPathU = imageRealPathU;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

    }

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("user_id", String.valueOf(user_id), ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_email", user_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_pw", user_pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_nm", user_nm, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_addr", user_addr, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_pro_img", user_pro_img, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_phone_no", user_phone_no, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_grade", user_grade, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_type", user_type, ContentType.create("Multipart/related", "UTF-8"));

            if(!imageRealPathU.trim().equals("")){
                builder.addPart("image", new FileBody(new File(imageRealPathU)));
            }

            Log.d("UpdateEmail", user_email);
            Log.d("UpdatePW", user_pw);
            Log.d("UpdateName", user_nm);
            Log.d("UpdateAddr", user_addr);
            Log.d("UpdateImg", user_pro_img);
            Log.d("imageRealPathU", imageRealPathU);

            String postURL = ipConfig + "/ateamappspring/userInfoChange";

            // 전송
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

            state = stringBuilder.toString();
            inputStream.close();

            // 응답결과
               /* String result = stringBuilder.toString();
                Log.d("response", result);*/

        }catch (Exception e){
            e.printStackTrace();
        }

        return state;
    }
}
