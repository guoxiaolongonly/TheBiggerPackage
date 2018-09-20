package cn.xiaolong.thebigest.net;

import cn.xiaolong.thebigest.ApiService;
import cn.xiaolong.thebigest.App;
import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.net.cookie.CookiesManager;
import cn.xiaolong.thebigest.util.SPHelp;
import okhttp3.Request;


/**
 * <网络层处理，负责创建ApiService,
 * 和固定参数构建>
 *
 * @data: 2016/7/7 11:28
 * @version: V1.0
 */
public class Dao {
    private static ApiService mApiService;

    public static ApiService getMainApiService() {
        if (mApiService == null) {
            synchronized (Dao.class) {
                if (mApiService == null) {
                    mApiService = RetrofitHolder.buildRetrofit(builder -> buildPublicParams(builder),new CookiesManager(App.getContext())).create(ApiService.class);
                }
            }
        }
        return mApiService;
    }


    /**
     * url固定参数构建
     * 这边构建头部。。
     * @param builder
     * @return
     */
    private static Request.Builder buildPublicParams(Request.Builder builder) {
        builder.addHeader("referer","https://h5.ele.me/hongbao/")
                .addHeader("origin","https://h5.ele.me")
                .addHeader("user-agent","Mozilla/5.0 (Linux; Android 7.0; MIX Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044004 Mobile Safari/537.36 V1_AND_SQ_7.5.0_794_YYB_D QQ/7.5.0.3430 NetType/WIFI WebP/0.3.0 Pixel/1080");

        //构建x-shard
        String cacheSN= SPHelp.getAppParam(BuildConfig.KEY_SN,"").toString();
        builder.addHeader("x-shard", "".equals(cacheSN)?Integer.parseInt("2a0423f08f39e0ab",16)+"":Integer.parseInt(cacheSN,16)+"");
        return builder;
    }


}
