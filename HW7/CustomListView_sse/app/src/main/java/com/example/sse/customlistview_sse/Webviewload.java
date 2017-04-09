package com.example.sse.customlistview_sse;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by shou on 4/8/2017.
 */

public class Webviewload extends Activity {
    private WebView webview;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Bundle bundle = getIntent().getExtras();
        String url= bundle.getString("url");
        webview = (WebView) findViewById(R.id.WebViewpage);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setInitialScale(1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(false);
        webview.loadUrl(url);

    }
}
