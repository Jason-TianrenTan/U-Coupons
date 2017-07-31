package com.example.administrator.ccoupons.CustomEditText;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import android.support.v7.widget.AppCompatEditText;

import com.example.administrator.ccoupons.R;

public class PasswordToggleEditText extends AppCompatEditText implements OnFocusChangeListener, TextWatcher {
    /**
     * 眼睛按钮
     */
    private Drawable mToggleDrawable;
    private boolean hidden = true;

    public PasswordToggleEditText(Context context) {
        this(context, null);
    }

    public PasswordToggleEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordToggleEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化右边小眼睛的控件
     */
    private void init() {
        mToggleDrawable = getCompoundDrawables()[2];
        if (mToggleDrawable == null) {
            mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.show_password);
        }
        mToggleDrawable.setBounds(0, 0, mToggleDrawable.getIntrinsicWidth(), mToggleDrawable.getIntrinsicHeight());
        setToggleIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight() - mToggleDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    if (hidden) {
                        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        postInvalidate();
                        CharSequence charSequence = getText();
                        if (charSequence != null) {
                            Spannable spanText = (Spannable) charSequence;
                            Selection.setSelection(spanText, charSequence.length());
                        }
                        hidden = false;
                    }
                    else {
                        hidden = true;
                        setTransformationMethod(PasswordTransformationMethod.getInstance());
                        postInvalidate();
                    }
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
            postInvalidate();
            setSelection(getText().length());
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setToggleIconVisible(getText().length() > 0);
        } else {
            setToggleIconVisible(false);
        //    setShakeAnimation();
            setTransformationMethod(PasswordTransformationMethod.getInstance());
            hidden = true;
            postInvalidate();
            setSelection(getText().length());
        }
    }

    public void setToggleIconVisible(boolean visible) {
        Drawable right = visible ? mToggleDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setToggleIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(3));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}