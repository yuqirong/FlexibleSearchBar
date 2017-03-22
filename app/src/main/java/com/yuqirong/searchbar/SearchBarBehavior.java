package com.yuqirong.searchbar;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author maomao
 * @email qirong.yu@jubaobar.com
 * @date 2017-3-9
 */

public class SearchBarBehavior extends CoordinatorLayout.Behavior<View> {

    public SearchBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if(child instanceof SearchBarView){


        }


        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
