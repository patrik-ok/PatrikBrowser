package com.patrik.browser.https;

/**
 * usage
 * Create by patrik on 2017/1/19.
 */
public class PatrikHttpUtils {
    private static PatrikHttpUtils mHttpUtils = null;
    private PatrikHttpUtils(){

    }
    public static PatrikHttpUtils getInstance(){
        if(mHttpUtils == null){
            synchronized (HttpUtils.class){
                if(mHttpUtils == null){
                    mHttpUtils = new PatrikHttpUtils();
                }
            }
        }
        return mHttpUtils;
    }
    public void _get(String url){

    }
    public void _post(String url){

    }
}
