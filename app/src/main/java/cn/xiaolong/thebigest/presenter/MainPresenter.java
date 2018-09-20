package cn.xiaolong.thebigest.presenter;

import android.app.Activity;

import cn.xiaolong.thebigest.util.LogUtil;
import cn.xiaolong.thebigest.view.IMainView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:57
 */
public class MainPresenter extends BasePresenter<IMainView>{

    public MainPresenter(Activity activity) {
        super(activity);
    }
    public void getCheckAndParseLuckyPackage(String redPackageUrl)
    {
        LogUtil.d(redPackageUrl);
    }

}
