package com.sunsun.nbapic.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollBannerView extends BaseBannerView {
	public ScrollBannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollBannerView(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		checkUserTouch(ev);
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		checkUserTouch(ev);
		return super.onTouchEvent(ev);
	}

	private void checkUserTouch(MotionEvent ev) {
		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			pauseAnimateInternal();
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			animateInternal();
			break;
		}
		}
	}

	// 视图切换的间隔
	private static final int SCROLL_DELAY = 4000;

	private int mDuration = SCROLL_DELAY;

	public void setDuration(int duration) {
		mDuration = duration;
	}

	/**
	 * 是否是用户调用
	 */
	public boolean mScrollEnabled;

	public void startScroll() {
		mScrollEnabled = true;
		animateInternal();
	}

	public void pauseScroll() {
		mScrollEnabled = false;
		pauseAnimateInternal();
	}

	private void pauseAnimateInternal() {
		removeCallbacks(mAnimateNextRunnable);
	}

	private void animateInternal() {
		if (mScrollEnabled) {
			removeCallbacks(mAnimateNextRunnable);
			postDelayed(mAnimateNextRunnable, mDuration);
		}
	}

	public interface OnPageScrollListener {
		public void onScroll(int curPage, int pageCount);
	}

	private OnPageScrollListener mListener;

	public void setOnPageScrollListener(OnPageScrollListener listener) {
		mListener = listener;
	}

	private Runnable mAnimateNextRunnable = new Runnable() {
		public void run() {

			if (!mScrollEnabled) {
				return;
			}
			int next = getCurrentItem() + 1;
			PrimaryItemPagerAdapter adapter = (PrimaryItemPagerAdapter) getAdapter();
			int pageCount = 0;
			if (adapter != null) {
				pageCount = adapter.getCount();
			}
			if (next < pageCount) {
				setCurrentItem(next);
				animateInternal();
			}

			if (mListener != null) {
				mListener.onScroll(next, pageCount);
			}

		};
	};
}
