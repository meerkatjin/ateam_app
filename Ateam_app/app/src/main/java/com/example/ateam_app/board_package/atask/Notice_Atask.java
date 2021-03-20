package com.example.ateam_app.board_package.atask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.ateam_app.board_package.dto.BoardDTO;

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

public class Notice_Atask extends AsyncTask<Void, Void, Void> {

    HttpClient httpClient;
    HttpPost httpPost;
    HttpResponse httpResponse;
    HttpEntity httpEntity;

    ArrayList<BoardDTO> list;

    public Notice_Atask(ArrayList<BoardDTO> noticeList) {
        this.list = noticeList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        list.clear();

        try {
            // MultipartEntityBuild 생성
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            String postURL = ipConfig + "/ateamappspring/list.no";

            // 전송
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Android");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

            readJsonStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            Log.d("Sub1", e.getMessage());
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

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);

    }

    public void readJsonStream(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                list.add(readMessage(reader));
            }
            reader.endArray();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            Log.d("222", e.getMessage());
        }finally {
            reader.close();
        }
    }

    public BoardDTO readMessage(JsonReader reader) throws IOException {

        long user_id = 0;
        int board_no = 0, board_readcount = 0, no = 0, root = 0, indent = 0, step = 0;
        String board_gp = "", board_title = "", board_content = "", user_type = "", filename = "", filepath = "", name = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String readStr = reader.nextName();
            if (readStr.equals("user_id")) {
                user_id = reader.nextLong();
            } else if (readStr.equals("board_no")) {
                board_no = reader.nextInt();
            } else if (readStr.equals("board_readcount")) {
                board_readcount = reader.nextInt();
            } else if (readStr.equals("no")) {
                no  = reader.nextInt();
            } else if (readStr.equals("root")) {
                root  = reader.nextInt();
            } else if (readStr.equals("indent")) {
                indent  = reader.nextInt();
            } else if (readStr.equals("step")) {
                step  = reader.nextInt();
            } else if (readStr.equals("board_gp")) {
                board_gp  = reader.nextString();
            } else if (readStr.equals("board_title")) {
                board_title  = reader.nextString();
            } else if (readStr.equals("board_content")) {
                board_content  = reader.nextString();
            } else if (readStr.equals("user_type")) {
                user_type  = reader.nextString();
            } else if (readStr.equals("filename")) {
                filename  = reader.nextString();
            } else if (readStr.equals("filepath")) {
                filepath  = reader.nextString();
            } else if (readStr.equals("name")) {
                name  = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new BoardDTO(user_id,board_no,board_readcount,no,root,indent,step,board_gp,board_title,board_content,user_type,filename,filepath,name);

    }
}
