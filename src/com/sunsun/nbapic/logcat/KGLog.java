package com.sunsun.nbapic.logcat;

import android.util.Log;

/**
 * 日志工具
 * @author sunsun
 * @date 2015年6月2日 上午11:41:21
 */
public class KGLog {

	public static final String TAG = "NbaPicProject";
    
	public static final boolean HANDLE_CRASH = false;

	public static final boolean ISDEBUG = true;

    /**
     * 是否处于调试模式
     * 
     * @param debug
     */
    public static boolean isDebug() {
        return ISDEBUG;
    }

    public static void d(String msg) {
        if (ISDEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (ISDEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String msg) {
        if (ISDEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ISDEBUG) {
            Log.e(tag, msg);
        }
    }
}
