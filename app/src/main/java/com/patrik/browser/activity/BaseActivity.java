package com.patrik.browser.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.patrik.browser.R;

/**
 * BaseActivity
 * Create by patrik on 2016/8/29.
 */
public abstract class BaseActivity extends AppCompatActivity {
    abstract protected int setContentView();

    abstract protected void initView();
    View mLyTitleView;
    TextView mTv_left;
    TextView mTv_title;
    TextView mTv_right;
    ImageView mIv_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSysBar();
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.layout_title_view, null);
        mLyTitleView = view.findViewById(R.id.ly_title_view);
        FrameLayout fl_content_view = (FrameLayout) view.findViewById(R.id.fl_content_view);
        View content_view = View.inflate(this, setContentView(), null);
        fl_content_view.addView(content_view);
        setContentView(view);

        init();
    }

    private void initSysBar() {
        //hide the title bar
        getSupportActionBar().hide();
        //加上这两句不知道为什么会造成空指针
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    private void init() {
        mTv_left = (TextView) findViewById(R.id.tv_left);
        mTv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View mFl_right = findViewById(R.id.fl_right);
        mFl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hook();
            }
        });
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_right = (TextView) findViewById(R.id.tv_right);
        mIv_right = (ImageView) findViewById(R.id.iv_right);
        initView();
    }
    public View getTvLeft(){

        return mTv_left;
    }
    public View getTvRight(){
        return mTv_right;
    }
    public View getIvRight(){
        return mIv_right;
    }
    public View getLyTitleView(){
        return mLyTitleView;
    }
    protected void setTv_title(int strid) {
        mTv_title.setText(strid);
    }

    protected void setTv_right(int strid) {
        mTv_right.setVisibility(View.VISIBLE);
        mTv_right.setText(strid);
    }

    protected void setIv_right(int drawble) {
        mIv_right.setVisibility(View.VISIBLE);
        mIv_right.setImageResource(drawble);
    }

    protected void hook() {

    }

    public void gotoActivity(Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra("bundle", bundle);
        intent.setClass(this, targetActivity);
        startActivity(intent);
    }

    public void gotoActivityForResult(Class<?> targetActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.putExtra("bundle", bundle);
        intent.setClass(this, targetActivity);
        startActivityForResult(intent, requestCode);
    }

    public void setVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
