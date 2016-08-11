package com.louisgeek.dropdownviewlib.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * mRecyclerView.addItemDecoration(new SpacesItemDecoration(mMargin));
 * Created by louisgeek on 2016/6/21.
 */
public class SpacesItemDecorationTwo extends RecyclerView.ItemDecoration {

    private int halfSpace;

    public SpacesItemDecorationTwo(int space) {
        this.halfSpace = space / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getPaddingLeft() != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
            parent.setClipToPadding(false);
        }

        outRect.top = halfSpace;
        outRect.bottom = halfSpace;
        outRect.left = halfSpace;
        outRect.right = halfSpace;
    }
}