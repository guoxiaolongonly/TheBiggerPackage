package cn.xiaolong.thebigest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.adapter.SmallAccountAdapter;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.presenter.BindPresenter;
import cn.xiaolong.thebigest.view.IBindView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 16:49
 */

public class BindActivity extends BaseActivity<BindPresenter> implements IBindView {
    public static final int REQUEST_CODE = 0X6220;
    private RecyclerView rvContent;
    private SmallAccountAdapter smallAccountAdapter;

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
            AccountInfo accountInfo = (AccountInfo) v.getTag();
            smallAccountAdapter.remove(accountInfo);
            showToast("删除成功！");
            presenter.cache(smallAccountAdapter.getItems());
        });

    }

    private void initData() {
        presenter.getCache();
    }

    private void initView() {
        rvContent = findViewById(R.id.rvContent);
        rvContent.setAdapter(smallAccountAdapter = new SmallAccountAdapter(this, new ArrayList<>()));
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvContent.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
//            accountAddDialog.setAccountInfo(null);
//            accountAddDialog.setTitleText("添加");
//            accountAddDialog.show();
            startActivityForResult(new Intent(this, LoginActivity.class),REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==LoginActivity.RESULT_LOGIN) {
            AccountInfo accountInfo = (AccountInfo) data.getSerializableExtra("accountInfo");
            if(accountInfo==null)
            {
                return;
            }
            smallAccountAdapter.addItem(accountInfo);
            presenter.cache(smallAccountAdapter.getItems());
        }

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
    public void onGetSmsCodeSuccess(String result) {
        showToast("验证码发送成功！");
    }

    @Override
    public void onLoginSuccess(AccountInfo accountInfo, AccountInfo result) {
        accountInfo.sid = result.sid;
        accountInfo.user_id = result.user_id;
        smallAccountAdapter.notifyDataSetChanged();
        presenter.cache(smallAccountAdapter.getItems());
        showToast("sid，获取成功！");
    }
}
