package com.patrik.browser.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.patrik.browser.R;
import com.patrik.browser.base.OnInputEditAction;
import com.patrik.browser.event.EvtHome;
import com.patrik.browser.event.EvtInput;
import com.patrik.browser.tool.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * usage
 * Create by patrik on 2017/1/22.
 */
public class ContentViewInput extends LinearLayout implements View.OnClickListener,CustomMutipleFuncEditText.OnMutipleLisen{
    private CustomWebview webview_homepage;
    private ImageView iv_action;
    private TextView tv_action;
    private CustomMutipleFuncEditText edit_home_inputurl;
    private final String str_go = getResources().getString(R.string.str_action_go);
    private final String str_cancel = getResources().getString(R.string.str_action_cancel);

    public ContentViewInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        edit_home_inputurl = (CustomMutipleFuncEditText) findViewById(R.id.edit_home_inputurl);
        webview_homepage = (CustomWebview) findViewById(R.id.webview_homepage);
        tv_action = (TextView) findViewById(R.id.tv_action);
        iv_action = (ImageView) findViewById(R.id.iv_action);
        edit_home_inputurl.setOnMutipleLisen(this);
        tv_action.setOnClickListener(this);
        iv_action.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EvtInput(EvtInput event) {
        if (event.getActionInt() == Constants.GOBACK) {
            if(webview_homepage.canGoBack()){
                webview_homepage.goBack();
            }else{
                EventBus.getDefault().post(new EvtHome(Constants.GOFIRSTPAGE));
            }
        } else if (event.getActionInt() == Constants.GOFORWARD) {
            webview_homepage.goForward();
        } else if (event.getActionInt() == Constants.LOADING) {
            iv_action.setContentDescription("pageloading");
            iv_action.setImageResource(R.mipmap.close_black);
        } else if (event.getActionInt() == Constants.PAGEFINISH) {
            iv_action.setContentDescription("pagefinish");
            iv_action.setImageResource(R.mipmap.refresh_black);
        }
    }

    private void pageLoading() {
        String url = edit_home_inputurl.getText().toString().trim();
        if(!isUrl(url)){
            iv_action.setVisibility(View.VISIBLE);
            tv_action.setVisibility(View.GONE);
            webview_homepage.loadUrl(url);
        }else{
            Toast.makeText(getContext().getApplicationContext(),"请输入网址，暂不支持纯文字搜索",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_action:
                String tv_tempstr = tv_action.getText().toString();
                if (tv_tempstr.equals(str_go)) {
                    pageLoading();
                } else if (tv_tempstr.equals(str_cancel)) {
                    if (listener != null) {
                        listener.updateToDefaultContentView();
                    }
                }
                //开启新页面时,关闭虚拟键盘(#patrik)2017/2/4.
                toggleSoftInput(false);
                break;
            case R.id.iv_action:
                CharSequence charSequence = iv_action.getContentDescription();
                if(charSequence!=null){
                    String iv_tempstr = charSequence.toString();
                    if (iv_tempstr.equalsIgnoreCase("pageloading")) {
                        webview_homepage.stopLoading();
                        iv_action.setImageResource(R.mipmap.refresh_black);
                    } else if (iv_tempstr.equalsIgnoreCase("pagefinish")) {
                        webview_homepage.reload();
                        iv_action.setImageResource(R.mipmap.close_black);
                    }
                }
                break;
        }
    }
    private boolean isUrl(String editInput) {
        String url = editInput;
        if (TextUtils.isEmpty(url))
            return false;

        return !(Patterns.WEB_URL.matcher(url).matches());
    }

    public CustomWebview getCustomWebview() {
        return webview_homepage;
    }

    private OnInputEditAction listener;

    public void setOnInputEditAction(OnInputEditAction listener) {
        this.listener = listener;
    }

    public void toggleSoftInput(boolean isOpen){
        InputMethodManager imm = (InputMethodManager)getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(isOpen){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            edit_home_inputurl.requestFocus();
        }else{
            imm.hideSoftInputFromWindow(edit_home_inputurl.getWindowToken(),0);
            edit_home_inputurl.clearFocus();
        }
    }
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void editChange(int length, boolean isSetTxt) {
        tv_action.setVisibility(View.VISIBLE);
        iv_action.setVisibility(View.GONE);
        if (length > 0) {
            tv_action.setText(str_go);
        } else {
            tv_action.setText(str_cancel);
        }
    }
}
