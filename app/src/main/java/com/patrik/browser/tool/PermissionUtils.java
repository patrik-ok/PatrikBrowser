package com.patrik.browser.tool;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * PermissionManageUtils
 * Create by patrik on 2016/11/25.
 */
public class PermissionUtils {
    private final int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public static PermissionUtils sInstance;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (sInstance == null) {
            sInstance = new PermissionUtils();
        }
        return sInstance;
    }

    //********************** start Android M Permission **********************

    /**
     * Android M               runtime permission package
     *
     * @param runnable    Requset permission Callback
     * @param permissions Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(Activity mAcitivity, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions == null || permissions.length == 0) return;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < 23) || checkPermissionGranted(mAcitivity, permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(mAcitivity, permissionRequestCode, permissions);
        }

    }

    private boolean checkPermissionGranted(Activity mActivity, String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(mActivity, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(Activity mActivity, final int requestCode, final String[] permissions) {
        // Contact permissions have not been granted yet. Request them directly.
        ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
                LogUtils.e(this,"permission denied");
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            LogUtils.e(this,"Must execute super.onRequestPermissionsResult() at first line in this method");
            throw new IllegalArgumentException("Must execute super.onRequestPermissionsResult() at first line in this method");
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    //********************** END Android M Permission **********************


    /**
     * you can also put content to customApplication class from below
     */
    final int PERMISSIONS_CODE = 0x0FFFFF;
    final Map<String, Boolean> PERMISSION_TABLE = new HashMap<String, Boolean>() {
        {
            put(Manifest.permission.READ_EXTERNAL_STORAGE, false);
            put(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
            put(Manifest.permission.READ_PHONE_STATE, false);
            put(Manifest.permission.CAMERA, false);
        }
    };

    public void requestPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String key : PERMISSION_TABLE.keySet()) {
                if (!PERMISSION_TABLE.get(key) &&
                        activity.checkSelfPermission(key) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(new String[]{
                            key
                    }, PERMISSIONS_CODE);
                }
            }
        }
    }
}
