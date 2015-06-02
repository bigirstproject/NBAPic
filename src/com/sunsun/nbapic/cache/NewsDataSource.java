package com.sunsun.nbapic.cache;

import java.io.File;

import com.sunsun.nbapic.entity.NewsEntity;
import com.sunsun.nbapic.helper.StoragePathHelper;

public class NewsDataSource extends BaseDataSource<NewsEntity> {

	private String cachePath = "news_data";

	private static NewsDataSource instance;

	public NewsDataSource() {
		super();
	}

	public static NewsDataSource getInstance() {
		if (instance == null) {
			synchronized (NewsDataSource.class) {
				if (instance == null) {
					instance = new NewsDataSource();
				}
			}
		}
		return instance;
	}

	@Override
	protected Class<NewsEntity> getClazz() {
		return NewsEntity.class;
	}

	@Override
	protected File getCacheFile() {
		return new File(StoragePathHelper.getFileSystemCacheDir() + cachePath);
	}

}
