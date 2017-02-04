package com.patrik.browser.activity;

import android.graphics.Rect;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;

import com.patrik.browser.R;
import com.patrik.browser.base.BaseActivity;
import com.patrik.browser.view.CropView;

/**
 * ScreenShot
 * Create by patrik on 2016/8/29.
 */
public class ScreenShotActivity extends BaseActivity {
    public Rect windowRc;
    public int mBottomHeight;
    @Override
    protected int setContentView() {
        return R.layout.activity_screen_shot;
    }

    @Override
    protected void initView() {
        windowRc = getIntent().getParcelableExtra("rc");
        if (windowRc.top == 0) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        ViewStub viewStub = (ViewStub)findViewById(R.id.view_stub_screen_shot);
        viewStub.setLayoutResource(R.layout.view_screen_shot);
        viewStub.inflate();
        mBottomHeight = viewStub.getLayoutParams().height;
        CropView cropView = new CropView(this,720,1824);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(cropView,lp);
    }
}
