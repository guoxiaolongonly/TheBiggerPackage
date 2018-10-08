package cn.xiaolong.thebigest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.adapter.SmallAccountAdapter;
import cn.xiaolong.thebigest.dialog.AccountAddDialog;
import cn.xiaolong.thebigest.dialog.SmsDialog;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.presenter.BindPresenter;
import cn.xiaolong.thebigest.util.LogUtil;
import cn.xiaolong.thebigest.view.IBindView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 16:49
 */

public class BindActivity extends BaseActivity<BindPresenter> implements IBindView {
    private RecyclerView rvContent;
    private SmallAccountAdapter smallAccountAdapter;
    private AccountAddDialog accountAddDialog;
    private SmsDialog smsDialog;
    @Override
    protected BindPresenter initPresenter() {
        return new BindPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        setTitle("小号列表");
        initView();
        initData();
        setListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    private void setListener() {
        smallAccountAdapter.setOnDeleteClickListener(v -> {
            AccountInfo accountInfo= (AccountInfo) v.getTag();
            smallAccountAdapter.remove(accountInfo);
            showToast("删除成功！");
            presenter.cache(smallAccountAdapter.getItems());
        });
        smallAccountAdapter.setOnGetSidClick(v -> {
//            AccountInfo accountInfo = (AccountInfo) v.getTag();
//            presenter.getQrCode(accountInfo);

        });

        smallAccountAdapter.setOnItemClickListener(v -> {
            accountAddDialog.setAccountInfo((AccountInfo) v.getTag());
            accountAddDialog.setTitleText("编辑");
            accountAddDialog.show();
        });

    }

    private void initData() {
        presenter.getCache();
    }

    private void initView() {
        rvContent = findViewById(R.id.rvContent);
        rvContent.setAdapter(smallAccountAdapter=new SmallAccountAdapter(this,new ArrayList<>()));
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvContent.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        accountAddDialog=new AccountAddDialog.Builder(this).setAccountInfo(null)
                .setTitle("添加小号")
                .setListener((oldAccount, newAccount) -> {
                    if(oldAccount!=null) {
                        smallAccountAdapter.replaceItem(oldAccount,newAccount);
                        showToast("修改完成！");
                    }else
                    {
                        smallAccountAdapter.addItem(newAccount);
                        showToast("添加完成！");
                    }
                    presenter.cache(smallAccountAdapter.getItems());
                }).build();

        smsDialog =new SmsDialog.Builder(this).setAccountInfo(null).setTitle("手机验证!").setListener(new SmsDialog.OnSubmitListener() {
            @Override
            public void submit(AccountInfo accountInfo, String mobile, String verCode) {

            }
        }).setOnGetVerCodeClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if (id == R.id.action_add) {
            accountAddDialog.setAccountInfo(null);
            accountAddDialog.setTitleText("添加");
            accountAddDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void cacheSuccess() {
        //缓存后回调
    }

    @Override
    public void onGetListSuccess(List<AccountInfo> accountInfos) {
        smallAccountAdapter.setData(accountInfos);
    }

    @Override
    public void onGetQrCodeSuccess(String result) {
        LogUtil.d(result);
    }
}
