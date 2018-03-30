package com.twocansandstring.twocansandstring;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainDashboard extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        this.webView = (WebView) findViewById(R.id.activity_main_dashboard_webview);
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        this.webView.setWebViewClient(new MyAppWebViewClient());

        this.webView.loadUrl("file:///android_asset/index.html");

    }

    @Override
    public void onBackPressed() {
        // TODO: implement custom logic
    }

    public class MyAppWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(Uri.parse(url).getHost().length() == 0) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }

    }
}
