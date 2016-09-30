/**
 * @version 1.0  2015年1月27日
 */
package com.louisgeek.dropdownviewlib.tools;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘操作工具类
 * @author louisgeek
 * 2016年9月22日13:21:20
 */
public class KeyBoardTool {

	//隐藏虚拟键盘
	public static void hideKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
		if ( imm.isActive( ) ) {
			imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

		}
	}

	//显示虚拟键盘
	public static void showKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

		imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

	}
}
