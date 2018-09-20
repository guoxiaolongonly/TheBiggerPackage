package cn.xiaolong.thebigest;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.xiaolong.thebigest.dialog.LoadingDialog;
import cn.xiaolong.thebigest.view.ILoadingView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:58
 */
public abstract class BaseActivity extends AppCompatActivity implements ILoadingView{
    private LoadingDialog loadingDialog;

    public void showToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    public void showLoading(String text)
    {

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

    }

    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }
}
