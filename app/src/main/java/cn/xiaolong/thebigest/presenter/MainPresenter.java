package cn.xiaolong.thebigest.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.net.DataManager;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IMainView;

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
                mView.getSnAndLuckyNumSuccess("", "");
            }
        } else {
            mView.getSnAndLuckyNumSuccess("", "");
        }
    }

    /**
     * 获取幸运红包
     *
     * @param sn
     */
    public void getLuckyNumber(String sn) {
        DataManager.getLuckyNumber(sn).subscribe(getSubscriber(s -> {
            mView.onGetLuckyNumberSuccess(s);
        }));
    }


    public void touchPackage(String sn, AccountInfo accountInfo) {
        String cookie = accountInfo.openId;
        DataManager.touchRedPackage(cookie, accountInfo.openId, "", sn, "", accountInfo.phoneNumber,
                "0", accountInfo.sign, "", accountInfo.unionId, accountInfo.headerurl, accountInfo.nickname)
                .subscribe(getSubscriber(s -> mView.touchSuccess(s)));
    }

    public void getCache() {
        String accountCache = (String) SPHelp.getAppParam(BuildConfig.KEY_ACCOUNT_CACHE, "");
        List<AccountInfo> accountInfoList;
        if (!TextUtils.isEmpty(accountCache)) {
           accountInfoList = JSON.parseArray(accountCache, AccountInfo.class);
        } else {
            accountInfoList = new ArrayList<>();
        }
        mView.onGetListSuccess(accountInfoList);
    }
}
