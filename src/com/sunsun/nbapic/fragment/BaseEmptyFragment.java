package com.sunsun.nbapic.fragment;

import com.sunsun.nbapic.R;

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


public class BaseEmptyFragment extends BaseFragment {

	protected static final int VIEW_TYPE_NO_DATA = 1;
	protected static final int VIEW_TYPE_NO_NOTWORK = 2;
	protected static final int VIEW_TYPE_DATA = 3;
	protected static final int VIEW_TYPE_LOADING = 4;
	protected static final int VIEW_TYPE_RECOMMEND = 5;

	protected LayoutInflater mInflater;
	private View mDataLayout;
	private View mEmptyLayout;
	private View mNoNetworkView;
	private View mEmptyView;
	private Button mReLoadButton;
	private View mProgressBar;
	private View mProgressBarInner;
	private TextView mLoadTextingView;
	private Animation mLoadingAnimation = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mInflater = inflater;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
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
			mNoNetworkView = mEmptyLayout.findViewById(R.id.load_fail_view);
			mEmptyView = mEmptyLayout.findViewById(R.id.content_empty_view);
			mReLoadButton = (Button) mEmptyLayout.findViewById(R.id.btn_reload);
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
		if (mDataLayout == null) {
			return;
		}
		switch (viewType) {
		case VIEW_TYPE_DATA: {
			mDataLayout.setVisibility(View.VISIBLE);
			mEmptyView.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.GONE);
			mNoNetworkView.setVisibility(View.GONE);
			hidenLoading();
			break;
		}
		case VIEW_TYPE_NO_DATA: {
			mDataLayout.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.VISIBLE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			mNoNetworkView.setVisibility(View.GONE);
			hidenLoading();
			break;
		}
		case VIEW_TYPE_LOADING: {
			mDataLayout.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			mNoNetworkView.setVisibility(View.GONE);
			showLoading();
			break;
		}
		case VIEW_TYPE_RECOMMEND: {
			mDataLayout.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			mNoNetworkView.setVisibility(View.GONE);
			hidenLoading();
			break;
		}
		case VIEW_TYPE_NO_NOTWORK: {
			mDataLayout.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			mNoNetworkView.setVisibility(View.VISIBLE);
			hidenLoading();
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

	protected void onLoadingTextColor(int color) {
		mLoadTextingView.setTextColor(color);
	}

	protected void setReLoadButtonText(String text) {
		if (mReLoadButton != null) {
			mReLoadButton.setText(text);
		}
	}

	protected void setProgressBarInnerBg(int resource) {
		mProgressBarInner.setBackgroundResource(resource);
	}

	/**
	 * 显示“正在加载”视图
	 */
	private void showLoading() {
		if (mLoadingAnimation == null) {
			mLoadingAnimation = AnimationUtils.loadAnimation(getActivity(),
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

	/**
	 * 显示“正在加载”视图
	 */
	protected void showViewLoading(View view) {
		if (mLoadingAnimation == null) {
			mLoadingAnimation = AnimationUtils.loadAnimation(getActivity(),
					R.anim.article_detail_loading);
			mLoadingAnimation.setInterpolator(new LinearInterpolator());
			mLoadingAnimation.setFillAfter(true);// 动画停止时保持在该动画结束时的状态
		}
		view.setVisibility(View.VISIBLE);
		if (view != null) {
			view.startAnimation(mLoadingAnimation);
		}
	}

	/**
	 * 隐藏“正在加载”视图
	 */
	protected void hidenViewLoading(View view) {
		if (view != null) {
			view.clearAnimation();
		}
		view.setVisibility(View.GONE);
	}

}
