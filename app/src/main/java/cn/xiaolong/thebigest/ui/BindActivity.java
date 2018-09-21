package cn.xiaolong.thebigest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.xiaolong.thebigest.R;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/21 16:49
 */
public class BindActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        setTitle("添加小号");
    }
}
