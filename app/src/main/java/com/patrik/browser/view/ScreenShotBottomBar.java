package com.patrik.browser.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.patrik.browser.R;

/**
 * ScreenShotBottomBar
 * Create by patrik on 2016/8/30.
 */
public class ScreenShotBottomBar  extends LinearLayout implements View.OnClickListener, ViewCommonInterface {
    private View mIv_crop_zone,mIv_crop_full,mIv_back;
    public ScreenShotBottomBar(Context context, AttributeSet attri) {
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
        mIv_crop_zone = findViewById(R.id.iv_crop_zone);
        mIv_crop_full = findViewById(R.id.iv_crop_full);
        mIv_back = findViewById(R.id.iv_back);
    }

    @Override
    public void bootCompleted() {
        mIv_crop_zone.setOnClickListener(this);
        mIv_crop_full.setOnClickListener(this);
        mIv_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_crop_zone:
                bottomCropZoneClick();
                break;
            case R.id.iv_crop_full:
                bottomCropFullClick();
                break;
            case R.id.iv_back:
                bottomBackClick();
                break;
        }
    }
    private void bottomCropZoneClick(){

    }
    private void bottomCropFullClick(){

    }
    private void bottomBackClick(){

    }
}

