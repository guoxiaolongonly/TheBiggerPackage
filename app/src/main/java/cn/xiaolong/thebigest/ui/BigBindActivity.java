package cn.xiaolong.thebigest.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.adapter.SmallAccountAdapter;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.presenter.BindPresenter;
import cn.xiaolong.thebigest.util.Constant;
import cn.xiaolong.thebigest.view.IBindView;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 16:49
 */

public class BigBindActivity extends BaseActivity<BindPresenter> implements IBindView {
    public static final int REQUEST_ADD = 0x111;
    public static final int RESULT_CHOOSE = 0X666;
    private RecyclerView rvContent;
    private SmallAccountAdapter smallAccountAdapter;
    private TextView tvExport;
    private TextView tvHint;

    @Override
    protected BindPresenter initPresenter() {
        return new BindPresenter(this, Constant.ACOOUT_TYPE_BIG);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        setTitle("大号列表");
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
        smallAccountAdapter.setOnCopyClickListener(v -> {
            AccountInfo accountInfo = (AccountInfo) v.getTag();
            ClipboardManager clipboardManager = (ClipboardManager) BigBindActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, accountInfo.cookie));
            showToast("已经复制Cookie到剪切板");
        });
        smallAccountAdapter.setOnEditClickListener(v -> {
            int position = (int) v.getTag();
            AccountInfo accountInfo = smallAccountAdapter.getItems().get(position);
            Intent intent = new Intent(BigBindActivity.this, LoginActivity.class);
            intent.putExtras(LoginActivity.buildBundle(accountInfo.cookie));
            startActivityForResult(intent, position);
        });
        smallAccountAdapter.setOnItemClickListener(v -> {
            AccountInfo accountInfo = (AccountInfo) v.getTag();
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("accountInfo", accountInfo);
            intent.putExtras(bundle);
            setResult(RESULT_CHOOSE,intent);
            finish();
        });
        tvExport.setOnClickListener(v -> presenter.export(smallAccountAdapter.getItems()));
    }

    private void initData() {
        tvHint.setText("可以使用导出文件的内容替换掉cache/" + Constant.CACHE_FILE_BIG + "文件中的内容做导出");
        presenter.getCache();
    }

    private void initView() {
        tvHint = findViewById(R.id.tvLog);
        tvExport = findViewById(R.id.tvExport);
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
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_ADD);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == REQUEST_ADD && resultCode == LoginActivity.RESULT_ADD) {
            AccountInfo accountInfo = (AccountInfo) data.getSerializableExtra("accountInfo");
            if (accountInfo == null) {
                return;
            }
            smallAccountAdapter.addItem(accountInfo);
            presenter.cache(smallAccountAdapter.getItems());
        } else {
            AccountInfo accountInfo = (AccountInfo) data.getSerializableExtra("accountInfo");
            if (accountInfo == null) {
                return;
            }
            smallAccountAdapter.replaceItem(requestCode, accountInfo);
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
    public void onExportSuccess(String filePath) {
        showToast("文件导出到：" + filePath);
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
