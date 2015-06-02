package com.sunsun.nbapic.cache;

import java.io.File;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.sunsun.nbapic.ICallback;
import com.sunsun.nbapic.logcat.AppLogger;
import com.sunsun.nbapic.util.FileUtil;

/**
 * 数据缓存类
 * 
 * @author sunsun
 * @date 2015年6月2日 下午12:17:59
 */
public abstract class BaseDataSource<T> {
	private static Handler mHandler = new Handler(Looper.getMainLooper());
	private static Gson GSON = new Gson();

	protected abstract Class<T> getClazz();

	protected abstract File getCacheFile();
	
	/**
	 * 加载缓存数据
	 */
	public T load() {
		T data = null;
		try {
			String json = loadStringFromDisk();
			data = GSON.fromJson(json, getClazz());
		} catch (Exception e) {
			AppLogger.error(e);
		}
		return data;
	}
	
	public void asyncLoad(final ICallback<T> callback) {
		if (callback == null) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				final T data = load();
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						callback.callback(data);
					}
				});
			}
		}).start();
	}
	
	/**
	 * 保存缓存数据
	 */
	private boolean save(T data) {
		try {
			String json = GSON.toJson(data, getClazz());
			return saveStringToDisk(json);
		} catch (Exception e) {
			AppLogger.error(e);
			return false;
		}
	}
	
	public void asyncSave(final T data, final ICallback<Boolean> callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final boolean result = save(data);
				if (callback != null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							callback.callback(result);
						}
					});
				}
			}
		}).start();
	}

	/**
	 * 加载缓存数据
	 * 
	 * @return
	 */
	private synchronized String loadStringFromDisk() {
		File file = getCacheFile();
		if (file == null) {
			return null;
		}
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			String jsonString = FileUtil.readAsString(file.getAbsolutePath());
			if (!jsonString.equals("")) {
				return jsonString;
			}
		} catch (Exception e) {
			AppLogger.error(e);
		}

		return null;
	}
	

	/**
	 * 保存缓存数据
	 * 
	 * @param content
	 */
	private synchronized boolean saveStringToDisk(String content) {
		File file = getCacheFile();
		if (file == null) {
			return false;
		}
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			FileUtil.writeString(file.getAbsolutePath(), content);
			return true;
		} catch (Exception e) {
			AppLogger.error(e);
			return false;
		}
	}

}
