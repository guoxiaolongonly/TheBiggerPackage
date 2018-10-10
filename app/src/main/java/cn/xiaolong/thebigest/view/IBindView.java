package cn.xiaolong.thebigest.view;

import java.util.List;

import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/8 10:59
 */
public interface IBindView extends ILoadingView {

    void cacheSuccess();
    void onGetListSuccess(List<AccountInfo> accountInfos);

    void onGetSmsCodeSuccess(String result);

    void onLoginSuccess(AccountInfo accountInfo, AccountInfo result);
}
