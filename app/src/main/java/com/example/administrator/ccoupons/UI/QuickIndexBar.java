package com.example.administrator.ccoupons.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class QuickIndexBar extends View {

    private String[] letterArr = {"热门", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Paint paint;
    int ColorDefault = Color.GRAY;
    int ColorPressed = Color.BLACK;
    boolean pressed = false;

    Paint bg_paint;

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        paint.setColor(Color.RED);
        bg_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int size = 30;
        paint.setTextSize(size);
        //文字绘制的起点默认是左下角，设置起点为文字底边的中心，baseline基准线
        paint.setTextAlign(Paint.Align.CENTER);

    }

    float cellHeight;//一个格子的高
    float x;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / letterArr.length;
        x = getMeasuredWidth() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (pressed) {
            setBackgroundColor(Color.LTGRAY);
        }
        else setBackgroundColor(Color.parseColor("#00000000"));
        //遍历26个字母，对每个字母进行绘制
        for (int i = 0; i < letterArr.length; i++) {
            String text = letterArr[i];
            //算法：格子高的一半 + 文字高的一半 + i*格子的高
            float y = cellHeight / 2 + getTextHeight(text) / 2 + i * cellHeight;

            paint.setTypeface(Typeface.DEFAULT_BOLD);
            //更改颜色
            paint.setColor(i == index ? ColorPressed : ColorDefault);

            canvas.drawText(text, x, y, paint);
        }

    }

    int index = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pressed = true;
                int tempIndex = (int) (event.getY() / cellHeight);
                if (tempIndex != index) {
                    index = tempIndex;

                    //对index进行合法性的判断
                    if (index >= 0 && index < letterArr.length) {
                        String letter = letterArr[index];
                        System.out.println("pressed letter " + letter);
                        if (listener != null) {
                            listener.onLetterChange(letter);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                pressed = false;
                //重置为-1
                index = -1;
                if (listener != null) {
                    listener.onRelease();
                }

                break;
        }

        //重绘
        invalidate();

        return true;
    }

    /**
     * 获取文字的高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        //当下面的方法执行完，bounds就有值了
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    private OnLetterChangeListener listener;

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.listener = listener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);

        void onRelease();
    }
}
