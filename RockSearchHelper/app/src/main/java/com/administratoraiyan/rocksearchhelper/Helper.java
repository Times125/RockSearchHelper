package com.administratoraiyan.rocksearchhelper;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by 李春辉 on 2016/3/3.
 * QQ:1251680944
 */
public class Helper extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper);
        WebView webView = (WebView)findViewById(R.id.help);
        WebSettings webSettings = webView.getSettings();
        //使页面自适应屏幕大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //使页面支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("http://weibo.com/p/1005051750842107/home?from=page_100505&mod=TAB&is_all=1#place");
    }
}
