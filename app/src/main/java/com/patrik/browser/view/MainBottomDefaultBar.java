package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.patrik.browser.R;
import com.patrik.browser.event.EvtPop;
import com.patrik.browser.tool.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * MainBottomDefaultBar
 * Created by shenghua.luo on 2016/8/30.
 */
public class MainBottomDefaultBar extends LinearLayout implements View.OnClickListener, ViewCommonInterface {
    private Context mContext;
    private PopMenuView popMenuView ;
    private View iv_bottom_back,iv_bottom_go_ahead,iv_bottom_pop,iv_bottom_home,fl_bottom_tab;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bottom_back:
                bottomBackClick();
                break;
            case R.id.iv_bottom_go_ahead:
                bottomGoAheadClick();
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
    private void bottomBackClick(){

    }
    private void bottomGoAheadClick(){

    }
    private void bottomPopClick(){
        PopMenuView popMenuView = getPopMenuView();
        if(popMenuView!=null){
//            boolean isShow = popMenuView.getVisibility() == View.VISIBLE;
//            if(isShow){
//                EventBus.getDefault().post(Constants.POP_HIDE);
//            }else {
                EventBus.getDefault().post(new EvtPop(Constants.POP_SHOW));
//            }
        }else{
            return ;
        }
    }
    /**
     * get popMenuView
     * @return
     */
    public PopMenuView getPopMenuView(){
        if(popMenuView == null){
            popMenuView = new PopMenuView(mContext);
        }
        return popMenuView;
    }
    private void bottomHomeClick(){

    }
    private void bottomTabClick(){

    }
}
