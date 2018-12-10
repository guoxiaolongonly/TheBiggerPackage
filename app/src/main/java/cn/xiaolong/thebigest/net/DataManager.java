package cn.xiaolong.thebigest.net;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.nio.charset.Charset;

import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.entity.ErrorResponse;
import cn.xiaolong.thebigest.entity.ErrorThrowable;
import cn.xiaolong.thebigest.entity.OpenPackageInfo;
import cn.xiaolong.thebigest.entity.PackageInfo;
import cn.xiaolong.thebigest.entity.TokenBean;
import cn.xiaolong.thebigest.util.Constant;
import cn.xiaolong.thebigest.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 14:28
 */
@Deprecated
public class DataManager {

    /**
     * @param sn
     * @return Json如下，这里直接打印String了 有需要可以自己去解析
     * {
     * "comment":"",
     * "status":1,
     * "app_icon_hash":"",
     * "subtitle":"最高10元大红包，饿了么拼手气@你",
     * "lucky_number":6,
     * "updated_at":1514359555,
     * "background":"cd7f51827ceaf3ffce33e73773bd7fdfjpeg",
     * "id":569,
     * "icon":"c65eb8f34b4bc9ac315aaa621e0a76b2jpeg",
     * "name":"01-12 打底红包",
     * "title":"饿了么拼手气，第6个领取的人得大红包",
     * "created_at":1494923988,
     * "is_lucky_group":true,
     * "color_map":{
     * "panelBackgroundOpacity":10,
     * "color":"ffffff",
     * "captionBackgroundOpacity":20,
     * "updated_at":1494923987,
     * "icon_url":"https://fuss10.elemecdn.com/5/bc/dba7f4cb636052f5958fc2518e719jpeg.jpeg",
     * "backgroundColor":"d62f1f",
     * "panelBackgroundColor":"000000",
     * "usernameColor":"ffe600",
     * "recordDateColor":"ffffff",
     * "headlineColor":"ffffff",
     * "extensionButtonVisible":"0",
     * "accountColor":"3190e8",
     * "panelColor":"ffec00",
     * "background_url":"https://fuss10.elemecdn.com/c/d7/f51827ceaf3ffce33e73773bd7fdfjpeg.jpeg",
     * "defineBtn":{
     * },
     * "show":0
     * "panelTitleColor":"ffffff",
     * "captionColor":"ffffff",
     * "captionBackgroundColor":"000000",
     * "headlineTextColor":"ffffff",
     * "created_at":1494923987,
     * "recordMessageColor":"ffffff",
     * "captionText":"来饿了么 优惠不会停",
     * "amountColor":"ffe600"
     * }
     * }
     */
    public static Observable<String> getLuckyNumber(String sn) {
        return Dao.getMainApiService().getLuckyNumber(sn)
                .flatMap(responseBody -> {
                    return Observable.just(responseBody.string());
                })
                .compose(observableTransformer());
    }

    public static Observable<TokenBean> getMobileCode(String mobile, String captchaHash
            , String captchaValue) {
        return Dao.getMainApiService().getMobileCode(mobile, captchaHash, captchaValue)
                .flatMap(responseBody -> {
                    if (responseBody == null || TextUtils.isEmpty(responseBody.validate_token)) {
                        return Observable.error(new Throwable("Response Error"));
                    } else {
                        return Observable.just(responseBody);
                    }
                })
                .compose(observableTransformer());
    }

    public static Observable<AccountInfo> loginByMobile(String mobile, String validateToken
            , String validateCode) {
        return Dao.getMainApiService()
                .loginByMobile(mobile, validateToken, validateCode)
                .flatMap(responseBody ->
                        {
                            LogUtil.d(responseBody.toString());
                            return Observable.just(responseBody);
                        }
                )
                .compose(observableTransformer());
    }


    public static Observable<PackageInfo> touchRedPackage(String cookie,
                                                          String openId,
                                                          String device_id,
                                                          String sn,
                                                          String hardware_id,
                                                          String method,
                                                          String phone,
                                                          String platform,
                                                          String sign,
                                                          String track_id,
                                                          String unionid,
                                                          String weixin_avatar,
                                                          String weixin_username) {
        return Dao.getMainApiService()
                .touchRedPackage(cookie, openId, device_id, sn, hardware_id, method, phone, platform, sign, track_id, unionid, weixin_avatar, weixin_username)
                .flatMap(responseBody -> {
                    LogUtil.d(responseBody.toString());
                    return Observable.just(responseBody);
                })
                .compose(observableTransformer());
    }

    public static Observable<OpenPackageInfo> openPackage(String cookie,
                                                          String avatar,
                                                          String backId,
                                                          String lat,
                                                          String lng,
                                                          String nickname,
                                                          String packet_id,
                                                          String user_id) {
        return Dao.getMainApiService()
                .openPackage(cookie, avatar, backId, lat, lng, nickname, packet_id, user_id)
                .flatMap(responseBody -> {
                    if ("200".equals(responseBody.code)) {
                        return Observable.just(responseBody.data);
                    } else {
                        return Observable.error(new ErrorThrowable(Constant.ERROR_RESPONSE, "Response错误：" + responseBody.message));
                    }

                })
                .compose(observableTransformer());
    }


    /**
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> observableTransformer() {
        return tObservable -> tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(errorResumeFunc());
    }

    private static <T> Function<Throwable, Observable<? extends T>> errorResumeFunc() {
        return throwable -> {
            throwable.printStackTrace();
            if (throwable instanceof HttpException) {
                ResponseBody errorBody = ((HttpException) throwable).response().errorBody();
                //如果不是网络错误可能会是本地错误
                ErrorResponse errorResponse = new ErrorResponse("未知错误，可联系作者反馈。", ((HttpException) throwable).message(), 0x66);
                String reuslt = errorBody.source().readString(Charset.forName("UTF-8"));
                if (errorBody != null && errorBody.contentLength() > 0) {
                    errorResponse = JSON.parseObject(reuslt, ErrorResponse.class);
                    switch (errorResponse.name) {
                        case "PHONE_IS_EMPTY":
                            //帐号失效
                            errorResponse.errorCode = Constant.ERROR_INVALID;
                            break;
                        case "TOO_BUSY":
                            //操作频繁
                            errorResponse.errorCode = Constant.ERROR_BUSY;
                            break;
                        case "UNAUTHORIZED":
                            //操作频繁
                            errorResponse.errorCode = Constant.ERROR_UN_LOGIN;
                            break;
                        case "SOA_TIMEOUT":
                            //操作频繁
                            errorResponse.errorCode = Constant.ERROR_OUT_OF_TIME;
                            break;
                        default:
                            errorResponse.errorCode = Constant.ERROR_UN_KNOWN;
                    }
                }
                return Observable.error(new ErrorThrowable(errorResponse.errorCode, errorResponse.message));
            } else if (throwable instanceof ErrorThrowable) {
                return Observable.error(throwable);
            } else {
                return Observable.error(new ErrorThrowable(Constant.ERROR_UN_KNOWN, throwable.getMessage()));
            }
        };

    }


}
