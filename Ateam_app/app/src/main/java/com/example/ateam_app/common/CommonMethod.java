package com.example.ateam_app.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CommonMethod {
    //public static String  ipConfig = "http://121.179.5.99:8989";  //선생님 컴퓨터로 보낼때
    public static String  ipConfig = "http://192.168.0.24:8080";  //테스트 할때 자기 ip와 포트로 접근하세요!!

    // 네트워크에 연결되어 있는가
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d("isconnected : ", "WIFI 로 설정됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("isconnected : ", "일반망으로 설정됨");
            }
            Log.d("isconnected : ", "OK => " + info.isConnected());
            return true;
        }else {
            Log.d("isconnected : ", "False => 데이터 통신 불가!!!" );
            return false;
        }
    }
}
