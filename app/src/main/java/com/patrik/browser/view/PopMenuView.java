package com.patrik.browser.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.patrik.browser.R;
import com.patrik.browser.activity.MainActivity;
import com.patrik.browser.activity.ScreenShotActivity;
import com.patrik.browser.tool.LogUtils;


/**
 * PopMenuView
 * Create by patrik on 2016/8/30.
 */
public class PopMenuView extends FrameLayout implements View.OnClickListener, View.OnTouchListener {
    private Context mContext;

    public PopMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public PopMenuView(Context context) {
        super(context);
        mContext = context;
        initMenuView(context);
        initListenser();
    }

    private void initMenuView(Context context) {
        View contentView = View.inflate(context, R.layout.view_pop, null);
        this.addView(contentView);
        this.setVisibility(View.GONE);
        ImageView mIv_update_notice = (ImageView) findViewById(R.id.iv_update_notice);
    }

    private void initListenser() {
        findViewById(R.id.pop_night_mode).setOnClickListener(this);
        findViewById(R.id.pop_no_pic).setOnClickListener(this);
        findViewById(R.id.pop_fullscreen).setOnClickListener(this);
        findViewById(R.id.pop_inco_mode).setOnClickListener(this);
        findViewById(R.id.pop_add_slide_bookmark).setOnClickListener(this);
        findViewById(R.id.pop_bookmarklist).setOnClickListener(this);
        findViewById(R.id.pop_refresh).setOnClickListener(this);
        findViewById(R.id.pop_dowload_manager).setOnClickListener(this);
        findViewById(R.id.pop_share).setOnClickListener(this);
        findViewById(R.id.pop_screenshot).setOnClickListener(this);
        findViewById(R.id.pop_light_always_setting).setOnClickListener(this);
        findViewById(R.id.fl_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_night_mode:

                break;
            case R.id.pop_no_pic:

                break;
            case R.id.pop_fullscreen:

                break;
            case R.id.pop_inco_mode:

                break;
            case R.id.pop_add_slide_bookmark:

                break;
            case R.id.pop_bookmarklist:

                break;
            case R.id.pop_refresh:

                break;
            case R.id.pop_dowload_manager:

                break;
            case R.id.pop_share:

                break;
            case R.id.pop_screenshot:
                onScreenShot();
                break;
            case R.id.pop_light_always_setting:

                break;
            case R.id.fl_update:

                break;
        }
    }

    /**
     * @return void
     */
    private void onScreenShot() {
        LogUtils.e(this,"onScreenShot");

        Rect frame = new Rect();
        MainActivity mainActivity = ((MainActivity) (mContext));
        mainActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        Intent intent = new Intent(mainActivity, ScreenShotActivity.class);
        intent.setFlags(0x20020000);
        intent.putExtra("rc", frame);
        mainActivity.startActivity(intent);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
