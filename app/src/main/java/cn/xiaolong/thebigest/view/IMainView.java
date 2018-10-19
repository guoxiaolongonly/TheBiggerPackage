package cn.xiaolong.thebigest.view;

import java.util.List;

import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 17:16
 */
public interface IMainView  extends ILoadingView {

    /**
     * 获取红包列表成功
     * @param accountInfoList
     */
    void onGetListSuccess(List<AccountInfo> accountInfoList);

    /**
     * 缓存成功
     */
    void cacheSuccess();
}
