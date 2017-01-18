package com.patrik.browser.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.patrik.browser.R;
import com.patrik.browser.tool.LogUtils;
import com.patrik.browser.tool.ToastUtils;


/**
 * LaunchActivity
 * Create by patrik on 2016/8/29.
 */
public class LaunchActivity extends Activity {
    private String packageName = this.getClass().getPackage().toString();
    private String className = this.getClass().getSimpleName();
    private final String[] PERMISSION_TABLE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_launch);
    }

    private void init() {
//        PermissionUtils.getInstance().requestAllPermission(this);
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                PERMISSION_TABLE, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                        //finish must below startActivity.(#patrik)2017/1/18.
                        finish();
                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastUtils.getInstance(LaunchActivity.this).showToast(R.string.str_permisson_deny);
                        finish();
                    }
                });
        LogUtils.e(className, packageName, "oncreate");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
