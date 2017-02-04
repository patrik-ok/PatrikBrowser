package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * usage
 * Create by patrik on 2017/1/22.
 */
public class LinearInputEditText extends LinearLayout {

    public LinearInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }
    private void initView(){

    }
}
