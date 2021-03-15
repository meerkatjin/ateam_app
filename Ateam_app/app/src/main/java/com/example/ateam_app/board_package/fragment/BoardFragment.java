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

        String ateamweb = "http://112.164.58.217:8999/ateamweb/list.no";

        if(vo.getUser_type().equals("nomal")){
            ateamweb = "http://112.164.58.217:8999/ateamweb/appNomalLogin?user_email="+vo.getUser_email()+"&user_pw="+vo.getUser_pw();
        }else if(!vo.getUser_type().equals("nomal") && !vo.getUser_type().equals(null)){
            ateamweb = "http://112.164.58.217:8999/ateamweb/appKakaoLogin?user_id="+vo.getUser_id()+"&user_type="+vo.getUser_type();
        }

        webView = viewGroup.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(ateamweb);

        new CommonMethod().fragmentBackPress((MainActivity)getActivity(), requireActivity(), this, R.id.tabMain);
        return viewGroup;
    }

}