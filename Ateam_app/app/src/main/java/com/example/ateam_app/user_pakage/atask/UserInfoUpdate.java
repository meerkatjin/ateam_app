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

import java.nio.charset.Charset;

public class UserInfoUpdate extends AsyncTask<Void, Void, Void> {

    private String user_email, user_pw, user_nm, user_addr, user_pro_img, user_phone_no;

    public UserInfoUpdate(String user_email, String user_pw, String user_nm, String user_addr, String user_pro_img, String user_phone_no) {
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nm = user_nm;
        this.user_addr = user_addr;
        this.user_pro_img = user_pro_img;
        this.user_phone_no = user_phone_no;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // MultipartEntityBuild 생성
            String postURL = "";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 문자열 및 데이터 추가
            builder.addTextBody("user_email", user_email, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_pw", user_pw, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_nm", user_nm, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_addr", user_addr, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_pro_img", user_pro_img, ContentType.create("Multipart/related", "UTF-8"));
            builder.addTextBody("user_phone_no", user_phone_no, ContentType.create("Multipart/related", "UTF-8"));

            Log.d("UpdateEmail", user_email);
            Log.d("UpdatePw", user_pw);
            Log.d("UpdateName", user_nm);
            Log.d("UpdateAddr", user_addr);
            Log.d("UpdateImg", user_pro_img);
            Log.d("UpdatePhone", user_phone_no);
            Log.d("Sub1Update:postURL", postURL);

            // 전송
            //InputStream inputStream = null;
            HttpClient httpClient = AndroidHttpClient.newInstance("Android");
            HttpPost httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //inputStream = httpEntity.getContent();

            // 응답
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }*/
            //inputStream.close();

            // 응답결과
               /* String result = stringBuilder.toString();
                Log.d("response", result);*/

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
