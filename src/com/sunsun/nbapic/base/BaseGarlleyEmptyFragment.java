package com.sunsun.nbapic.base;

import com.sunsun.nbapic.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseGarlleyEmptyFragment extends BaseFragment {

	protected static final int VIEW_TYPE_NO_NOTWORK = 1;
	protected static final int VIEW_TYPE_LOADING = 2;
	protected static final int VIEW_TYPE_SUCESSED = 3;

	protected LayoutInflater mInflater;
	private View mDataLayout;
	private View mEmptyLayout;
	private ImageView mImageView;
	private TextView mTextView;
	private Animation mLoadingAnimation = null;

	private int viewType;

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
			mEmptyLayout = mInflater.inflate(R.layout.gallery_reload, parent,
					false);
			mImageView = (ImageView) mEmptyLayout.findViewById(R.id.reload_img);
			mTextView = (TextView) mEmptyLayout.findViewById(R.id.reload_text);
			mImageView.setOnClickListener(reloadClickListener);
			mTextView.setOnClickListener(reloadClickListener);
		} else {
			if (parent != null) {
				parent.removeView(mEmptyLayout);
			}
		}
		parent.addView(mEmptyLayout);
	}

	private OnClickListener reloadClickListener = new OnClickListener() {

		@Override
		public void onClick(View paramView) {
			if (viewType == VIEW_TYPE_NO_NOTWORK) {
				onEmptyViewClicked();
			}
		}
	};

	protected void showView(int viewType) {
		if (getActivity() != null) {
			this.viewType = viewType;
			switch (viewType) {
			case VIEW_TYPE_LOADING: {
				mDataLayout.setVisibility(View.GONE);
				mEmptyLayout.setVisibility(View.VISIBLE);
				mTextView.setText(getActivity()
						.getString(R.string.gallery_load));
				showLoading();
				break;
			}
			case VIEW_TYPE_SUCESSED: {
				mDataLayout.setVisibility(View.VISIBLE);
				mEmptyLayout.setVisibility(View.GONE);
				hidenLoading();
				break;
			}
			case VIEW_TYPE_NO_NOTWORK: {
				mDataLayout.setVisibility(View.GONE);
				mEmptyLayout.setVisibility(View.VISIBLE);
				mTextView.setText(getActivity().getString(
						R.string.gallery_reload));
				hidenLoading();
				break;
			}
			}
		}

	}

	/**
	 * 为空，点击再一次加载
	 */
	protected void onEmptyViewClicked() {

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
		if (mImageView != null) {
			mImageView.setVisibility(View.VISIBLE);
			mImageView.startAnimation(mLoadingAnimation);
		}
	}

	/**
	 * 隐藏“正在加载”视图
	 */
	protected void hidenLoading() {
		if (mImageView != null) {
			mImageView.clearAnimation();
		}
	}

}
