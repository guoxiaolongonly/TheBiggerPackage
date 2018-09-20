package cn.xiaolong.thebigest;

import android.os.Bundle;
import android.widget.EditText;

import cn.xiaolong.thebigest.view.IMainView;

public class MainActivity extends BaseActivity implements IMainView{
    private EditText etUrl;
    private EditText tvPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPhoneNumber= (EditText) findViewById(R.id.tvPhoneNumber);
        etUrl= (EditText) findViewById(R.id.etUrl);
    }

    @Override
    public void onGetLuckyNumberSuccess(String luckyNumber) {

    }
}
