package com.patrik.browser.tool;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * PermissionManageUtils
 * Create by patrik on 2016/11/25.
 */
public class PermissionUtils {
    private String packageName = this.getClass().getPackage().toString();
    private String className = this.getClass().getSimpleName() ;
    private final int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public static PermissionUtils sInstance;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (sInstance == null) {
            synchronized (PermissionUtils.class){
                if (sInstance == null) {
                    sInstance = new PermissionUtils();
                }
            }
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
                LogUtils.e(className,packageName,"permission denied");
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            LogUtils.e(className,packageName,"Must execute super.onRequestPermissionsResult() at first line in this method");
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
    final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    public void requestAllPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(PERMISSIONS, PERMISSIONS_CODE);
        }
    }

}
