package com.louisgeek.dropdownviewlib.tools;

import android.content.Context;

/**
 * Created by louisgeek on 2016/6/17.
 */
public class SizeTool {
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
