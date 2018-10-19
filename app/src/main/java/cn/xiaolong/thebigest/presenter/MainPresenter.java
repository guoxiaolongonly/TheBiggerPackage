package cn.xiaolong.thebigest.presenter;

import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.entity.ErrorThrowable;
import cn.xiaolong.thebigest.entity.PackageInfo;
import cn.xiaolong.thebigest.net.DataManager;
import cn.xiaolong.thebigest.ui.MainActivity;
import cn.xiaolong.thebigest.util.Constant;
import cn.xiaolong.thebigest.util.FileUtil;
import cn.xiaolong.thebigest.util.UrlParserHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.xiaolong.thebigest.util.Constant.ERROR_PACKAGE;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:57
 */
public class MainPresenter extends BasePresenter<MainActivity> {
    public MainPresenter(MainActivity activity) {
        super(activity);
    }

    public void getCheckAndParseLuckyPackage(String redPackageUrl) {
        String result = UrlParserHelper.parserUrl(redPackageUrl);
        if (TextUtils.isEmpty(result)) {
            mView.showError(new ErrorThrowable(ERROR_PACKAGE, "红包地址错误"));
        } else if (result.startsWith("id=")) {
            mView.getIdSuccess(result.replace("id=", ""));
        } else if (result.startsWith("sn=")) {
            mView.getSnSuccess(result.replace("sn=", ""));
        }

    }

    /**
     * 获取幸运红包
     *
     * @param sn
     */
    public void getLuckyNumber(String sn) {
        DataManager.getLuckyNumber(sn).subscribe(getSubscriberNoProgress(s -> {
            if (s.contains("lucky_number\": ")) {
                String luckyNumber = s.split("lucky_number\": ")[1].split(",")[0];
                mView.onGetLuckyNumberSuccess(luckyNumber);
            }
        }));
    }

    /**
     * 点红包
     *
     * @param sn          红包唯一识别码
     * @param accountInfo 红包信息
     */
    public void touchPackage(String sn, AccountInfo accountInfo) {
        if (TextUtils.isEmpty(accountInfo.sid)) {
            if (accountInfo.cookie.contains("nickname\":\"")) {
                accountInfo.sid = accountInfo.cookie.split("SID=")[1];
            } else {
                mView.showError(new Throwable("cookie保存异常，请重新绑定该账号cookie，" + accountInfo.QQ));
                return;
            }
        }
        String cookie = "SID=" + accountInfo.sid;
        DataManager.touchRedPackage(cookie, accountInfo.openId, "", sn, "", accountInfo.method, accountInfo.phoneNumber,
                "0", accountInfo.sign, "", accountInfo.unionId, accountInfo.headerurl, accountInfo.nickname)
                .subscribe(getSubscriber(s ->
                        mView.touchSuccess(accountInfo, s)
                ));
    }

    public void openPackage(String id, AccountInfo accountInfo) {
        Map<String, String> cookies = getCookieInfo(accountInfo.cookie);
        StringBuffer cookieBuffer = new StringBuffer();
        String userid = cookies.get("USERID");
        String ubtssid = cookies.get("ubt_ssid");
        String sid = cookies.get("SID");
        cookieBuffer.append("USERID=").append(userid).append(";")
                .append("ubt_ssid=").append(ubtssid).append(";")
                .append("SID=").append(sid);
        DataManager.openPackage(cookieBuffer.toString(), accountInfo.headerurl, "10001",
                "24.4946534", "118.1742511", accountInfo.nickname, id, userid)
                .subscribe(getSubscriber(s -> {
                    mView.openSuccess(accountInfo, s);
                }));
    }


    public void bigTouch(String sn, AccountInfo accountInfo) {
        if (TextUtils.isEmpty(accountInfo.sid)) {
            if (accountInfo.cookie.contains("nickname\":\"")) {
                accountInfo.sid = accountInfo.cookie.split("SID=")[1];
            } else {
                mView.showError(new Throwable("大号cookie保存异常，请重新绑定该账号cookie，" + accountInfo.QQ));
                return;
            }
        }
        String cookie = "SID=" + accountInfo.sid;
        DataManager.touchRedPackage(cookie, accountInfo.openId, "", sn, "", accountInfo.method, accountInfo.phoneNumber,
                "0", accountInfo.sign, "", accountInfo.unionId, accountInfo.headerurl, accountInfo.nickname)
                .subscribe(new Observer<PackageInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(PackageInfo packageInfo) {
                        mView.bigTouchSuccess(accountInfo, packageInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.bigTouchFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCache() {
        String accountCache = FileUtil.loadDataFromFile(Environment.getExternalStorageDirectory().getPath() + "/cache/", Constant.CACHE_FILE_SMALL);
        List<AccountInfo> accountInfoList;
        if (!TextUtils.isEmpty(accountCache)) {
            accountInfoList = JSON.parseArray(accountCache, AccountInfo.class);
            Collections.sort(accountInfoList, (o1, o2) -> o1.allTimeCount - o2.allTimeCount);
        } else {
            accountInfoList = new ArrayList<>();
        }
        mView.onGetListSuccess(accountInfoList);
    }

    public void resetPerDayCount(List<AccountInfo> accountInfos) {
        for (AccountInfo accountInfo : accountInfos) {
            accountInfo.perDaycount = 0;
        }
        cache(accountInfos);
    }


    public void cache(List<AccountInfo> accountInfoList) {
        FileUtil.saveDataToFile(JSON.toJSONString(accountInfoList), Environment.getExternalStorageDirectory().getPath() + "/cache/", Constant.CACHE_FILE_SMALL);
        mView.cacheSuccess();
    }


    public HashMap<String, String> getCookieInfo(String cookies) {
        HashMap<String, String> cookieMaps = new HashMap<>();
        String[] cookieArray = cookies.split(";");
        for (String cookie : cookieArray) {
            String[] result = cookie.split("=");
            cookieMaps.put(result[0].trim().intern(), result[1]);
        }
        return cookieMaps;
    }
}
