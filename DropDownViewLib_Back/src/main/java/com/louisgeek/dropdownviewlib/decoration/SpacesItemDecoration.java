package com.louisgeek.dropdownviewlib.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
 int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
 mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

 * Created by louisgeek on 2016/6/21.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}