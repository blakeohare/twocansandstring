package com.twocansandstring.twocansandstring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class TwoCansWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        this.webView = (WebView) findViewById(R.id.activity_main_dashboard_webview);
        this.webView.getSettings().setJavaScriptEnabled(true);

        this.webView.setWebViewClient(new TwoCansWebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String filename = consoleMessage.sourceId();
                if (filename.startsWith("file:///android_asset/")) {
                    filename = filename.substring("file:///android_asset/".length());
                }
                Log.d("JS", "<" + filename + ":" + consoleMessage.lineNumber() + "> " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        this.webView.addJavascriptInterface(new JavaScriptBridge(this), "JavaScriptBridge");
        this.webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        this.sendMessage("SYS", "k 漢字");
    }

    private String urlEncode(String value) {
        int length = value.length();
        if (length == 0) return "";
        StringBuilder sb = new StringBuilder();
        sb.append((int)value.charAt(0));
        for (int i = 1; i < length; ++i) {
            sb.append(' ');
            sb.append((int)value.charAt(i));
        }
        return sb.toString();
    }

    private void sendMessage(String type, String msg) {
        this.webView.loadUrl("javascript:receiveMessage('" + urlEncode(type) + "', '" + urlEncode(msg) + "')");
    }

    void receiveMessage(String type, String msg) {
        Log.d(type, msg);
    }
}
