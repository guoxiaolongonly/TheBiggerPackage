package cn.xiaolong.thebigest;

import android.app.Application;
import android.content.Context;

import cn.xiaolong.thebigest.net.NetworkConfig;
import cn.xiaolong.thebigest.util.SPHelp;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 14:55
 */
public class App extends Application {
    private static Context baseContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SPHelp.init(this);
        baseContext = this;
        NetworkConfig.setBaseUrl(BuildConfig.HOST_URL);
    }

    public static Context getContext() {
        return baseContext;
    }
}
