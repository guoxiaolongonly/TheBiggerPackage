package cn.xiaolong.thebigest.net;

import cn.xiaolong.thebigest.ApiService;
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
                    mApiService = RetrofitHolder.buildRetrofit(builder -> buildPublicParams(builder)).create(ApiService.class);
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
        return builder;
    }


}
