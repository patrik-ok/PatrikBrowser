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
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        init();
    }
    private void init(){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE
                }, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastUtils.getInstance(LaunchActivity.this).showToast(R.string.app_name);
                        finish();
                    }
                });
        LogUtils.e(this, "oncreate");
    }
}
