package com.sunsun.nbapic.logcat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings.Secure;

import com.sunsun.nbapic.NbaPicApplication;
import com.sunsun.nbapic.config.AppConfig;
import com.sunsun.nbapic.helper.StoragePathHelper;
import com.sunsun.nbapic.util.DateUtil;

/**
 * 异常信息输出处理类：输出格式化的异常信息
 * 
 * @version 2015年3月13日 下午3:50:30
 */
public class ExceptionLogger {

	private static final String TIME_FORMAT = "yyyy-MM-dd";
	private static final String CRASH_NAME = "crash.log_";

	public static String getCrashInfo(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		String crashInfo = writer.toString();
		printWriter.close();
		return crashInfo;
	}

	public static void logCrash(String crashInfo) {
		try {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("time", System.currentTimeMillis() / 1000);
			jsonObj.put("version", NbaPicApplication.getInstance()
					.getAppVersion());
			jsonObj.put("device", Secure.getString(NbaPicApplication
					.getInstance().getContentResolver(), Secure.ANDROID_ID));
			jsonObj.put("model", android.os.Build.MODEL);
			jsonObj.put("screen", AppConfig.widthPx + "_" + AppConfig.heightPx);
			jsonObj.put("system", android.os.Build.VERSION.SDK_INT);
			jsonObj.put("system_detail", android.os.Build.VERSION.RELEASE);
			jsonObj.put("exception", escape(crashInfo));
			write(jsonObj.toString() + "\n", CRASH_NAME);
		} catch (JSONException e) {
		}
	}

	public static void error(String msg) {
		logCrash(msg);
	}

	public static void error(Throwable ex) {
		logCrash(getCrashInfo(ex));
	}

	private static void write(String msg, String fileName) {
		File logDir = new File(StoragePathHelper.getLogPath());
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		File logFile = new File(StoragePathHelper.getLogPath()
				+ fileName
				+ DateUtil.timestampToFormatDate(System.currentTimeMillis(),
						TIME_FORMAT));
		FileWriter writer = null;
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			writer = new FileWriter(logFile, true);
			writer.write(msg);
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private static String escape(String value) {
		if (value == null) {
			return null;
		}
		String str = StringEscapeUtils.escapeJavaScript(value);
		Pattern p = Pattern.compile("\r\n|\r|\n");
		Matcher m = p.matcher(str);
		str = m.replaceAll("#_#");
		return str;
	}
}
