package com.patrik.browser.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.patrik.browser.R;

/**
 * usage
 * Create by patrik on 2017/2/4.
 */
public class CustomMutipleFuncEditText extends EditText {
    //cursor position
    //txt before inputText;
    private String inputAfterText;
    //is reset text
    private boolean hasReset;

    private Context mContext;

    private boolean isSetTxt = false;

    private Drawable mClearDrawable;

    private boolean mHasFocus;

    public CustomMutipleFuncEditText(Context context) {
        super(context);
        this.mContext = context.getApplicationContext();
    }

    public CustomMutipleFuncEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context.getApplicationContext();
        initEditText(context, attrs);
    }

    public CustomMutipleFuncEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context.getApplicationContext();
        initEditText(context, attrs);
    }

    private void initEditText(Context context, AttributeSet attrs) {
        mClearDrawable = getCompoundDrawables()[2]; // 获取drawableRight
        if (mClearDrawable == null) {
            // 如果为空，即没有设置drawableRight，则使用R.mipmap.close这张图片
            mClearDrawable = getResources().getDrawable(R.mipmap.clear_img);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mHasFocus = hasFocus;
                if (hasFocus && getText().length() > 0) {
                    setDrawableVisible(true); // 有焦点且有文字时显示图标
                } else {
                    setDrawableVisible(false);
                }
            }
        });
        // 默认隐藏图标
        setDrawableVisible(false);

//        TypedArray a = context.getApplicationContext().obtainStyledAttributes(attrs, R.styleable.CustomEdit);
//        isSetTxt = a.getBoolean(R.styleable.CustomEdit_tips_type, false);
//        InputFilter[] textFilters = new InputFilter[1];
//        textFilters[0] = new InputFilter.LengthFilter(Constants.INPUTMAX_LIMIT) {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end,
//                                       Spanned dest, int dstart, int dend) {
//                if (source.length() > 0 && dest.length() == Constants.INPUTMAX_LIMIT) {
//                    Toast.makeText(getContext(),getResources().getString(R.string.str_input_limit, Constants.INPUTMAX_LIMIT),Toast.LENGTH_SHORT).show();
//                }
//                return super.filter(source, start, end, dest, dstart, dend);
//            }
//        };
//        setFilters(textFilters);
        addTextChangedListener(new TextWatcher() {
            int cursorPos;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                cursorPos = s.length();
                if (hasReset) {
                    hasReset = false;
                } else {
                    inputAfterText = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mHasFocus) {
                    setDrawableVisible(s.length() > 0);
                }
                if (cursorPos < s.length()) {
                    if (start + before < start + count) {
                        CharSequence input = s.subSequence(start + before, start + count);
                        if (containsEmoji(input.toString())) {
                            Toast.makeText(getContext(), getResources().getString(R.string.str_not_support), Toast.LENGTH_SHORT).show();
                            setText(inputAfterText);
                            resetCursor(start + before);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = getText().length();
                if (lisen != null) {
                    lisen.editChange(length, isSetTxt);
                }
            }
        });
//        a.recycle();
    }
    /**
     * 我们无法直接给EditText设置点击事件，只能通过按下的位置来模拟clear点击事件
     * 当我们按下的位置在图标包括图标到控件右边的间距范围内均算有效
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int start = getWidth() - getTotalPaddingRight() + getPaddingRight(); // 起始位置
                int end = getWidth(); // 结束位置
                boolean available = (event.getX() > start) && (event.getX() < end);
                if (available) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void resetCursor(int index) {
        CharSequence text = getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, index);
        }
    }
    private void setDrawableVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }
    public interface OnMutipleLisen {
        void editChange(int length, boolean isSetTxt);
    }

    private OnMutipleLisen lisen;

    public void setOnMutipleLisen(OnMutipleLisen lisen) {
        this.lisen = lisen;
    }


    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
