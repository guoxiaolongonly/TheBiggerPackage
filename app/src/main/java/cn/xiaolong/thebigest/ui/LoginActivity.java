package cn.xiaolong.thebigest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLDecoder;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.util.AccountInfoRandomGenerator;
import cn.xiaolong.thebigest.util.LogUtil;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/11 14:02
 */
public class LoginActivity extends BaseActivity {

    public static final int RESULT_ADD = 0X4564;
    private static final String url = "https://h5.ele.me/hongbao/#hardware_id=&is_lucky_group=True&lucky_number=9&track_id=&platform=4&sn=2a0423f08f39e0ab&theme_id=2905&device_id=&refer_user_id=20475540";
    private WebView wvLogin;
    private String cookieCache = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wvLogin = findViewById(R.id.wvLogin);
        setTitle("cookie获取");
        initWebSetting();
        setCookie();
        wvLogin.loadUrl(url);
    }

    private void setCookie() {
        String cookieStr = getIntent().getStringExtra("Cookie");
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(true);
        if (!TextUtils.isEmpty(cookieStr)) {
            CookieManager.getInstance().removeAllCookie();
            String[] cookies = cookieStr.split(";");
            for (String cookie : cookies) {
                CookieManager.getInstance().setCookie(url, cookie);
            }
            CookieSyncManager.getInstance().sync();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    private void initWebSetting() {
        wvLogin.setWebViewClient(new WebViewClient() {

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                CookieManager cookieManager = CookieManager.getInstance();
//                String cookie = cookieManager.getCookie(url);
//                Log.i("override", "onPageFinished: " + cookie);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
//                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

                CookieManager cookieManager = CookieManager.getInstance();
                cookieCache = cookieManager.getCookie(url);
                Log.i("finish", "onPageFinished: " + cookieCache);
                super.onPageFinished(view, url);
            }
        });
        wvLogin.getSettings().setJavaScriptEnabled(true);                    //支持Javascript 与js交互
        wvLogin.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        wvLogin.getSettings().setAllowFileAccess(true);                      //设置可以访问文件
        wvLogin.getSettings().setSupportZoom(true);                          //支持缩放
        wvLogin.getSettings().setBuiltInZoomControls(true);                  //设置内置的缩放控件
        wvLogin.getSettings().setUseWideViewPort(true);                      //自适应屏幕
        wvLogin.getSettings().setSupportMultipleWindows(true);               //多窗口
        wvLogin.getSettings().setDefaultTextEncodingName("utf-8");            //设置编码格式
        wvLogin.getSettings().setDomStorageEnabled(true);
        wvLogin.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);       //缓存模式
//        wvLogin.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        wvLogin.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1; m1 metal Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043409 Safari/537.36 V1ANDSQ7.2.5744YYBD QQ/7.2.5.3305 NetType/WIFI WebP/0.3.0 Pixel/1080");
        LogUtil.d(wvLogin.getSettings().getUserAgentString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
//            startActivityForResult(new Intent(this,LoginActivity.class));
            saveData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        //保存Cookie
        if (TextUtils.isEmpty(cookieCache) || (cookieCache.indexOf("snsInfo[wx2a416286e96100ed]=") == -1
                && cookieCache.indexOf("snsInfo[101204453]=") == -1) || (cookieCache.indexOf("USERID=") == -1
                || cookieCache.indexOf("SID=") == -1)) {
            showToast("未获取到正确的cookie信息，请登录完QQ之后绑定手机号码");
            wvLogin.reload();
            return;
        }
        cookieCache = URLDecoder.decode(cookieCache);
        String openid = "";
        if (cookieCache.contains("openid\":\"")) {
            openid = cookieCache.split("openid\":\"")[1].split("\",")[0];
        }
        String sign = "";
        if (cookieCache.contains("eleme_key\":\"")) {
            sign = cookieCache.split("eleme_key\":\"")[1].split("\",")[0];
        }
        String nickName = "";
        if (cookieCache.contains("nickname\":\"")) {
            nickName = cookieCache.split("nickname\":\"")[1].split("\",")[0];
        }

        AccountInfo accountInfo = new AccountInfo(cookieCache, sign, openid, nickName);
        AccountInfoRandomGenerator.generate(accountInfo);
        String sid = "";
        if (cookieCache.contains("nickname\":\"")) {
            sid = cookieCache.split("SID=")[1];
        }
        accountInfo.sid = sid;
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("accountInfo", accountInfo);
        intent.putExtras(bundle);
        setResult(RESULT_ADD, intent);
        clearWebViewCache();
        finish();
    }


    public void clearWebViewCache() {
        CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public static Bundle buildBundle(String cookie) {
        Bundle bundle = new Bundle();
        bundle.putString("Cookie", cookie);
        return bundle;
    }

//    /**
//     * 逻辑处理
//     * @author linzewu
//     */
//    final class InJavaScriptLocalObj {
//        @JavascriptInterface
//        public void getSource(String html) {
//            Log.d("html=", html);
//        }
//    }
}
