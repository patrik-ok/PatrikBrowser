package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.patrik.browser.R;
import com.patrik.browser.base.OnInputEditAction;

/**
 * usage
 * Create by patrik on 2017/1/22.
 */
public class ContentViewDefault extends LinearLayout {
    private ScrollView scrollView_homepage;

    public ContentViewDefault(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        scrollView_homepage = (ScrollView) findViewById(R.id.scrollView_homepage);
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.updateToInputContentView();
                }
            }
        });
    }

    private OnInputEditAction listener;

    public void setOnInputEditAction(OnInputEditAction listener) {
        this.listener = listener;
    }
}
