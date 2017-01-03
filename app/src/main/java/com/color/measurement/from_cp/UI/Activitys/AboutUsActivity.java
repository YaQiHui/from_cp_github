package com.color.measurement.from_cp.UI.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.color.measurement.from_cp.R;

public class AboutUsActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_web_loading_progress);
        mWebView = (WebView) findViewById(R.id.wv_show_cp_web);

        mProgressBar.setMax(100);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //设置加载进度条的进度
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.loadUrl("http://www.hzcaipu.com/");
    }
    @Override
    public void onBackPressed() {
        //判断是否有历史记录,如果有就返回上一个网页
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            //没有就作为正常的返回按钮返回上一个活动
            super.onBackPressed();
        }
    }

}
