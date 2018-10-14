package cn.xiaolong.thebigest.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import cn.xiaolong.thebigest.util.PermissionUtil;


/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/1/30 09:14
 */

public class LaunchActivity extends BaseActivity {
    private PermissionUtil.PermissionTool permissionTool;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) { //针对6.0以后的版本加权限判断
            permissionTool = new PermissionUtil.PermissionTool(() -> initView());
            permissionTool.checkAndRequestPermission(this, permissionTool.requestPermissions);
        } else {
            initView();
        }
    }


    private void initView() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionTool.onRequestPermissionResult(this, requestCode, permissions, grantResults);
    }

}
