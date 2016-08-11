package com.louisgeek.dropdownviewlib;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void testA(){
        ProCateSelectView proCateSelectView=new ProCateSelectView(getContext());
    }
}