package com.example.ateam_app.board_package.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.ateam_app.MainActivity;
import com.example.ateam_app.R;
import com.example.ateam_app.common.CommonMethod;
import com.example.ateam_app.user_pakage.dto.UserDTO;

import java.io.File;

public class BoardFragment extends Fragment  {

    private WebView webView;

    Bundle extra;
    UserDTO vo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_board, container, false);

        extra = this.getArguments();
        if(extra != null){
            vo = (UserDTO) extra.getSerializable("vo");
        }

        String ateamweb = "http://112.164.58.217:8999/ateamweb/testLogin?user_id=1&user_pw=1";

        webView = viewGroup.findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setTextZoom(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageCommitVisible(WebView view, String url) {
//                if(url.equals(ateamweb)){
//                    String script = "javascript:function startLoad(){" +
//                            "var vo = {'user_id':'"+vo.getUser_id()+"'," +
//                            "'user_pw':'"+vo.getUser_pw()+"'," +
//                            "'user_nm':'"+vo.getUser_nm()+"',"+
//                            "'user_addr':'"+vo.getUser_addr()+"',"+
//                            "'user_pro_img':'"+vo.getUser_pro_img()+"',"+
//                            "'user_phone_no':'"+vo.getUser_phone_no()+"',"+
//                            "'user_grade':'"+vo.getUser_grade()+"',"+
//                            "'user_type':'"+vo.getUser_type()+"'"+
//                            "};"+
//                            //"alert('하이브리ㄷ 접속 삥! "+vo.getUser_id()+"');" +
//                            "sessionStorage.setItem('loginInfo',vo);" +
//                            "};" +
//                            "startLoad();";
//                    view.loadUrl(script);
//                }
//            }
//
////            @Override
////            public void onLoadResource(WebView view, String url) {
////                if(url.equals(ateamweb)){
////                    String script = "javascript:function startLoad(){" +
////                            "var vo = {'user_id':'"+vo.getUser_id()+"'," +
////                            "'user_pw':'"+vo.getUser_pw()+"'," +
////                            "'user_nm':'"+vo.getUser_nm()+"',"+
////                            "'user_addr':'"+vo.getUser_addr()+"',"+
////                            "'user_pro_img':'"+vo.getUser_pro_img()+"',"+
////                            "'user_phone_no':'"+vo.getUser_phone_no()+"',"+
////                            "'user_grade':'"+vo.getUser_grade()+"',"+
////                            "'user_type':'"+vo.getUser_type()+"'"+
////                            "};"+
////                            "alert('하이브리ㄷ 접속 삥! "+vo.getUser_id()+"');" +
////                            "sessionStorage.setItem('loginInfo',vo);" +
////                            "};" +
////                            "startLoad();";
////                    view.loadUrl(script);
////                }
////            }
//        });

        webView.loadUrl(ateamweb);

        new CommonMethod().fragmentBackPress((MainActivity)getActivity(), requireActivity(), this, R.id.tabMain);
        return viewGroup;
    }

}