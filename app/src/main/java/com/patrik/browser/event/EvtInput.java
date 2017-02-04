package com.patrik.browser.event;
/**
 * usage
 * Create by patrik on 2017/1/22.
 */
public class EvtInput {
    public int mAction;

    public EvtInput(int action) {
        this.mAction = action;
    }

    public int getActionInt() {
        return this.mAction;
    }
}
