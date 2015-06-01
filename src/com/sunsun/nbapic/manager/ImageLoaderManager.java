package com.sunsun.nbapic.manager;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.TotalSizeLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sunsun.nbapic.R;
import com.sunsun.nbapic.helper.StoragePathHelper;

public class ImageLoaderManager {
	private static int DISK_IMAGECACHE_SIZE = 50 * 1024 * 1024;

	private static ImageLoaderManager instance;
	private ImageLoader mImageLoader;

	public static ImageLoaderManager getInstance() {
		if (instance == null) {
			synchronized (ImageLoaderManager.class) {
				if (instance == null) {
					instance = new ImageLoaderManager();
				}
			}
		}
		return instance;
	}

	public ImageLoaderManager() {
		mImageLoader = ImageLoader.getInstance();
	}

	/**
	 * 初始化ImageLoader
	 * 
	 * @param context
	 */
	public void initImageLoader(Context context) {

		DisplayImageOptions options = getDisplayImageOptions(R.drawable.video_default_icon);

		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.discCache(new TotalSizeLimitedDiscCache(new File(StoragePathHelper
				.getPicPath()), new Md5FileNameGenerator(),
				DISK_IMAGECACHE_SIZE));
		config.memoryCache(new LruMemoryCache(1024 * 1024 * 5));
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.defaultDisplayImageOptions(options);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	/**
	 * Build a DisplayImageOptions
	 * 
	 * @param imageOnFail
	 * @param imageOnLoading
	 * @param imageForEmptyUri
	 * @return
	 */
	private DisplayImageOptions getDisplayImageOptions(int imageDefault) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnFail(imageDefault)
				.showImageOnLoading(imageDefault)
				.showImageForEmptyUri(imageDefault)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();
		return options;
	}
	
	private DisplayImageOptions getDisplayImageOptions(int imageOnFail,
			int imageOnLoading, int imageForEmptyUri) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnFail(imageOnFail)
				.showImageOnLoading(imageOnLoading)
				.showImageForEmptyUri(imageForEmptyUri)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.build();
		return options;
	}

	public void displayImage(String uri, ImageView imageView) {
		displayImage(uri, imageView, null, null, null);
	}

	public void displayImage(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		displayImage(uri, imageView, null, listener, null);
	}
	
	public void displayImage(String uri, ImageView imageView, int imageDefault) {
		displayImage(uri, imageView, imageDefault, imageDefault, imageDefault);
	}

	public void displayImage(String uri, ImageView imageView, int imageOnFail,
			int imageOnLoading, int imageForEmptyUri) {
		displayImage(uri, imageView, null, imageOnFail, imageOnLoading,
				imageForEmptyUri);
	}

	public void displayImage(String uri, ImageView imageView,
			ImageLoadingListener listener, int imageOnFail, int imageOnLoading,
			int imageForEmptyUri) {
		DisplayImageOptions options = getDisplayImageOptions(imageOnFail,
				imageOnLoading, imageForEmptyUri);
		displayImage(uri, imageView, options, listener, null);
	}

	public void displayImage(String uri, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener,
			ImageLoadingProgressListener progressListener) {
		if (listener == null) {
			listener = new AnimateFirstDisplayListener();
		}
		mImageLoader.displayImage(uri, imageView, options, listener,
				progressListener);
	}

	public void loadImage(String url, ImageLoadingListener listener) {
		mImageLoader.loadImage(url, listener);
	}

	/**
	 * 加载图片默认的ImageLoadingListener
	 * 
	 * @author yellow
	 *
	 */
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri
						.trim());
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri.trim());
				}
			}
		}
	}

	/**
	 * 暂停加载
	 */
	public void pause() {
		mImageLoader.pause();
	}

	/**
	 * 唤醒加载器
	 */
	public void resume() {
		mImageLoader.resume();
	}

}
