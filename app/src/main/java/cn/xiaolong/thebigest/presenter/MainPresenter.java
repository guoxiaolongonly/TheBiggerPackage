package cn.xiaolong.thebigest.presenter;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.entity.ErrorThrowable;
import cn.xiaolong.thebigest.net.DataManager;
import cn.xiaolong.thebigest.util.FileUtil;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IMainView;

import static cn.xiaolong.thebigest.util.Constant.ERROR_PACKAGE;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:57
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Activity activity) {
        super(activity);
    }

    public void getCheckAndParseLuckyPackage(String redPackageUrl) {
        redPackageUrl.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        redPackageUrl = URLDecoder.decode(redPackageUrl);
        if (redPackageUrl.startsWith("https://") && redPackageUrl.contains("https://h5.ele.me/hongbao/")) {
            //说明应该是红包链接了
            Map<String, String> paramMap = new HashMap<>();
            String parmas = redPackageUrl.split("https://h5.ele.me/hongbao/")[1];
            String[] datas = parmas.split("&");
            for (String data : datas) {
                String[] param = data.split("=");
                if (param.length > 1) {
                    paramMap.put(param[0], param[1]);
                }
            }
            if (paramMap.containsKey("sn") && paramMap.containsKey("lucky_number")) {
                mView.getSnAndLuckyNumSuccess(paramMap.get("sn"), paramMap.get("lucky_number"));
            } else {
                mView.showError(new ErrorThrowable(ERROR_PACKAGE, "红包地址错误"));
            }
        } else {
            mView.showError(new ErrorThrowable(ERROR_PACKAGE, "红包地址错误"));
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
                        mView.touchSuccess(accountInfo,s)
                ));
    }

    public void getCache() {

        String accountCache = FileUtil.loadDataFromFile(Environment.getExternalStorageDirectory().getPath()+"/cache/","hbCache.txt");
        List<AccountInfo> accountInfoList;
        if (!TextUtils.isEmpty(accountCache)) {
            accountInfoList = JSON.parseArray(accountCache, AccountInfo.class);
            Collections.sort(accountInfoList, (o1, o2) -> o1.allTimeCount - o2.allTimeCount);
        } else {
            accountInfoList = new ArrayList<>();
        }
        mView.onGetListSuccess(accountInfoList);
    }

    public void cache(List<AccountInfo> accountInfoList) {
        FileUtil.saveDataToFile(JSON.toJSONString(accountInfoList),Environment.getExternalStorageDirectory().getPath()+"/cache/","hbCache.txt");
        mView.cacheSuccess();
    }
}
