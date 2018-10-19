package cn.xiaolong.thebigest.view;

import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.entity.PackageInfo;

/**
 * <点红包专用>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/19 11:54
 */
public interface ITouchView extends ILoadingView {
    /**
     * 获取最大红包
     * @param luckyNumber
     */
    void onGetLuckyNumberSuccess(String luckyNumber);

    /**
     * 获取红包唯一识别sn码
     * @param sn
     */
    void getSnSuccess(String sn);

    /**
     * 点击成功
     * @param accountInfo
     * @param position
     */
    void touchSuccess(AccountInfo accountInfo, PackageInfo position);

    /**
     * 大号拆解成功
     * @param accountInfo
     * @param packageInfo
     */
    void bigTouchSuccess(AccountInfo accountInfo,PackageInfo packageInfo);

    /**
     * 大红包点击失败
     * @param e
     */
    void bigTouchFail(Throwable e);
}
