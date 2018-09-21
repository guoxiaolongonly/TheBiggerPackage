package cn.xiaolong.thebigest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import cn.xiaolong.thebigest.BuildConfig;
import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.presenter.MainPresenter;
import cn.xiaolong.thebigest.util.LogUtil;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    private EditText etUrl;
    private EditText tvPhoneNumber;
    private TextView tvUrlParseResult;
    private TextView tvSubmit;
    private String mCurrentSn;
    private TextView tvBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();

    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    private void initView() {
        tvPhoneNumber = (EditText) findViewById(R.id.tvPhoneNumber);
        etUrl = (EditText) findViewById(R.id.etUrl);
        tvUrlParseResult = (TextView) findViewById(R.id.tvUrlParseResult);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);
        tvBind= (TextView) findViewById(R.id.tvBind);
    }

    private void initData() {

    }

    private void setListener() {
        tvSubmit.setOnClickListener(v -> {
//            presenter.getLuckyNumber(mCurrentSn);
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
        tvBind.setOnClickListener(v -> {
            startActivity(new Intent());
        });
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
        } else {
            tvUrlParseResult.setText("红包地址错误,请复制正确的链接！");
        }
    }
}
