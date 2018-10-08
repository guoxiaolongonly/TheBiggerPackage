package cn.xiaolong.thebigest.net;


import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 14:28
 */
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
     * "show":0
     * },
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
        return Dao.getMainApiService().getLuckyNumber(sn).flatMap(responseBody -> {
            return Observable.just(responseBody.string());
        }).compose(observableTransformer());
    }

    public static Observable<String> getMobileCode(String mobile, String captchaHash
            , String captchaValue) {
        return Dao.getMainApiService().getMobileCode(mobile,captchaHash,captchaValue).flatMap(responseBody -> {
            return Observable.just(responseBody.string());
        }).compose(observableTransformer());
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
            return Observable.error(throwable);
        };
    }
}
