package com.patrik.browser.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.patrik.browser.R;
import com.patrik.browser.base.BaseActivity;
import com.patrik.browser.base.OnInputEditAction;
import com.patrik.browser.event.EvtHome;
import com.patrik.browser.tool.Constants;
import com.patrik.browser.view.ContentViewDefault;
import com.patrik.browser.view.ContentViewInput;
import com.patrik.browser.view.MainBottomControlBar;
import com.patrik.browser.view.MainBottomDefaultBar;
import com.patrik.browser.view.PopMenuView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * MainActivity
 * Create by patrik on 2016/8/29.
 */

public class MainActivity extends BaseActivity implements View.OnTouchListener,OnInputEditAction {
    private MainBottomDefaultBar mBtmDefaultBar;
    private MainBottomControlBar mBtmControlBar;
    private FrameLayout mPopMask;
    private Handler mHandler = new Handler();
    private long exitTime = 0;
    private ViewGroup ll_home_dynamic_contentView;
    private ContentViewDefault contentview_home_scroll;
    private ContentViewInput contentview_home_input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLyTitleView().setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mHandler.post(delayInit);
            }
        });
    }

    private Runnable delayInit = new Runnable() {
        @Override
        public void run() {
            mBtmDefaultBar.bootCompleted();
            mBtmControlBar.bootCompleted();
        }
    };

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mPopMask = (FrameLayout) findViewById(R.id.pop_mask);
        if (mPopMask != null) {
//            mPopMask.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    hide();
//                }
//            });
            mPopMask.setOnTouchListener(this);
        }
        ViewStub main_view_stub_one = (ViewStub) findViewById(R.id.main_view_stub_one);
        mBtmDefaultBar = (MainBottomDefaultBar) main_view_stub_one.inflate();
        ViewStub main_view_stub_two = (ViewStub) findViewById(R.id.main_view_stub_two);
        mBtmControlBar = (MainBottomControlBar) main_view_stub_two.inflate();

        contentview_home_input = (ContentViewInput) View.inflate(this, R.layout.contentview_home_input, null);
        contentview_home_scroll = (ContentViewDefault) View.inflate(this, R.layout.contentview_home_scroll, null);
        contentview_home_scroll.setOnInputEditAction(this);
        contentview_home_input.setOnInputEditAction(this);
        ll_home_dynamic_contentView = (ViewGroup) findViewById(R.id.ll_home_dynamic_contentView);
        ll_home_dynamic_contentView.removeAllViews();
        ll_home_dynamic_contentView.addView(contentview_home_scroll);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EvtHome(EvtHome event) {
        if (event.getActionInt() == Constants.POP_SHOW) {
            show();
        } else if (event.getActionInt() == Constants.POP_HIDE) {
            hide();
        }
    }

    private void show() {
        PopMenuView popMenuView = mBtmDefaultBar.getPopMenuView();
        FrameLayout frameLayout = (FrameLayout) this.getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.addView(popMenuView, params);
        setVisible(mPopMask);
        setVisible(popMenuView);
        setVisible(mBtmControlBar);
    }

    private void hide() {
        PopMenuView popMenuView = mBtmDefaultBar.getPopMenuView();
        FrameLayout frameLayout = (FrameLayout) this.getWindow().getDecorView().findViewById(android.R.id.content);
        if (popMenuView != null) {
            frameLayout.removeView(popMenuView);
        }
        setGone(mPopMask);
        setGone(mBtmControlBar);
        setGone(popMenuView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mBtmDefaultBar.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBtmDefaultBar.getPopMenuView().getVisibility() == View.VISIBLE) {
            hide();
            return false;
        }
        if (contentview_home_input.getVisibility()==View.VISIBLE && contentview_home_input.getCustomWebview().canGoBack()) {
            contentview_home_input.getCustomWebview().goBack();
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.str_exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 150);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean result = true;
        PopMenuView popMenuView = mBtmDefaultBar.getPopMenuView();
        View popContent = ((ViewGroup) popMenuView.getChildAt(0)).getChildAt(0);
        if (popMenuView.getVisibility() != View.GONE) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                int touchX = (int) event.getRawX();
                int touchY = (int) event.getRawY();
                int[] location = new int[2];
                popContent.getLocationOnScreen(location);
                //location[0] or location[1] always get 0 when you won't get the correct 'popContent';
                int x_begin = location[0];
                int y_begin = location[1];
                int x_end = x_begin + popContent.getRight() - popContent.getLeft();
                if (touchY <= y_begin && touchX <= x_end && touchX >= x_begin) {
                    result = true;
                    hide();
                } else {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void updateToInputContentView() {
        ll_home_dynamic_contentView.removeAllViews();
        ll_home_dynamic_contentView.addView(contentview_home_input);
        contentview_home_input.toggleSoftInput(true);
    }

    @Override
    public void updateToDefaultContentView() {
        ll_home_dynamic_contentView.removeAllViews();
        ll_home_dynamic_contentView.addView(contentview_home_scroll);
    }
}
