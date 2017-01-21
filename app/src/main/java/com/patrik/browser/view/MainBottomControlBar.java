package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.patrik.browser.R;
import com.patrik.browser.event.EvtHome;
import com.patrik.browser.tool.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * MainBottomControlBar
 * Create by patrik on 2016/8/30.
 */
public class MainBottomControlBar extends LinearLayout implements View.OnClickListener, ViewCommonInterface {
    private View iv_bottom_setting,iv_bottom_pop_close,iv_bottom_exit;
    public MainBottomControlBar(Context context, AttributeSet attri) {
        super(context, attri);
        this.setVisibility(View.GONE);
    }
    /**
     * init where in inflate .
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initLayout();
    }
    @Override
    public void initLayout() {
        iv_bottom_setting = findViewById(R.id.iv_bottom_setting);
        iv_bottom_pop_close = findViewById(R.id.iv_bottom_pop_close);
        iv_bottom_exit = findViewById(R.id.iv_bottom_exit);
    }

    @Override
    public void bootCompleted() {
        iv_bottom_setting.setOnClickListener(this);
        iv_bottom_pop_close.setOnClickListener(this);
        iv_bottom_exit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bottom_setting:
                bottomSettingClick();
                break;
            case R.id.iv_bottom_pop_close:
                bottomPopCloseClick();
                break;
            case R.id.iv_bottom_exit:
                bottomExitClick();
                break;
        }
    }
    private void bottomSettingClick(){

    }
    private void bottomPopCloseClick(){
        EventBus.getDefault().post(new EvtHome(Constants.POP_HIDE));
    }
    private void bottomExitClick(){
        System.exit(0);
    }
}
