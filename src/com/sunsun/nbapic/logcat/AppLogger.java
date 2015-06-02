package com.sunsun.nbapic.logcat;

import android.util.Log;

import com.sunsun.nbapic.config.AppConfig;

/**
 * log输出处理类
 * 
 * @version 2015年3月18日 下午3:19:13
 */
public class AppLogger {

	public static String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		if (sts == null) {
			return null;
		}

		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(AppLogger.class.getClass().getName())) {
				continue;
			}
			return "[" + Thread.currentThread().getName() + "("
					+ Thread.currentThread().getId() + "): " + st.getFileName()
					+ ":" + st.getLineNumber() + "]";
		}

		return null;
	}

	public static String createMessage(String msg) {
		String functionName = getFunctionName();
		String message = (functionName == null ? msg
				: (functionName + " - " + msg));
		return message;
	}

	/**
	 * log.i
	 */
	public void i(String msg) {
		if (AppConfig.DEBUG) {
			String message = createMessage(msg);
			Log.i(KGLog.TAG, message);
		}
	}

	/**
	 * log.v
	 */
	public static void v(String msg) {
		v(KGLog.TAG, msg);
	}

	/**
	 * log.v
	 */
	public static void v(String tag, String msg) {
		if (AppConfig.DEBUG) {
			String message = createMessage(msg);
			Log.v(tag, message);
		}
	}

	/**
	 * log.d
	 */
	public static void d(String msg) {
		d(KGLog.TAG, msg);
	}

	public static void d(String tag, String msg) {
		if (AppConfig.DEBUG) {
			String message = createMessage(msg);
			Log.d(tag, message);
		}
	}

	/**
	 * log.e
	 */
	public static void e(String msg) {
		e(KGLog.TAG, msg);

	}

	/**
	 * log.e
	 */
	public static void e(String tag, String msg) {
		if (AppConfig.DEBUG) {
			String message = createMessage(msg);
			Log.e(tag, message);
		} else {
			ExceptionLogger.error(msg);
		}
	}

	/**
	 * log.error
	 */
	public static void error(Exception e) {
		if (AppConfig.DEBUG) {
			StringBuffer sb = new StringBuffer();
			String name = getFunctionName();
			StackTraceElement[] sts = e.getStackTrace();

			if (name != null) {
				sb.append(name + " - " + e + "\r\n");
			} else {
				sb.append(e + "\r\n");
			}
			if (sts != null && sts.length > 0) {
				for (StackTraceElement st : sts) {
					if (st != null) {
						sb.append("[ " + st.getFileName() + ":"
								+ st.getLineNumber() + " ]\r\n");
					}
				}
			}
			Log.e(KGLog.TAG, sb.toString());
		} else {
			ExceptionLogger.error(e);
		}
	}
}
