package com.patrik.browser.https;

/**
 * usage
 * Create by patrik on 2017/1/19.
 */
public class HttpUtils {
    private static HttpUtils mHttpUtils = null;
    private HttpUtils(){

    }
    public static HttpUtils getInstance(){
        if(mHttpUtils == null){
            synchronized (HttpUtils.class){
                if(mHttpUtils == null){
                    mHttpUtils = new HttpUtils();
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
