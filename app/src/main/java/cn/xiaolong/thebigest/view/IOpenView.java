package cn.xiaolong.thebigest.view;

import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.entity.OpenPackageInfo;

/**
 * <拆红包专用View>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/19 11:55
 */
public interface IOpenView extends ILoadingView {

    /**
     * 获取红包唯一识别sn码
     *
     */
    void getIdSuccess(String id);

    /**
     * 点击成功
     *  @param accountInfo
     * @param packageInfo
     */
    void openSuccess(AccountInfo accountInfo, OpenPackageInfo packageInfo);

}
