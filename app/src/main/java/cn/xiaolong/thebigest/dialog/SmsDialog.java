package cn.xiaolong.thebigest.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/8 13:56
 */
public class SmsDialog extends BaseAnimDialog {
    private EditText etPhone;
    private EditText etVerCode;
    private TextView tvGetVerCode;
    private TextView tvTitle;
    private AccountInfo accountInfo;
    private String title;
    private OnSubmitListener onSubmitListener;
    private View.OnClickListener onGetVerCodeClickListener;
    private TextView tvSubmit;

    protected SmsDialog(Context context) {
        super(context);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_sms, null);
    }

    @Override
    protected void initView(View contentView) {
        tvTitle = contentView.findViewById(R.id.tvTitle);
        etPhone = contentView.findViewById(R.id.etPhone);
        etVerCode = contentView.findViewById(R.id.etVerCode);
        tvGetVerCode = contentView.findViewById(R.id.tvGetVerCode);
        tvSubmit = contentView.findViewById(R.id.tvSubmit);
        initData();
    }

    private void initData() {
        tvTitle.setText(title);
        tvGetVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGetVerCodeClickListener != null) {
                    String phone = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(mContext, "手机号码不能为空！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    onGetVerCodeClickListener.onClick(v);
                }
            }
        });
        tvSubmit.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            String verCode = etVerCode.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(mContext, "手机号码不能为空！", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(verCode)) {
                Toast.makeText(mContext, "sign不能为空！", Toast.LENGTH_LONG).show();
                return;
            }
            if (onSubmitListener != null) {
                onSubmitListener.submit(accountInfo, phone, verCode);
            }
            dismiss();
        });
    }


    public static class Builder {

        private Context context;
        private OnSubmitListener listener;
        private String titleText;
        private View.OnClickListener mOnClickListener;
        private AccountInfo accountInfo;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setListener(OnSubmitListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setOnGetVerCodeClickListener(View.OnClickListener listener) {
            this.mOnClickListener = listener;
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

        public SmsDialog build() {
            SmsDialog smsDialog = new SmsDialog(context);
            smsDialog.setTitleText(titleText);
            smsDialog.setOnSubmitListener(listener);
            smsDialog.setOnGetVerCodeClickListener(mOnClickListener);
            smsDialog.setAccountInfo(accountInfo);
            return smsDialog;
        }
    }

    private void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public void setTitleText(String titleText) {
        if (tvTitle != null) {
            tvTitle.setText(titleText);
            return;
        }
        this.title = titleText;
    }

    private void setOnSubmitListener(OnSubmitListener listener) {
        this.onSubmitListener = listener;
    }

    private void setOnGetVerCodeClickListener(View.OnClickListener listener) {
        this.onGetVerCodeClickListener = listener;
    }

    /**
     * 提交回调
     */
    public interface OnSubmitListener {
        void submit(AccountInfo accountInfo, String mobile, String verCode);
    }
}
