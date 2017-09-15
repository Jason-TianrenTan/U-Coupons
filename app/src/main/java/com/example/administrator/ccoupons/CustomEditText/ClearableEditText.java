package com.example.administrator.ccoupons.CustomEditText;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.administrator.ccoupons.R;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public class ClearableEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    /**
     * 清除按钮
     */
    private Drawable mToggleDrawable;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * initialize icon on the right
     */
    private void init() {
        mToggleDrawable = getCompoundDrawables()[2];
        if (mToggleDrawable == null) {
            mToggleDrawable = ContextCompat.getDrawable(getContext(), R.drawable.clear_icon);
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
                    this.setText("");
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

        }
        return super.onTouchEvent(event);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    public void setToggleIconVisible(boolean visible) {
        Drawable right = visible ? mToggleDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * called when text in input changes
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
     * set shaking animation
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(3));
    }

    /**
     * Animation for shaking
     *
     * @param counts shakes per second
     * @return
     */
    public Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
