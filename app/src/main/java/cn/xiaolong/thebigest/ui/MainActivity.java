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
import cn.xiaolong.thebigest.entity.ErrorThrowable;
import cn.xiaolong.thebigest.entity.PackageInfo;
import cn.xiaolong.thebigest.entity.PromotionItem;
import cn.xiaolong.thebigest.presenter.MainPresenter;
import cn.xiaolong.thebigest.util.Constant;
import cn.xiaolong.thebigest.util.LogUtil;
import cn.xiaolong.thebigest.util.SPHelp;
import cn.xiaolong.thebigest.view.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    public static final int REQUEST_BIG_ACCOUNT = 0x123;

    private EditText etUrl;
    private TextView tvUrlParseResult;
    private TextView tvSubmit;
    private String mCurrentSn;
    private List<AccountInfo> accountInfoList;
    private int index;
    private int luckyNumber;
    private int perPackageCount;
    private TextView tvLog;
    private TextView tvReset;
    private TextView tvChoose;
    private AccountInfo mBigAccount;
    private TextView tvHint;

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
        tvChoose = findViewById(R.id.tvChoose);
        tvReset = findViewById(R.id.tvReset);
        etUrl = findViewById(R.id.etUrl);
        tvLog = findViewById(R.id.tvLog);
        tvHint = findViewById(R.id.tvHint);
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
            if (luckyNumber == 0 || TextUtils.isEmpty(mCurrentSn)) {
                showToast("请填写正确红包地址！");
                return;
            }
            if (accountInfoList.size() == 0) {
                showToast("你还没有小号，请先通过配置添加小号！");
                return;
            }
            perPackageCount = 0;
            touchPackage();
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
        tvReset.setOnClickListener(v -> {
            presenter.resetPerDayCount(accountInfoList);
        });
        tvChoose.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, BigBindActivity.class), REQUEST_BIG_ACCOUNT);
        });
    }

    private void touchPackage() {
        presenter.touchPackage(mCurrentSn, accountInfoList.get(index));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_manager) {
            startActivity(new Intent(this, BindActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetLuckyNumberSuccess(String result) {
        luckyNumber = Integer.parseInt(result);
        tvUrlParseResult.setText("红包地址正确,最大红包为:" + result);
    }

    @Override
    public void getSnAndLuckyNumSuccess(String sn, String luckNumber) {
        if (!TextUtils.isEmpty(sn)) {
            presenter.getLuckyNumber(sn);
            mCurrentSn = sn;
            SPHelp.setAppParam(BuildConfig.KEY_SN, sn);
            if (Integer.parseInt(luckNumber) - 1 > accountInfoList.size()) {
                showToast("当前帐号数量可能少于，所需用户数，请慎重领取。");
                return;
            }
            tvSubmit.setEnabled(true);
        } else {
            tvUrlParseResult.setText("红包地址错误,请复制正确的链接！");
            tvSubmit.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BIG_ACCOUNT && resultCode == BigBindActivity.RESULT_CHOOSE) {
            AccountInfo accountInfo = (AccountInfo) data.getSerializableExtra("accountInfo");
            tvHint.setText("将使用大号：" + accountInfo.QQ + "领取大红包");
            this.mBigAccount = accountInfo;
        }
    }

    @Override
    public void touchSuccess(AccountInfo accountInfo, PackageInfo packageInfo) {
        accountInfo.isValid = true;
        newUserCheck(packageInfo);
        perPackageCount++;
        //如果这个红包的点击次数已经循环了一个列表
        if (perPackageCount > accountInfoList.size()) {
            showToast("小不够用啦！可以去配置加一些！当前红包：" + packageInfo.promotion_records.size() + "大红包：" + luckyNumber);
            return;
        }
        //当前账号没有次数
        if (packageInfo.promotion_items.size() == 0) {
            touchNext();
            return;
        }
        LogUtil.d("是否大红包？" + packageInfo.is_lucky + "__当前红包:" + packageInfo.promotion_records.size() + "金额：" + packageInfo.promotion_items.get(0).amount);
        //        showToast("是否大红包？" + packageInfo.is_lucky + "__当前红包:" + packageInfo.promotion_records.size() + "金额：" + packageInfo.promotion_items.get(0).amount);
        accountInfo.countIncrease();
        presenter.cache(accountInfoList);
        if (packageInfo == null || packageInfo.promotion_records == null) {
            showToast("未成功获取当前领取用户数量！建议换个链接试试");
            return;
        }
        if (packageInfo.promotion_records.size() >= luckyNumber) {
            showToast("此红包已经被领取完了，请勿继续领取。");
        } else if (packageInfo.promotion_records.size() == luckyNumber - 1) {
            showToast("下一个为大红包，可以复制出来啦。");
            if (mBigAccount != null) {
                presenter.bigTouch(mCurrentSn, mBigAccount);
            }
        } else {
            touchNext();
        }
    }

    @Override
    public void bigTouchSuccess(AccountInfo accountInfo, PackageInfo packageInfo) {
        if (packageInfo.promotion_records.size() == luckyNumber) {
            showToast("QQ号：" + accountInfo.QQ + "领取大红包成功");
        } else {
            showToast("QQ号：" + accountInfo.QQ + "领取次数可能用尽");
        }

    }

    @Override
    public void bigTouchFail(Throwable e) {
        if (e instanceof ErrorThrowable) {
            ErrorThrowable errorThrowable = (ErrorThrowable) e;
            switch (errorThrowable.code) {
                case Constant.ERROR_INVALID:
                    tvLog.append("大号：" + mBigAccount.QQ + "验证失败，可能需要重新添加，下个红包为最大红包，请手动领取或者换个红包继续。");
                    break;
                case Constant.ERROR_BUSY:
                    presenter.bigTouch(mCurrentSn, mBigAccount);
                    break;
                default:
                    showToast(e.getMessage());
                    break;
            }
        }
    }

    private void newUserCheck(PackageInfo packageInfo) {
        if (packageInfo.promotion_items != null && packageInfo.promotion_items.size() > 1) {
            for (PromotionItem promotionItem : packageInfo.promotion_items) {
                if (promotionItem.is_new_user) {
                    tvLog.append("手机号：" + promotionItem.phone + "是新用户，可以搞首单满减\n");
                    break;
                }
            }
        }

    }

    @Override
    public void onGetListSuccess(List<AccountInfo> accountInfoList) {
        index = 0;
        this.accountInfoList = accountInfoList;
    }

    @Override
    public void cacheSuccess() {

    }


    @Override
    public void showError(Throwable error) {
        if (error instanceof ErrorThrowable) {
            ErrorThrowable errorThrowable = (ErrorThrowable) error;
            switch (errorThrowable.code) {
                case Constant.ERROR_INVALID:
                    tvLog.append("QQ：" + accountInfoList.get(index).QQ + "验证失败，可能需要重新添加\n");
                    perPackageCount++;
                    accountInfoList.get(index).isValid = false;
                    presenter.cache(accountInfoList);
                    touchNext();
                    break;
                case Constant.ERROR_BUSY:
                    touchPackage();
                    break;
                default:
                    showToast(error.getMessage());
                    break;
            }
        }
    }

    public void touchNext() {
        index = (index + 1) % accountInfoList.size();
        touchPackage();
    }
}
