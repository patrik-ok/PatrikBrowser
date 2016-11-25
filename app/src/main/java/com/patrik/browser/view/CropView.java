package com.patrik.browser.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.patrik.browser.activity.ScreenShotActivity;
/**
 * CropView
 * Create by patrik on 2016/8/29.
 */
public class CropView extends View {
    private static final int SCOPE = 35;
    private static final int MINWIDTH = SCOPE * 4;

    private int left = 0;
    private int top = 0;
    private int right = 0;
    private int bottom = 0;
    private int mScreenHeight;
    private int mScreenWidth;
    private Context mContext;
    private int lineColor;

    public CropView(Context context, int screenwidth, int screenheight) {
        super(context);
        mContext = context;
        mScreenHeight = screenheight;
        mScreenWidth = screenwidth;
        ScreenShotActivity capturePicActivity = null;
        //        屏幕的高
        int scnHeight = mScreenHeight;
        if (context instanceof ScreenShotActivity) {
            capturePicActivity = (ScreenShotActivity) context;
        }

        int windowRcTop=0;
        int bottomHeight=0;
        if (capturePicActivity != null) {

            windowRcTop = capturePicActivity.windowRc.top;
            bottomHeight = capturePicActivity.mBottomHeight;
            scnHeight = screenheight + windowRcTop + bottomHeight;
        }
        if (scnHeight>mScreenWidth){
            left = mScreenWidth / 6;
            right = 5 * mScreenWidth / 6;
//        top = 2 * mScreenHeight / 6;
//        bottom = 4 * mScreenHeight / 6;
//
//            top = (mScreenHeight - (right - left)) / 2;
            top = (mScreenHeight - (right - left)) / 2;

            bottom = top + (right - left);
        }else{

            left=(mScreenWidth-(scnHeight*4 / 6))/2;
            right=left+(scnHeight*4/6);
            top=(mScreenHeight-(scnHeight*4/6))/2;
            bottom=top+(scnHeight*4/6);

        }



//        lineColor = context.getResources().getColor(R.color.skin_capture_line_color);
//        lineColor = SkinManager.getInstance().getResourceManager().getColor("skin_capture_line_color");
    }

    float mOffsetX = 0, mOffsetY = 0;
    float mX = 0, mY = 0;

