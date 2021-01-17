package com.example.ateam_app.recipe_fragment;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

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
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import static com.example.ateam_app.common.CommonMethod.ipConfig;

public class RecipeDAO {


    public class recipeInfo extends AsyncTask<Void, Void, Void> {
        private RecipeDTO dto;
        private int recipe_id;
        private int img_url;
        private String recipe_nm_ko;
        private String sumry;
        private String nation_nm;
        private String ty_nm;
        private String cooking_time;
        private String calorie;
        private String qnt;
        private String level_nm;
        private String irdnt_code;
        private Object RecipeDTO;


        public recipeInfo(RecipeDTO dto) {
            this.dto = dto;
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
                builder.addTextBody("RECIPE_ID", String.valueOf(recipe_id), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("RECIPE_NM_KO", recipe_nm_ko, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("SUMRY", sumry, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("NATION_NM", nation_nm, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("TY_NM", ty_nm, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("COOKING_TIME", cooking_time, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("CALORIE", calorie, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("QNT", qnt, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("LEVEL_NM", level_nm, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("IRDNT_CODE", irdnt_code, ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("IMG_URL", String.valueOf(img_url), ContentType.create("Multipart/related", "UTF-8"));

                String postURL = ipConfig + "/ateamappspring/recipeInfo";

                //전송
                InputStream inputStream = null;
                httpClient = AndroidHttpClient.newInstance("Android");
                httpPost = new HttpPost(postURL);
                httpPost.setEntity(builder.build());
                httpResponse = httpClient.execute(httpPost);
                httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                // 하나의 오브젝트 가져올때

                ArrayList<RecipeDTO> dtos = readMessage(inputStream);

                inputStream.close();

            }catch (Exception e){
                Log.d("main:RecipeDAO", e.getMessage());
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

        private RecipeDTO readMessage(InputStream inputStream) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

            int recipe_id = 0;
            String  img_url = "", sumry = "", nation_nm = "", ty_nm = "",
                    cooking_time = "", calorie = "", qnt = "", level_nm = "", irdnt_code = "";

            reader.beginObject();
            while (reader.hasNext()){
                String readStr = reader.nextName();
                if(readStr.equals("recipe_id")){
                    recipe_id = Integer.parseInt(reader.nextString());
                }else if(readStr.equals("img_url")){
                    img_url = reader.nextString();
                }else if(readStr.equals("sumry")){
                    sumry = reader.nextString();
                }else if(readStr.equals("nation_nm")){
                    nation_nm = reader.nextString();
                }else if(readStr.equals("ty_nm")){
                    ty_nm = reader.nextString();
                }else if(readStr.equals("cooking_time")){
                    cooking_time = reader.nextString();
                }else if(readStr.equals("calorie")){
                    calorie = reader.nextString();
                }else if (readStr.equals("qnt")){
                    qnt = reader.nextString();
                }else if (readStr.equals("level_nm")){
                    level_nm = reader.nextString();
                }else if (readStr.equals("irdnt_code")){
                    irdnt_code = reader.nextString();
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            Log.d("main:loginselect : ", String.valueOf(recipe_id));
            return new RecipeDTO(recipe_id, img_url, sumry, nation_nm, ty_nm, cooking_time, calorie, qnt, level_nm, irdnt_code);
        }//readMessage()
    }


}//RecipeDAO Class
