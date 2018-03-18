package com.example.rahulshaw.allnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DetailedNews extends AppCompatActivity {

    /*TextView tvNews;*/
    WebView detailedNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_news);

        Intent i = getIntent();
        detailedNews = (WebView) findViewById(R.id.detailedNewsView);
        detailedNews.setWebViewClient(new WebViewClient());
        detailedNews.getSettings().setJavaScriptEnabled(true);
        detailedNews.loadUrl(i.getStringExtra("url"));

        /*tvNews = (TextView) findViewById(R.id.textView);

        tvNews.setText(i.getStringExtra("url"));*/
    }
}
