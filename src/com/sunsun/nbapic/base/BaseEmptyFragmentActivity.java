package com.sunsun.nbapic.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.sunsun.nbapic.R;

public class BaseEmptyFragmentActivity extends BaseFragmentActivity {
	
	protected static final int VIEW_TYPE_EMPTY = 1;
	protected static final int VIEW_TYPE_DATA = 2;
	protected static final int VIEW_TYPE_LOADING = 3;

	protected LayoutInflater mInflater;
	private View mDataLayout;
	private View mEmptyLayout;
	private View mEmptyView;
	private Button mReLoadButton;
	private View mProgressBar;
	private View mProgressBarInner;
	private TextView mLoadTextingView;
	private Animation mLoadingAnimation = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mInflater = getLayoutInflater();
	}

	/**
	 * 设置容器，并添加empty view
	 * 
	 * @param container
	 */
	protected void setContainer(View view) {
		if (view == null) {
			return;
		}
		this.mDataLayout = view;
		ViewGroup parent = (ViewGroup) view.getParent();
		if (mEmptyLayout == null) {
			mEmptyLayout = mInflater.inflate(R.layout.load_global_view, parent,
					false);
			mEmptyView = mEmptyLayout.findViewById(R.id.load_fail_view);
			mReLoadButton = (Button) mEmptyLayout
					.findViewById(R.id.btn_reload);
			mReLoadButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onEmptyViewClicked();
				}
			});
			mProgressBar = mEmptyLayout.findViewById(R.id.loading_view);
			mProgressBarInner = mEmptyLayout
					.findViewById(R.id.reload_progressbar_inner);
			mLoadTextingView = (TextView) mEmptyLayout
					.findViewById(R.id.loading_text);
		} else {
			if (parent != null) {
				parent.removeView(mEmptyLayout);
			}
		}
		parent.addView(mEmptyLayout);
	}

	protected void showView(int viewType) {
		switch (viewType) {
		case VIEW_TYPE_DATA: {
			mDataLayout.setVisibility(View.VISIBLE);
			mEmptyView.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.GONE);
			hidenLoading();
			break;
		}
		case VIEW_TYPE_EMPTY: {
			mDataLayout.setVisibility(View.INVISIBLE);
			mEmptyView.setVisibility(View.VISIBLE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			hidenLoading();
			break;
		}
		case VIEW_TYPE_LOADING: {
			mDataLayout.setVisibility(View.INVISIBLE);
			mEmptyView.setVisibility(View.INVISIBLE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			showLoading();
			break;
		}
		}
	}

	/**
	 * 为空，点击再一次加载
	 */
	protected void onEmptyViewClicked() {

	}

	protected void onLoadingText(String text) {
		if (mLoadTextingView != null) {
			mLoadTextingView.setText(text);
		}
	}

	protected void setReLoadButtonText(String text) {
		if (mReLoadButton != null) {
			mReLoadButton.setText(text);
		}
	}

	/**
	 * 显示“正在加载”视图
	 */
	private void showLoading() {
		if (mLoadingAnimation == null) {
			mLoadingAnimation = AnimationUtils.loadAnimation(this,
					R.anim.article_detail_loading);
			mLoadingAnimation.setInterpolator(new LinearInterpolator());
			mLoadingAnimation.setFillAfter(true);// 动画停止时保持在该动画结束时的状态
		}
		mProgressBar.setVisibility(View.VISIBLE);
		if (mProgressBarInner != null) {
			mProgressBarInner.startAnimation(mLoadingAnimation);
		}
	}

	/**
	 * 隐藏“正在加载”视图
	 */
	protected void hidenLoading() {
		if (mProgressBarInner != null) {
			mProgressBarInner.clearAnimation();
		}
		mProgressBar.setVisibility(View.GONE);
	}

}
