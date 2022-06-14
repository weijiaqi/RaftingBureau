package com.drifting.bureau.view.customWebview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CommWebChromeClient extends WebChromeClient {
    private WebLoadingListener loadingListener;

    public CommWebChromeClient(WebLoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }



    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (loadingListener != null) {
            loadingListener.startProgress(newProgress);
        }
    }
}
