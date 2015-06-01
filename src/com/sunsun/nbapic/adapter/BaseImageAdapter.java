package com.sunsun.nbapic.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.sunsun.nbapic.R;
import com.sunsun.nbapic.manager.ImageLoaderManager;

@SuppressLint("ClickableViewAccessibility")
public abstract class BaseImageAdapter<E> extends BaseAdapter {

	protected ImageLoaderManager mImageLoader = ImageLoaderManager
			.getInstance();
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected Resources mResource;
	protected List<E> mDataSource = new ArrayList<E>();

	public BaseImageAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mResource = context.getResources();
	}

	public Resources getResources() {
		return mResource;
	}

	public Context getContext() {
		return mContext;
	}

	public void setDataSource(List<E> dataSource) {
		if (mDataSource != null) {
			mDataSource.addAll(dataSource);
			notifyDataSetChanged();
		}
	}

	public void addDataSource(List<E> data) {
		if (mDataSource != null) {
			mDataSource.addAll(data);
			notifyDataSetChanged();
		}
	}

	public List<E> getDataSource() {
		return mDataSource;
	}

	@Override
	public int getCount() {
		return mDataSource == null ? 0 : mDataSource.size();
	}

	@Override
	public E getItem(int position) {
		if (mDataSource == null || position < 0
				|| position >= mDataSource.size()) {
			return null;
		}
		return mDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	protected void loadImage(String url, ImageLoadingListener listener) {
		mImageLoader.loadImage(url, listener);
	}

	protected void displayImage(String url, ImageView imageView,
			int imageOnFail, int imageOnLoading, int imageForEmptyUri) {
		mImageLoader.displayImage(url, imageView, imageOnFail, imageOnLoading,
				imageForEmptyUri);
	}

	protected void displayImage(String url, ImageView imageView) {
		mImageLoader.displayImage(url, imageView, R.drawable.news_pic_fail_bg,
				R.drawable.news_default_icon, R.drawable.news_default_icon);
	}

	protected void displayImage(String url, ImageView imageView,
			ImageLoadingListener listener) {
		mImageLoader.displayImage(url, imageView, listener);
	}

	protected void displayImage(String url, ImageView view,
			DisplayImageOptions options) {
		displayImage(url, view, options, null);
	}

	public void displayImage(String url, ImageView imageView,
			DisplayImageOptions options, ImageLoadingListener listener) {
		mImageLoader.displayImage(url, imageView, options, listener, null);
	}

	public OnTouchListener onTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				changeLight((ImageView) view, 0);
				break;
			case MotionEvent.ACTION_DOWN:
				changeLight((ImageView) view, -80);
				break;
			case MotionEvent.ACTION_MOVE:
				// changeLight(view, 0);
				break;
			case MotionEvent.ACTION_CANCEL:
				changeLight((ImageView) view, 0);
				break;
			default:
				break;
			}
			return true;
		}

	};

	private void changeLight(ImageView imageview, int brightness) {
		if (imageview == null) {
			return;
		}
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
				brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
	}

}
