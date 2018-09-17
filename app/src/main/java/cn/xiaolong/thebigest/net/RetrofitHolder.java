package cn.xiaolong.thebigest.net;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.xiaolong.thebigest.BuildConfig;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Retrofit的封装类
 *
 * @author lC
 * @version 1.0
 */
public class RetrofitHolder {

    private Retrofit mRetrofit;

    public static Retrofit buildRetrofit(IBuildPublicParams iBuildPublicParams, CookieJar cookieJar) {
        return new RetrofitHolder(iBuildPublicParams, cookieJar).mRetrofit;
    }

    public static Retrofit buildRetrofit(IBuildPublicParams iBuildPublicParams) {
        return new RetrofitHolder(iBuildPublicParams, new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return new ArrayList<>();
            }
        }).mRetrofit;
    }


    private RetrofitHolder(IBuildPublicParams iBuildPublicParams, CookieJar cookieJar) {
        if (mRetrofit == null) {
            if (NetworkConfig.getBaseUrl() == null || NetworkConfig.getBaseUrl().trim().equals("")) {
                throw new RuntimeException("网络模块必须设置在Application处调用 请求的地址 调用方法：NetworkConfig.setBaseUrl(String url)");
            }
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(BuildConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(BuildConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(BuildConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .addInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY))
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(NetworkConfig.getBaseUrl())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public interface IBuildPublicParams {
        Request.Builder buildPublicParams(Request.Builder builder);
    }
}
