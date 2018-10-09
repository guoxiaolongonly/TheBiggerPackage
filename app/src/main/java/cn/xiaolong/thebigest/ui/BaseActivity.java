package cn.xiaolong.thebigest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.xiaolong.thebigest.dialog.LoadingDialog;
import cn.xiaolong.thebigest.presenter.BasePresenter;
import cn.xiaolong.thebigest.view.ILoadingView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:58
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements ILoadingView{
    private LoadingDialog loadingDialog;
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter=initPresenter();
    }

    public void showToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void showLoading(String text)
    {
        showLoadingDialog(text);
    }

    public void showLoadingDialog(String text) {
        showLoadingDialog(text, false);
    }

    public void showLoadingDialog(String loadText, boolean cancelable) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.show(this, loadText, cancelable);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        loadingDialog.setLoadText(loadText);
    }

    public void closeLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog("正在加载中..");
    }

    @Override
    public void showError(Throwable error) {
        showToast(error.getMessage());
    }

    protected T initPresenter(){
        return null;
    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null) {
            presenter.detachView();
        }
    }
}
