package cn.xiaolong.thebigest.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.net.DataManager;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IBindView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/8 10:58
 */
public class BindPresenter extends BasePresenter<IBindView> {


    private String validateToken;
    private List<AccountInfo> accountInfos;

    public BindPresenter(Activity activity) {
        super(activity);
    }

    //为了保证流统一销毁和异常处理，请调用getSubscribe处理返回数据
    public void getMobileCode(String mobile, String captchaHash, String captchaValue) {
        DataManager.getMobileCode(mobile, captchaHash, captchaValue).subscribe(getSubscriber(s -> {
            this.validateToken = s.validate_token;
            mView.onGetSmsCodeSuccess(validateToken);
        }));
    }

    /**
     * @param accountInfo  传Account Info 是为了鉴别是哪个Account的信息好做绑定
     * @param mobile
     * @param validateCode
     */
    public void login(AccountInfo accountInfo, String mobile, String validateCode) {
        DataManager.loginByMobile(mobile, validateToken, validateCode).subscribe(getSubscriber(s -> {
            accountInfo.phoneNumber = mobile;
            mView.onLoginSuccess(accountInfo, s);
        }));
    }

    public void getCache() {
        String accountCache = (String) SPHelp.getAppParam(BuildConfig.KEY_ACCOUNT_CACHE, "");
        if (!TextUtils.isEmpty(accountCache)) {
            accountInfos = JSON.parseArray(accountCache, AccountInfo.class);
        } else {
            accountInfos = new ArrayList<>();
        }
        mView.onGetListSuccess(accountInfos);
    }

    public void cache(List<AccountInfo> accountInfoList) {
        SPHelp.setAppParam(BuildConfig.KEY_ACCOUNT_CACHE, JSON.toJSONString(accountInfoList));
        mView.cacheSuccess();
    }

}
