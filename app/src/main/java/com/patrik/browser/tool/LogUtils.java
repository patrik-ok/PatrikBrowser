package com.patrik.browser.tool;

import android.util.Log;

/**
 * LogUtils
 * Create by patrik on 2016/8/29.
 */
public class LogUtils {
    private static  boolean mDebug = false;
    private static final String mTAG = "patrik_";
    private static final String mStrIndex= "com.";
    public static void e(Object obj,String... strs){
        execute(obj,1,strs);
    }
    public static void w(Object obj,String... strs){
        execute(obj,2,strs);
    }
    public static void setDebug(boolean isDebug){
        mDebug = isDebug;
    }
    public static boolean isDebug(){
        return mDebug;
    }
    private static void execute(Object obj,int type,String... strs){
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
                    String packageLongStr = obj.getClass().getPackage().toString();
                    builderContent.append("\n");
                    builderContent.append("at");
                    builderContent.append("\t");
                    builderContent.append(packageLongStr.substring(packageLongStr.indexOf(mStrIndex)));
                }
            }
            switch (type){
                case 1:
                    Log.e(builderTag.toString(),builderContent.toString());
                    break;
                case 2:
                    Log.w(builderTag.toString(),builderContent.toString());
                    break;
            }
        }
    }

}
