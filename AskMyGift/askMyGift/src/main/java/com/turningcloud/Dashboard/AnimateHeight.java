package com.turningcloud.Dashboard;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by sumeet on 29/7/15.
 */
public class AnimateHeight extends Animation {
    int initialHeight;
    int targetHeight;
    private final boolean down;
    View view;
    public AnimateHeight(View view,int initialHeight,int targetHeight,boolean down){
        this.view = view;
        this.initialHeight= initialHeight;
        this.targetHeight = targetHeight;
        this.down = down;
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        if (down) {
            newHeight = initialHeight+(int) (targetHeight * interpolatedTime);
            Log.d("com.turningcloud","before height is"+newHeight);
        } else {
            newHeight = initialHeight+(int) (targetHeight * (1 - interpolatedTime));
            Log.d("com.turningcloud","height is"+newHeight);
        }
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }
    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
