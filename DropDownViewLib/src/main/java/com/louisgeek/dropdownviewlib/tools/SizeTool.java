package com.louisgeek.dropdownviewlib.tools;

import android.content.Context;
import android.view.View;

/**
 * Created by louisgeek on 2016/6/17.
 */
public class SizeTool {
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * @param v
     * @return
     */
    public static int getMeasuredWidthMy(View v) {
        int w_h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w_h, w_h);
        int width = v.getMeasuredWidth();
        System.out.println("measure width=" + width);
        return width;
    }

    /**
     * @param v
     * @return
     */
    public static int getMeasuredHeightMy(View v) {
        int w_h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w_h, w_h);
        int height = v.getMeasuredHeight();
        System.out.println("measure height=" + height);
        return height;
    }
}
