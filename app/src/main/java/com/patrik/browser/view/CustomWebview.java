package com.patrik.browser.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.patrik.browser.tool.LogUtils;
import com.patrik.browser.tool.UrlUtils;

/**
 * usage
 * Create by patrik on 2017/1/20.
 */
public class CustomWebview extends WebView {
    private String packageName = this.getClass().getPackage().toString();
    private String className = this.getClass().getSimpleName();
    private ProgressBar mProgressBar;

    public CustomWebview(Context context) {
        this(context, null);
    }

    public CustomWebview(Context context, AttributeSet attrs) {
        //can not be input zero for defStyleAttr(#patrik)2017/1/20.
//        this(context, attrs,0);
        this(context, attrs, android.R.attr.webTextViewStyle);
    }

    public CustomWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgressBar(context);
        initWebview();
    }

    private void initProgressBar(Context context) {
        //设置进度条(#patrik)2017/1/20.
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20, 0, 0));
        addView(mProgressBar);
    }

    private void initWebview() {
        WebSettings webSettings = this.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setSupportZoom(true);//是否可以缩放，默认true
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        //用户代理造成百度页面显示不全,暂未解决用户代理模式下的网页如何处理。
//        webSettings.setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        //java script
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        // access Assets and resources
        webSettings.setAllowFileAccess(true);
        //zoom page
        webSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        //set xml dom cache
        webSettings.setDomStorageEnabled(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //set cache
//        String appCachePath =getContext().getDir("netCache", Context.MODE_PRIVATE).getAbsolutePath();
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setAppCachePath(appCachePath);
//        webSettings.setAppCacheMaxSize(1024*1024*5);
        //优先缓存，会造成页面可能一直不是最新的.
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        this.setWebViewClient(new WebViewClientBase());
        this.setWebChromeClient(new WebChromeClientBase());
        this.onResume();
//        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public void loadUrl(String url) {
        if (UrlUtils.getInstance().isValidUrl(url)) {
            super.loadUrl(url);
        } else {
            super.loadUrl(UrlUtils.getInstance().recombineUrl(url));
        }
    }

    public void reload() {
        if (UrlUtils.getInstance().isValidUrl(getUrl())) {
            super.reload();
        } else {
            LogUtils.e(true, className, packageName, "Invalid URL,Fail to reload.");
        }
    }


    private class WebViewClientBase extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            LogUtils.e(className, packageName, "" + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            LogUtils.e(className, packageName, "" + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            LogUtils.e(className, packageName, "" + url);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub

            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url,
                                           boolean isReload) {
            // TODO Auto-generated method stub
            super.doUpdateVisitedHistory(view, url, isReload);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogUtils.e(className, packageName, error.toString(), error.getUrl());
//            View certificateView = (View) reflectObject.reflectInvokeMethod("inflateCertificateView,android.content.Context", getContext());
            //暂时通过认证.以实现网页加载,待后期优化体验.
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }
    }


    private class WebChromeClientBase extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // TODO Auto-generated method stub
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

    }
}
