package com.patrik.browser.tool;

import android.util.Log;

/**
 * LogUtils
 * Create by patrik on 2016/8/29.
 */
public class LogUtils {
    private static  boolean mDebug = false;
    private static final String mTAG = "patrik_";
    public static void e(Object obj,String... strs){
        if(mDebug){
            StringBuilder builderTag = new StringBuilder();
            StringBuilder builderContent = new StringBuilder();
            builderTag.append(mTAG);
            builderTag.append(obj.getClass().getSimpleName());
            builderTag.append("-->");
            for (int i = 0 ;i < strs.length;i++) {
                builderContent.append(strs[i]);
                if(i != strs.length-1){
                    builderContent.append(" || ");
                }else{
                    builderContent.append("\n");
                    builderContent.append(obj.getClass().getPackage());
                }
            }
            Log.e(builderTag.toString(),builderContent.toString());
        }
    }
    public static void setDebug(boolean isDebug){
        mDebug = isDebug;
    }
    public static boolean isDebug(){
        return mDebug;
    }

}
