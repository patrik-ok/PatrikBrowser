package com.patrik.browser.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

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
                        if(ActivityCompat.shouldShowRequestPermissionRationale(LaunchActivity.this,permission)){
                            ToastUtils.getInstance(LaunchActivity.this).showToast(R.string.str_permisson_deny);
                            finish();
                        }else{
                            goToAppDetailSettingUI();
                        }
                    }
                });
        LogUtils.e(className, packageName, "oncreate");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private void goToAppDetailSettingUI() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
}
