package com.example.miaudote.UserData;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miaudote.R;

public class Termos_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

        WebView webView = (WebView) findViewById(R.id.ww_content);
        webView.loadUrl("file:///android_asset/termos.html");
    }
}