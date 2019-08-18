package cn.zjnu.myjournalquery;

import android.util.Log;

public  class MyUtils {
	private static boolean isOpen=true;
	public static void LOGD(String tag,String msg){
		if(isOpen){
			Log.d(tag, msg);
		}
	}
}