    boolean mINFlag = false;
    boolean mChangeLeftFlag = false;
    boolean mChangeRightFlag = false;
    boolean mChangeTopFlag = false;
    boolean mChangeBottomFlag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                mINFlag = false;
                mChangeLeftFlag = false;
                mChangeRightFlag = false;
                mChangeTopFlag = false;
                mChangeBottomFlag = false;
                if (mX < right - SCOPE && mX > left + SCOPE && mY < bottom - SCOPE && mY > top + SCOPE) {
                    mINFlag = true;
                    mOffsetX = mX - left;
                    mOffsetY = mY - top;
                } else if ((mX > left - CropView.SCOPE) && mX < left + CropView.SCOPE) {
                    if ((mY > top - CropView.SCOPE) && (mY < top + CropView.SCOPE)) {
                        mChangeLeftFlag = true;
                        mOffsetX = mX - left;
                        mChangeTopFlag = true;
                        mOffsetY = mY - top;
                    } else if ((mY > bottom - CropView.SCOPE)// 如果触点选中了下边框
                            && (mY < bottom + CropView.SCOPE)) {
                        mChangeLeftFlag = true;
                        mOffsetX = mX - left;
                        mChangeBottomFlag = true;
                        mOffsetY = mY - bottom;
                    } else if ((mY > top) && (mY < bottom)) {
                        mChangeLeftFlag = true;
                        mOffsetX = mX - left;
                    }
                } else if ((mX > right - CropView.SCOPE)// 如果触点选中了右边框
                        && (mX < right + CropView.SCOPE)) {
                    if ((mY > top - CropView.SCOPE)
                            && (mY < top + CropView.SCOPE)) {
                        mOffsetX = mX - right;
                        mChangeRightFlag = true;
                        mChangeTopFlag = true;
                        mOffsetY = mY - top;
                    } else if ((mY > bottom - CropView.SCOPE)// 如果触点选中了下边框
                            && (mY < bottom + CropView.SCOPE)) {
                        mOffsetX = mX - right;
                        mChangeRightFlag = true;
                        mChangeBottomFlag = true;
                        mOffsetY = mY - bottom;
                    } else if ((mY > top)
                            && (mY < bottom)) {
                        mOffsetX = mX - right;
                        mChangeRightFlag = true;
                    }

                }
//			else if ((mX > left - CropView.SCOPE)// 如果触点选中了左边框
//					&& mX < left + CropView.SCOPE
//					&& (mY > top)
//					&& (mY < bottom)) {
//				mChangeLeftFlag = true;
//				mOffsetX = mX - left;
//			}
//			else if ((mX > right - CropView.SCOPE)// 如果触点选中了右边框
//					&& (mX < right + CropView.SCOPE)
//					&& (mY > top)
//					&& (mY < bottom)) {
//				mOffsetX = mX - right;
//				mChangeRightFlag = true;
//			}
                else if ((mY > top - CropView.SCOPE)// 如果触点选中了上边框
                        && (mY < top + CropView.SCOPE)
                        && (mX > left)
                        && (mX < right)) {
                    mChangeTopFlag = true;
                    mOffsetY = mY - top;
                } else if ((mY > bottom - CropView.SCOPE)// 如果触点选中了下边框
                        && (mY < bottom + CropView.SCOPE)
                        && (mX > left)
                        && (mX < right)) {
                    mChangeBottomFlag = true;
                    mOffsetY = mY - bottom;
                }
            case MotionEvent.ACTION_MOVE:
                if (mINFlag) {
                    int width = right - left;
                    int heigth = bottom - top;
                    top = (int) (event.getY() - mOffsetY);
                    if (top < 0)
                        top = 0;
                    bottom = top + heigth;
                    if (bottom > mScreenHeight) {
                        bottom = mScreenHeight;
                        top = bottom - heigth;
                    }

                    left = (int) (event.getX() - mOffsetX);
                    if (left < 0)
                        left = 0;
                    right = left + width;
                    if (right > mScreenWidth) {
                        right = mScreenWidth;
                        left = right - width;
                    }
                }
                if (mChangeLeftFlag) {

                    left = (int) (event.getX() - mOffsetX);
                    if (left < 0)
                        left = 0;
                    else if (right - left < MINWIDTH) {
                        left = right - MINWIDTH;
                    }
                }
                if (mChangeRightFlag) {
                    right = (int) (event.getX() - mOffsetX);
                    if (right > mScreenWidth) {
                        right = mScreenWidth;
                    } else if (right - left < MINWIDTH) {
                        right = left + MINWIDTH;
                    }
                }
                if (mChangeTopFlag) {
                    top = (int) (event.getY() - mOffsetY);
                    if (top < 0)
                        top = 0;
                    else if (bottom - top < MINWIDTH) {
                        top = bottom - MINWIDTH;
                    }
                }
                if (mChangeBottomFlag) {
                    bottom = (int) (event.getY() - mOffsetY);
                    if (bottom > mScreenHeight) {
                        bottom = mScreenHeight;
                    } else if (bottom - top < MINWIDTH) {
                        bottom = top + MINWIDTH;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mINFlag = false;
                mChangeLeftFlag = false;
                mChangeRightFlag = false;
                mChangeTopFlag = false;
                mChangeBottomFlag = false;
                break;

        }
        invalidate();
        return mINFlag || mChangeLeftFlag || mChangeRightFlag || mChangeTopFlag || mChangeBottomFlag;
    }

    public Rect getRegion() {
        return new Rect(left, top, right, bottom);
    }

    Paint paint1 = new Paint();
    Paint paint2 = new Paint();
    Paint paint3 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        paint1.setStyle(Style.FILL);
        paint1.setColor(Color.BLACK);
        paint1.setAlpha(160);

        canvas.drawRect(0, 0, mScreenWidth, top, paint1);
        canvas.drawRect(0, bottom, mScreenWidth, mScreenHeight, paint1);
        canvas.drawRect(0, top, left, bottom, paint1);
        canvas.drawRect(right, top, mScreenWidth, bottom, paint1);

        paint2.setStyle(Style.STROKE);
        // mPaint.setStrokeMiter(6);
        float width = Dp2Px(mContext, 1.5f);
        paint2.setStrokeWidth(width);
        paint2.setColor(lineColor);

        canvas.drawRect(left, top, right, bottom, paint2);

        paint3.setStyle(Style.FILL_AND_STROKE);
        float width2 = Dp2Px(mContext, 6f);
        paint3.setStrokeWidth(width2);
        paint3.setColor(lineColor);
//        canvas.drawCircle(left, top, width2, paint3);
//        canvas.drawCircle(right, top, width2, paint3);
//        canvas.drawCircle(right, bottom, width2, paint3);
//        canvas.drawCircle(left, bottom, width2, paint3);
        float len = Dp2Px(mContext, 10);
        canvas.drawLine(left, top - width2 / 2, left, top + len, paint3);
        canvas.drawLine(left - width2 / 2, top, left + len, top, paint3);

        canvas.drawLine(right, top - width2 / 2, right, top + len, paint3);
        canvas.drawLine(right + width2 / 2, top, right - len, top, paint3);

        canvas.drawLine(right, bottom + width2 / 2, right, bottom - len, paint3);
        canvas.drawLine(right + width2 / 2, bottom, right - len, bottom, paint3);

        canvas.drawLine(left, bottom + width2 / 2, left, bottom - len, paint3);
        canvas.drawLine(left - width2 / 2, bottom, left + len, bottom, paint3);
    }

    public float Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }


}