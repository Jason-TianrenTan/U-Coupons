package com.example.administrator.ccoupons.Tools;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * Activities inherit from this activity to support slide rightward
 */
public class SlideBackActivity extends AppCompatActivity {
        //手指上下滑动时的最小速度
        private static final int YSPEED_MIN = 1000;
        //手指向右滑动时的最小距离
        private static final int XDISTANCE_MIN = 50;
        //手指向上滑或下滑时的最小距离
        private static final int YDISTANCE_MIN = 100;
        //记录手指按下时的横坐标。
        private float xDown;
        //记录手指按下时的纵坐标。
        private float yDown;
        //记录手指移动时的横坐标。
        private float xMove;
        //记录手指移动时的纵坐标。
        private float yMove;
        //用于计算手指滑动的速度。
        private VelocityTracker mVelocityTracker;

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            createVelocityTracker(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDown = event.getRawX();
                    yDown = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    xMove = event.getRawX();
                    yMove= event.getRawY();
                    int distanceX = (int) (xMove - xDown);
                    int distanceY= (int) (yMove - yDown);
                    int ySpeed = getScrollVelocity();
                    if(distanceX > XDISTANCE_MIN &&(distanceY<YDISTANCE_MIN&&distanceY>-YDISTANCE_MIN)&& ySpeed < YSPEED_MIN) {
                        finish();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    recycleVelocityTracker();
                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(event);
        }
        /**
         * creat VelocityTracker object and add the slide incident
         *
         * @param event
         *
         */
        private void createVelocityTracker(MotionEvent event) {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);
        }

        /**
         * recycle VelocityTracker object
         */
        private void recycleVelocityTracker() {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        /**
         *
         * @return speed of slide
         */
        private int getScrollVelocity() {
            mVelocityTracker.computeCurrentVelocity(1000);
            int velocity = (int) mVelocityTracker.getYVelocity();
            return Math.abs(velocity);
        }
}
