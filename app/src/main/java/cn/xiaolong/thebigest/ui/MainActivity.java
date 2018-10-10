package cn.xiaolong.thebigest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.entity.AccountInfo;
import cn.xiaolong.thebigest.presenter.MainPresenter;
import cn.xiaolong.thebigest.util.LogUtil;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    private EditText etUrl;
//    private EditText tvPhoneNumber;
    private TextView tvUrlParseResult;
    private TextView tvSubmit;
    private String mCurrentSn;
    private List<AccountInfo> accountInfoList;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    private void initView() {
//        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        etUrl = findViewById(R.id.etUrl);
        tvUrlParseResult = findViewById(R.id.tvUrlParseResult);
        tvSubmit = findViewById(R.id.tvSubmit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCache();
    }

    private void initData() {
    }

    private void setListener() {
        tvSubmit.setOnClickListener(v -> {
            presenter.touchPackage(mCurrentSn, accountInfoList.get(index));
            index = (index + 1) % accountInfoList.size();
        });
        etUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.getCheckAndParseLuckyPackage(s.toString());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            startActivity(new Intent(this, BindActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetLuckyNumberSuccess(String luckyNumber) {
        LogUtil.d(luckyNumber);
    }

    @Override
    public void getSnAndLuckyNumSuccess(String sn, String luckNumber) {
        if (!TextUtils.isEmpty(sn)) {
            tvUrlParseResult.setText("红包地址正确,最大红包为:" + luckNumber);
            mCurrentSn = sn;
            SPHelp.setAppParam(BuildConfig.KEY_SN, sn);
            tvSubmit.setEnabled(true);
        } else {
            tvUrlParseResult.setText("红包地址错误,请复制正确的链接！");
            tvSubmit.setEnabled(false);
        }
    }

    @Override
    public void touchSuccess(String position) {
        LogUtil.d(position);
    }

    @Override
    public void onGetListSuccess(List<AccountInfo> accountInfoList) {
        this.accountInfoList = accountInfoList;
    }
}
