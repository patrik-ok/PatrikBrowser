package com.patrik.browser.tool;

import android.util.Log;

/**
 * LogUtils
 * Create by patrik on 2016/8/29.
 */
public class LogUtils {
    private static boolean mDebug = true;
    private static final String mTAG = "patrik_";

    public static void e(String className, String packageName, String... strs) {
        execute(mDebug, className, packageName, 1, strs);
    }

    public static void e(boolean isDebug, String className, String packageName, String... strs) {
        execute(isDebug, className, packageName, 1, strs);
    }

    public static void w(String className, String packageName, String... strs) {
        execute(mDebug, className, packageName, 2, strs);
    }

    public static void w(boolean isDebug, String className, String packageName, String... strs) {
        execute(isDebug, className, packageName, 2, strs);
    }

    public static void setDebug(boolean isDebug) {
        mDebug = isDebug;
    }

    public static boolean isDebug() {
        return mDebug;
    }

    private static void execute(boolean isdebug, String className, String packageName, int type, String... strs) {
        if (isdebug) {
            StringBuilder builderTag = new StringBuilder();
            StringBuilder builderContent = new StringBuilder();
            builderTag.append(mTAG);
            if (className == null && className.equals("")) {
                builderTag.append("Can't get className or no incoming className.");
            } else {
                builderTag.append(className);
            }
            builderTag.append("-->");
            for (int i = 0; i < strs.length; i++) {
                builderContent.append(strs[i]);
                if (i != strs.length - 1) {
                    builderContent.append(" || ");
                } else {
                    builderContent.append("\n");
                    builderContent.append("at");
                    builderContent.append("\t");
                    if (packageName == null && packageName.equals("")) {
                        builderContent.append("Can't get packageName or no incoming packageName.");
                    } else {
                        builderContent.append(packageName);
                        builderContent.append(".");
                        builderContent.append(className);
                    }
                }
            }
            switch (type) {
                case 1:
                    Log.e(builderTag.toString(), builderContent.toString());
                    break;
                case 2:
                    Log.w(builderTag.toString(), builderContent.toString());
                    break;
            }
        }
    }

}
