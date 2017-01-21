package com.patrik.browser.tool;

/**
 * usage
 * Create by patrik on 2017/1/20.
 */
public class UrlUtils {
    private static UrlUtils mInstance = null;
    private final String ABOUT_TYPE = "about:";
    private final String FILE_TYPE = "file://";
    private final String HTTP_TYPE = "http://";
    private final String HTTPS_TYPE = "https://";
    private final String CONTENT_TYPE = "content:";
    private final String JAVASCRIPT_TYPE = "javascript:";
    private final String ASSET_TYPE = "file:///android_asset/";
    private final String RESOURCE_TYPE = "file:///android_res/";
    private final String PROXY_TYPE = "file:///cookieless_proxy/";

    private UrlUtils() {
    }

    public static UrlUtils getInstance() {
        if (mInstance == null) {
            synchronized (UrlUtils.class) {
                if (mInstance == null) {
                    mInstance = new UrlUtils();
                }
            }
        }
        return mInstance;
    }

    public boolean isValidUrl(String url) {
        if (url == null || url.length() == 0) {
            return false;
        }

        return (isFileUrl(url) ||
                isHttpUrl(url) ||
                isAboutUrl(url) ||
                isHttpsUrl(url) ||
                isAssetUrl(url) ||
                isContentUrl(url) ||
                isResourceUrl(url) ||
                isJavaScriptUrl(url));
    }

    public static String recombineUrl(String url) {
        if (!url.contains(":/")) {
            url = "http://" + url;
        }
        return url;
    }

    private boolean isAssetUrl(String url) {
        return (null != url) && url.startsWith(ASSET_TYPE);
    }

    private boolean isResourceUrl(String url) {
        return (null != url) && url.startsWith(RESOURCE_TYPE);
    }

    private boolean isFileUrl(String url) {
        return (null != url) && (url.startsWith(FILE_TYPE) &&
                !url.startsWith(ASSET_TYPE) &&
                !url.startsWith(PROXY_TYPE));
    }

    private boolean isAboutUrl(String url) {
        return (null != url) && url.startsWith(ABOUT_TYPE);
    }

    private boolean isHttpUrl(String url) {
        return (null != url) &&
                (url.length() > 6) &&
                url.substring(0, 7).equalsIgnoreCase(HTTP_TYPE);
    }

    private boolean isHttpsUrl(String url) {
        return (null != url) &&
                (url.length() > 7) &&
                url.substring(0, 8).equalsIgnoreCase(HTTPS_TYPE);
    }

    private boolean isJavaScriptUrl(String url) {
        return (null != url) && url.startsWith(JAVASCRIPT_TYPE);
    }

    private boolean isContentUrl(String url) {
        return (null != url) && url.startsWith(CONTENT_TYPE);
    }
}
