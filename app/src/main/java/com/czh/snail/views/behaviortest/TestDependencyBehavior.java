package com.czh.snail.views.behaviortest;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v13.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by chenzhihui on 16/8/26.
 */

public class TestDependencyBehavior extends CoordinatorLayout.Behavior<View> {

    public TestDependencyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof Button || dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        return super.onDependentViewChanged(parent, child, dependency);
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        return true;
    }
}
