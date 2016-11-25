package com.patrik.browser.tool;

import android.content.Context;
import android.widget.Toast;
/**
 * ToastUtils
 * Create by patrik on 2016/8/29.
 */
public class ToastUtils {
    public static ToastUtils ourInstance = new ToastUtils();
    private static Context mContext;
    private ToastUtils() {
    }
    public static ToastUtils getInstance(Context context){
        mContext = context;
        return  ourInstance;
    }
    public void showToast(int stringId){
        Toast.makeText(mContext,stringId , Toast.LENGTH_SHORT).show();
    }
    public  void showTestToast(String stringId){
        Toast.makeText(mContext,stringId , Toast.LENGTH_SHORT).show();
    }
}
