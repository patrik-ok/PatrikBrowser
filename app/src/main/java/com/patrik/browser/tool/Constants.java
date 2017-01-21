package com.patrik.browser.tool;

/**
 * usage
 * Create by patrik on 2016/11/25.
 */
public class Constants {
    /**
     * pop
     */
    public static final int POP_SHOW = 1;
    public static final int POP_HIDE = POP_SHOW + 1;
    public static final int DEFAULT_BAR_SHOW = POP_HIDE + 1;
    public static final int DEFAULT_BAR_HIDE = DEFAULT_BAR_SHOW + 1;
    public static final int CONTROL_BAR_SHOW = DEFAULT_BAR_HIDE + 1;
    public static final int CONTROL_BAR_HIDE = CONTROL_BAR_SHOW + 1;

    /**
     * default url
     */
    public static final String DEFAULT_URL = "www.baidu.com";
    /**
     * webview
     */
    public static final int CAN_GOFORWARD = CONTROL_BAR_HIDE + 1;
    public static final int CAN_GOBACK = CAN_GOFORWARD + 1;
    public static final int CANNOT_GOFORWARD = CAN_GOBACK + 1;
    public static final int CANNOT_GOBACK = CANNOT_GOFORWARD + 1;
    public static final int GOFORWARD = CANNOT_GOBACK + 1;
    public static final int GOBACK = GOFORWARD + 1;
}
