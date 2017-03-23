package com.yuqirong.searchbar;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author maomao
 * @email qirong.yu@jubaobar.com
 * @date 2017-3-9
 */

public class SearchBarBehavior extends CoordinatorLayout.Behavior<View> {

    private SearchBarView searchBarView;

    public SearchBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency instanceof SearchBarView) {
            searchBarView = (SearchBarView) dependency;
            return true;
        } else if (dependency instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) dependency);
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = viewGroup.getChildAt(i);
                if (view instanceof SearchBarView) {
                    searchBarView = (SearchBarView) view;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (child instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) child;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    searchBarView.startOpen();
                } else {
                    searchBarView.startClose();
                }
            }
        }
        return true;
    }

}
