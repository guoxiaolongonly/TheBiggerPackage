package cn.xiaolong.thebigest.view;



/**
 * <请描述这个类是干什么的>
 *
 * @version: V1.0
 */
public interface ILoadingView {
    void showLoading();

    void showError(Throwable error);

    void hideLoading();
}
