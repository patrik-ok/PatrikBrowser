package com.patrik.browser.event;
/**
 * usage
 * Create by patrik on 2017/1/21.
 */
public class EvtBtmBar {
    public int mAction;

    public EvtBtmBar(int action) {
        this.mAction = action;
    }

    public int getActionInt() {
        return this.mAction;
    }
}
