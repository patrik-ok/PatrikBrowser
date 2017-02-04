package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.patrik.browser.R;
import com.patrik.browser.event.EvtBtmBar;
import com.patrik.browser.event.EvtHome;
import com.patrik.browser.event.EvtInput;
import com.patrik.browser.tool.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * MainBottomDefaultBar
 * Created by shenghua.luo on 2016/8/30.
 */
public class MainBottomDefaultBar extends LinearLayout implements View.OnClickListener, ViewCommonInterface {
    private Context mContext;
    private PopMenuView popMenuView;
    private View iv_bottom_back, iv_bottom_go_ahead, iv_bottom_pop, iv_bottom_home, fl_bottom_tab;

    public MainBottomDefaultBar(Context context, AttributeSet attri) {
        super(context, attri);
        mContext = context;
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
        iv_bottom_back = findViewById(R.id.iv_bottom_back);
        iv_bottom_go_ahead = findViewById(R.id.iv_bottom_go_ahead);
        iv_bottom_pop = findViewById(R.id.iv_bottom_pop);
        iv_bottom_home = findViewById(R.id.iv_bottom_home);
        fl_bottom_tab = findViewById(R.id.fl_bottom_tab);
        getPopMenuView();
    }

    @Override
    public void bootCompleted() {
        iv_bottom_back.setOnClickListener(this);
        iv_bottom_go_ahead.setOnClickListener(this);
        iv_bottom_pop.setOnClickListener(this);
        iv_bottom_home.setOnClickListener(this);
        fl_bottom_tab.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bottom_back:
                bottomBackClick();
                break;
            case R.id.iv_bottom_go_ahead:
                bottomGoForwardClick();
                break;
            case R.id.iv_bottom_pop:
                bottomPopClick();
                break;
            case R.id.iv_bottom_home:
                bottomHomeClick();
                break;
            case R.id.fl_bottom_tab:
                bottomTabClick();
                break;
        }
    }

    private void bottomBackClick() {
        EventBus.getDefault().post(new EvtInput(Constants.GOBACK));
    }

    private void bottomGoForwardClick() {
        EventBus.getDefault().post(new EvtInput(Constants.GOFORWARD));
    }

    private void bottomPopClick() {
        PopMenuView popMenuView = getPopMenuView();
        if (popMenuView != null) {
//            boolean isShow = popMenuView.getVisibility() == View.VISIBLE;
//            if(isShow){
//                EventBus.getDefault().post(Constants.POP_HIDE);
//            }else {
            EventBus.getDefault().post(new EvtHome(Constants.POP_SHOW));
//            }
        } else {
            return;
        }
    }

    /**
     * get popMenuView
     *
     * @return
     */
    public PopMenuView getPopMenuView() {
        if (popMenuView == null) {
            popMenuView = new PopMenuView(mContext);
        }
        return popMenuView;
    }

    private void bottomHomeClick() {

    }

    private void bottomTabClick() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EvtOnBtmBar(EvtBtmBar event) {
        if (event.getActionInt() == Constants.CANNOT_GOBACK) {
            iv_bottom_back.setEnabled(false);
        } else if (event.getActionInt() == Constants.CANNOT_GOFORWARD) {
            iv_bottom_go_ahead.setEnabled(false);
        } else if (event.getActionInt() == Constants.CAN_GOBACK) {
            iv_bottom_back.setEnabled(true);
        } else if (event.getActionInt() == Constants.CAN_GOFORWARD) {
            iv_bottom_go_ahead.setEnabled(true);
        }
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
