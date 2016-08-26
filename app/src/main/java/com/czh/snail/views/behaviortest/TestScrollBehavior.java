package com.czh.snail.views.behaviortest;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v13.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenzhihui on 16/8/26.
 */

public class TestScrollBehavior extends CoordinatorLayout.Behavior<View> {

    public TestScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        int leftScrolled = target.getScrollY();
        child.setScrollY(leftScrolled);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        ((NestedScrollView) child).fling((int)velocityY);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

}
