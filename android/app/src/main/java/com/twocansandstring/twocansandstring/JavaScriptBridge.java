package com.twocansandstring.twocansandstring;

import android.webkit.JavascriptInterface;

public class JavaScriptBridge {

    private TwoCansWebViewActivity activity;

    public JavaScriptBridge(TwoCansWebViewActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void onSendNativeMessage(String type, String rawValue) {
        this.activity.receiveMessage(type, rawValue);
    }
}
