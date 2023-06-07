package com.example.a3keynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.net.URL;

// For showing the news data when the news article is clicked and storing news data for offline reading.
public class NewsDetailed extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detailed);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        webView=findViewById(R.id.webView);
        webView.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        webView.getSettings().setAllowFileAccess( true );
        webView.getSettings().setAppCacheEnabled( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT );
        if ( !isNetworkAvailable() ) {
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}