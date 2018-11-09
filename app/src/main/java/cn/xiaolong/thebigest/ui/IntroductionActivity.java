package cn.xiaolong.thebigest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.xiaolong.thebigest.R;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/19 17:04
 */
public class IntroductionActivity extends BaseActivity {
    private WebView wvLogin;
    private static final String url = "https://github.com/guoxiaolongonly/TheBiggerPackage/blob/master/README.md";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("説明文檔");
        wvLogin = findViewById(R.id.wvLogin);
        initWebSetting();
        wvLogin.loadUrl(url);
    }

    private void initWebSetting() {
        wvLogin.getSettings().setJavaScriptEnabled(true);                    //支持Javascript 与js交互
        wvLogin.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        wvLogin.getSettings().setAllowFileAccess(true);                      //设置可以访问文件
        wvLogin.getSettings().setSupportZoom(true);                          //支持缩放
        wvLogin.getSettings().setBuiltInZoomControls(true);                  //设置内置的缩放控件
        wvLogin.getSettings().setUseWideViewPort(true);                      //自适应屏幕
        wvLogin.getSettings().setSupportMultipleWindows(true);               //多窗口
        wvLogin.getSettings().setDefaultTextEncodingName("utf-8");            //设置编码格式
        wvLogin.getSettings().setDomStorageEnabled(true);
        wvLogin.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);       //缓存模式
        wvLogin.setWebViewClient(new WebViewClient() {

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (wvLogin.canGoBack()) {
            wvLogin.goBack();
        } else {
            super.onBackPressed();
        }

    }
}
