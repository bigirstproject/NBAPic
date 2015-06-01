package com.sunsun.nbapic.helper;

import java.io.File;

import com.sunsun.nbapic.NbaPicApplication;
import com.sunsun.nbapic.util.FileUtil;

public class StoragePathHelper {
	
	public static final String[] INTERNAL_STORAGE_PATHS = new String[] {
			"/mnt/", "/emmc/" };
	public static final String PATH = "data/";
	public static final String LOG_PATH = PATH + ".log/";
	public static final String CACHE_PATH = PATH + ".cache/";
	public static final String VIDEO_PATH = ".video/";
	public static final String PIC_PATH = PATH + "pic/.nomedia/";

	private static String fileSystemDir;
	private static String internalStoragePath;

	private static String parentPath;
	private static String logPath;
	private static String picPath;
	private static String cachePath;
	private static String videoPath;
	private static String fileSystemCacheDir;

	private static void initInternalStoragePath() {
		if (NbaPicApplication.getInstance().isSDCardMounted()) {
			return;
		}
		for (String path : INTERNAL_STORAGE_PATHS) {
			if (FileUtil.isFileCanReadAndWrite(path)) {
				internalStoragePath = path;
				return;
			} else {
				File f = new File(path);
				if (f.isDirectory()) {
					for (File file : f.listFiles()) {
						if (file != null
								&& file.isDirectory()
								&& !file.isHidden()
								&& FileUtil.isFileCanReadAndWrite(file
										.getPath())) {
							internalStoragePath = file.getPath();
							if (!internalStoragePath.endsWith(File.separator)) {
								internalStoragePath += File.separator;
							}
							return;
						}
					}
				}
			}
		}
	}

	public static void initStoragePath() {
		initInternalStoragePath();
		fileSystemCacheDir = NbaPicApplication.getAppContext().getCacheDir()
				.getAbsolutePath().concat(File.separator);
		fileSystemDir = NbaPicApplication.getAppContext().getFilesDir()
				.getAbsolutePath().concat(File.separator);
		parentPath = fileSystemDir + PATH;
		logPath = fileSystemDir + LOG_PATH;
		cachePath = fileSystemDir + CACHE_PATH;
		videoPath = fileSystemDir + VIDEO_PATH;
		picPath = fileSystemDir + PIC_PATH;

		String storagePath = null;
		if (NbaPicApplication.getInstance().isSDCardMounted()) {
			// storagePath = Environment.getExternalStorageDirectory()
			// .getAbsolutePath();
			storagePath = NbaPicApplication.getAppContext()
					.getExternalFilesDir(null).getAbsolutePath();
		} else if (null != internalStoragePath) {
			storagePath = internalStoragePath;
		}
		if (null != storagePath) {
			parentPath = storagePath.concat(File.separator) + PATH;
			logPath = storagePath.concat(File.separator) + LOG_PATH;
			cachePath = storagePath.concat(File.separator) + CACHE_PATH;
			videoPath = storagePath.concat(File.separator) + VIDEO_PATH;
			picPath = storagePath.concat(File.separator) + PIC_PATH;
		}

		File pathDir = new File(parentPath);
		if (!pathDir.exists()) {
			pathDir.mkdirs();
		}

		File logDir = new File(logPath);
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		File cacheDir = new File(cachePath);
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		File videoDir = new File(videoPath);
		if (!videoDir.exists()) {
			videoDir.mkdirs();
		}
		File picDir = new File(picPath);
		if (!picDir.exists()) {
			picDir.mkdirs();
		}
	}

	public static String getParentPath() {
		return parentPath;
	}

	public static String getPicPath() {
		return picPath;
	}

	public static String getLogPath() {
		return logPath;
	}

	public static String getCachePath() {
		return cachePath;
	}

	public static String getVideoPath() {
		return videoPath;
	}

	public static String getFileSystemDir() {
		return fileSystemDir;
	}

	public static void setFileSystemDir(String fileSystemDir) {
		StoragePathHelper.fileSystemDir = fileSystemDir;
	}

	public static String getFileSystemCacheDir() {
		return fileSystemCacheDir;
	}

	public static void setFileSystemCacheDir(String fileSystemCacheDir) {
		StoragePathHelper.fileSystemCacheDir = fileSystemCacheDir;
	}
}
