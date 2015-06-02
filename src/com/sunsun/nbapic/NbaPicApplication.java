package com.sunsun.nbapic;

import java.io.File;
import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;

import com.sunsun.nbapic.imageutil.SwitchImageLoader;

public class NbaPicApplication extends Application {

	private static NbaPicApplication mInstance;
	private static Context appContext;
	private String userAgent;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		appContext = getApplicationContext();
		SwitchImageLoader.init(this);
	}

	public static NbaPicApplication getInstance() {
		return mInstance;
	}

	public static Context getAppContext() {
		return appContext;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public String getDeviceId() {
		return Secure.getString(NbaPicApplication.getInstance()
				.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * 检查 SDCard 是否装载
	 */
	public boolean isSDCardMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * SD卡剩余空间大小
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public long getSDFreeSize() {
		if (!isSDCardMounted()) {
			return 0;
		}
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		long blockSize = sf.getBlockSize();
		long freeBlocks = sf.getAvailableBlocks();
		return (freeBlocks * blockSize);
	}

	/**
	 * SD卡总容量
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public long getSDAllSize() {
		if (!isSDCardMounted()) {
			return 0;
		}
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		long blockSize = sf.getBlockSize();
		long allBlocks = sf.getBlockCount();
		return (allBlocks * blockSize);
	}

	/**
	 * 检查网络状态̬
	 */
	public boolean checkNetworkState() {
		boolean connected = false;
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			connected = true;
		} else if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			connected = true;
		}
		return connected;
	}

	/**
	 * 网络状态是否是wifi
	 */
	public boolean isWifiNetworkState() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return true;
		}
		return false;
	}

	/**
	 * 获取版本号
	 * 
	 * @return app_version
	 */
	public String getAppVersion() {
		PackageManager pm = this.getPackageManager();
		PackageInfo pi;
		String version = null;
		try {
			pi = pm.getPackageInfo(this.getPackageName(), 0);
			version = pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取版本号
	 * 
	 * @return app_versionCode
	 */
	public int getAppVersionCode() {
		PackageManager pm = this.getPackageManager();
		PackageInfo pi;
		int versionCode = -1;
		try {
			pi = pm.getPackageInfo(this.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public String getMetaValue(String key) {
		if (key == null) {
			return null;
		}
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(
					getPackageName(), PackageManager.GET_META_DATA);
			if (ai != null && ai.metaData != null) {
				return ai.metaData.getString(key);
			}
		} catch (NameNotFoundException e) {
		}
		return null;
	}

	public String getUserAgent() {
		if (TextUtils.isEmpty(userAgent)) {
			userAgent = generateUserAgent();
		}
		return userAgent;
	}

	/**
	 * 生成请求用的user-agent
	 * 
	 * @return
	 */
	public String generateUserAgent() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("duowanapp/").append(getAppVersion()).append(" (");
		stringBuilder.append("linux;android ")
				.append(android.os.Build.VERSION.RELEASE).append(";");
		stringBuilder.append(Locale.getDefault().getLanguage()).append("-")
				.append(Locale.getDefault().getCountry()).append(";");
		stringBuilder.append(android.os.Build.MODEL).append(";)");
		return stringBuilder.toString();
	}

}
