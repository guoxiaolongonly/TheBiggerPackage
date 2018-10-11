package cn.xiaolong.thebigest.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLDecoder;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/8 13:56
 */
public class AccountAddDialog extends BaseAnimDialog {
    private EditText etQQ;
//    private EditText etOpenid;
//    private EditText etSign;
    private EditText etCookie;
    private TextView tvTitle;
    private AccountInfo accountInfo;
    private String title;
    private OnSubmitListener onSubmitListener;
    private TextView tvSubmit;

    protected AccountAddDialog(Context context) {
        super(context);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_add, null);
    }

    @Override
    protected void initView(View contentView) {
        tvTitle = contentView.findViewById(R.id.tvTitle);
        etQQ = contentView.findViewById(R.id.etQQ);
//        etOpenid = contentView.findViewById(R.id.etOpenid);
//        etSign = contentView.findViewById(R.id.etSign);
        etCookie=contentView.findViewById(R.id.etCookie);
        tvSubmit = contentView.findViewById(R.id.tvSubmit);
        initData();
    }

    private void initData() {
        tvTitle.setText(title);
        if(accountInfo!=null) {
//            etOpenid.setText(accountInfo.openId);
//            etSign.setText(accountInfo.sign);
            etCookie.setText(accountInfo.cookie);
            etQQ.setText(accountInfo.QQ);
        }
        tvSubmit.setOnClickListener(v -> {
//            String openid = etOpenid.getText().toString();
//            String sign = etSign.getText().toString();
            String QQ = etQQ.getText().toString();
            String cookie =etCookie.getText().toString();
            if (TextUtils.isEmpty(etCookie.getText().toString())) {
                Toast.makeText(mContext, "cookie不能为空！", Toast.LENGTH_LONG).show();
                return;
            }
//            if (TextUtils.isEmpty(etSign.getText().toString())) {
//                Toast.makeText(mContext, "sign不能为空！", Toast.LENGTH_LONG).show();
//                return;
//            }
            if (cookie.indexOf("snsInfo[wx2a416286e96100ed]=") == -1
                    && cookie.indexOf("snsInfo[101204453]=") == -1) {
               Toast.makeText(mContext,"请确保内容包含：snsInfo[wx2a416286e96100ed] 或 snsInfo[101204453]",Toast.LENGTH_LONG).show();
               return;
            }
            cookie = URLDecoder.decode(cookie);
            String openid="";
            if(cookie.contains("openid\":\"")) {
                openid = cookie.split("openid\":\"")[1].split("\",")[0];
            }else
            {
                Toast.makeText(mContext,"Cookie未携带openId,请检测cookie是否正确！",Toast.LENGTH_LONG).show();
                return;
            }
            String sign="";
            if(cookie.contains("eleme_key\":\"")) {
                sign = cookie.split("eleme_key\":\"")[1].split("\",")[0];
            }else
            {
                Toast.makeText(mContext,"Cookie未携带eleme_key,请检测cookie是否正确！",Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(etQQ.getText().toString())) {
                Toast.makeText(mContext, "QQ号码不能为空！", Toast.LENGTH_LONG).show();
                return;
            }
            if (onSubmitListener != null) {
                onSubmitListener.add(accountInfo,new AccountInfo(cookie,sign,openid,QQ));
            }
            dismiss();
        });
    }


    public static class Builder {

        private Context context;
        private AccountInfo accountInfo;
        private OnSubmitListener listener;
        private String titleText;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setListener(OnSubmitListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setTitle(String text) {
            titleText = text;
            return this;
        }

        public Builder setAccountInfo(AccountInfo accountInfo) {
            this.accountInfo = accountInfo;
            return this;
        }

        public AccountAddDialog build() {
            AccountAddDialog accountAddDialog = new AccountAddDialog(context);
            accountAddDialog.setListener(listener);
            accountAddDialog.setAccountInfo(accountInfo);
            accountAddDialog.setTitleText(titleText);
            return accountAddDialog;
        }
    }

    public void setTitleText(String titleText) {
        if(tvTitle!=null)
        {
            tvTitle.setText(titleText);
            return;
        }
        this.title = titleText;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        if(accountInfo==null)
        {
            accountInfo=new AccountInfo("","","","");
        }
        if(etQQ!=null)
        {
            etQQ.setText(accountInfo.QQ);
        }
        if(etCookie!=null)
        {
            etCookie.setText(accountInfo.cookie);
        }
    }

    private void setListener(OnSubmitListener listener) {
        this.onSubmitListener = listener;
    }

    /**
     * 提交回调
     */
    public interface OnSubmitListener {
        /**
         * @param oldAccount 这个用来做删除的 可能是编辑的
         * @param newAccount 添加的新的
         */
        void add(AccountInfo oldAccount, AccountInfo newAccount);
    }
}
