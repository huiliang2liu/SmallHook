package com.witget;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * com.witget
 * 2018/10/29 17:50
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class WebView extends android.webkit.WebView {
    private WebChromeClient.CustomViewCallback myCallback = null;
    boolean loadErr = false;
    boolean loaded = false;
    private View myView = null;
    private LoadListener load;
    private ScreenListener screen;
    private JsListener js;
    private Map<String, CallBack> backMap;

    {
        initWebViewSettings();
        initWebViewClient();
        initWebChromeClient();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            android.webkit.WebView.setWebContentsDebuggingEnabled(true);
        }
        addJavascriptInterface(new JavaScripte(), "javaScript");
        backMap = new HashMap<>();
    }

    public void setScreenListener(ScreenListener screen) {
        this.screen = screen;
    }

    public void setLoadListener(LoadListener load) {
        this.load = load;
    }

    public void setJsListener(JsListener js) {
        this.js = js;
    }

    public WebView(Context context) {
        super(context.getApplicationContext());
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context.getApplicationContext(), attrs, defStyleAttr);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context.getApplicationContext(), attrs, defStyleAttr, defStyleRes);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context.getApplicationContext(), attrs, defStyleAttr, privateBrowsing);
    }

    private void initWebChromeClient() {
        setWebChromeClient(new WebChromeClient() {
            @Nullable
            @Override
            public Bitmap getDefaultVideoPoster() {
                return super.getDefaultVideoPoster();
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {//全屏显示
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                    return;
                }
                ViewGroup parent = (ViewGroup) getParent();
                parent.removeView(WebView.this);
                parent.addView(view);
                myView = view;
                myCallback = callback;
                fullScreen();
            }

            @Override
            public void onHideCustomView() {
                if (myView != null) {
                    if (myCallback != null) {
                        myCallback.onCustomViewHidden();
                        myCallback = null;
                    }
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);
                    parent.addView(WebView.this);
                    myView = null;
                    exitFullScreen();
                }
            }

            @Override
            public boolean onShowFileChooser(android.webkit.WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
        });
    }

    private void initWebViewClient() {
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                if (!url.startsWith("http") && !url.startsWith("https") && !url.startsWith("ftp")) {
                    notWeb(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedHttpError(android.webkit.WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                err();
            }

            @Override
            public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                err();
            }

            @Override
            public void onReceivedError(android.webkit.WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                err();
            }

            @Override
            public void onLoadResource(android.webkit.WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadErr)
                    return;
                loaded = true;
//                setVisibility(View.VISIBLE);
                Log.e("MainActivity", "网页加载完成");
                success();
            }
        });
    }

    private void initWebViewSettings() {
        WebSettings s = getSettings();
        s.setBuiltInZoomControls(true);
        s.setPluginState(WebSettings.PluginState.ON);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setRenderPriority(WebSettings.RenderPriority.HIGH);
        s.setAppCacheEnabled(false);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(false);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setLoadsImagesAutomatically(true);
        s.setSupportZoom(false);// ql
        s.setBuiltInZoomControls(false);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("http://www.cvbaoli.com/webak/public/showAgreement");
        s.setDomStorageEnabled(true);
        //如果大于5.0设置混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            s.setDisplayZoomControls(false);
        }
    }

    public void loadData(String data) {
        loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
    }

    public void js(String metod) {
        loadUrl(String.format("javascript:%s()", metod));
    }

    public void js(String metod, @NonNull String param) {
        loadUrl(String.format("javascript:%s('%s')", metod, param));
    }

    public void js(String metod, @NonNull List<String> params) {
        StringBuffer sb = new StringBuffer("'");
        for (String param : params)
            sb.append(param).append("','");
        loadUrl(String.format("javascript:%s(%s)", metod, sb.substring(0, sb.length() - 2)));
    }

    public void js(String metod, CallBack back) {
        if (back != null)
            backMap.put(metod, back);
        js(metod);
    }
    public void js(String metod, @NonNull String param, CallBack back) {
        if (back != null)
            backMap.put(metod, back);
        js(metod, param);
    }
    public void js(String metod,@NonNull List<String> params, CallBack back) {
        if (back != null)
            backMap.put(metod, back);
        js(metod, params);
    }

    private void fullScreen() {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    fullScreen();
                }
            });
            return;
        }
        if (screen != null)
            screen.fullScreen();
    }

    private void exitFullScreen() {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    exitFullScreen();
                }
            });
            return;
        }
        if (screen != null)
            screen.exitFullScreen();
    }


    private void data(final String method, final String... params) {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    data(method, params);
                }
            });
            return;
        }
        if (backMap.containsKey(method)) {
            CallBack back = backMap.get(method);
            back.back(params);
            backMap.remove(method);
        }
    }

    private void method(final String method, final String... params) {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    method(method, params);
                }
            });
            return;
        }
        if (js != null)
            js.jsLoad(method, params);
    }

    private void notWeb(final String url) {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    notWeb(url);
                }
            });
            return;
        }
        if (load != null)
            load.notWeb(url);
    }

    private void err() {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    err();
                }
            });
            return;
        }
        if (loaded)
            return;
        loadErr = true;
        if (load != null)
            load.err();
    }

    private void success() {
        if (!isMain()) {
            post(new Runnable() {
                @Override
                public void run() {
                    success();
                }
            });
            return;
        }
        if (load != null)
            load.success();
    }

    private boolean isMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    private class JavaScripte {

        @JavascriptInterface
        public void method(String method, String... params) {
            WebView.this.method(method, params);
        }

        @JavascriptInterface
        public void data(String method, String... params) {
            WebView.this.data(method, params);
        }
    }

    public static interface ScreenListener {
        void fullScreen();

        void exitFullScreen();
    }

    public static interface LoadListener {
        void err();

        void success();

        void notWeb(String url);
    }

    public static interface CallBack {
        void back(String... params);
    }

    public static interface JsListener {
        void jsLoad(String method, String... params);
    }
}
