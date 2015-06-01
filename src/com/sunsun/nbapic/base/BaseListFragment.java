package com.sunsun.nbapic.base;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.sunsun.nbapic.R;
import com.sunsun.nbapic.adapter.BaseImageAdapter;
import com.sunsun.nbapic.xlistview.XListView;
import com.sunsun.nbapic.xlistview.XListView.IXListViewListener;

public abstract class BaseListFragment<E> extends BaseEmptyFragment implements
		OnItemClickListener {

	protected final int PULL_TO_REFRESH = 0;
	protected final int LOAD_MORE = 1;

	protected final int LOADING = 10;
	protected final int LOADED = 11;
	protected XListView mXListView;
	protected View mFooterView;
	protected LinearLayout mFooterLoadingView;
	protected LinearLayout mFooterLoadedView;
	protected BaseImageAdapter<E> mListAdapter;
	protected boolean mHasRefresh;

	public int refreshStatus;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.news_list_fragment, container,
				false);
		mFooterView = inflater.inflate(R.layout.common_footer_view, null);
		mFooterLoadedView = (LinearLayout) mFooterView
				.findViewById(R.id.footer_loaded);
		mFooterLoadingView = (LinearLayout) mFooterView
				.findViewById(R.id.footer_loading);
		mFooterLoadedView.setVisibility(View.GONE);
		mFooterLoadingView.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mXListView = (XListView) view.findViewById(R.id.refresh_listview);
		if (needEnptyView()) {
			setContainer(mXListView);
		}
		mXListView.addFooterView(mFooterView);
		mXListView.setXListViewListener(new CustomIXListViewListener());
		addBanner();
		mListAdapter = initListAdapter();
		mXListView.setAdapter(mListAdapter);
		if (setListViewListener()) {
			mXListView.setOnItemClickListener(this);
		}
	}

	/**
	 * add banner
	 */
	protected void addBanner() {

	}

	/**
	 * 数据请求
	 */
	protected abstract void requestDataImpl(final int refreshStatus,
			int method, String url, Class<?> clazz);

	/**
	 * 是否需要加载网络或无数据（显示）
	 * 
	 * @return
	 */
	protected boolean needEnptyView() {
		return true;
	}

	/**
	 * 初始化List数据视图的adapter
	 * 
	 * @return
	 */
	protected abstract BaseImageAdapter<E> initListAdapter();

	/**
	 * 是否有加载更多
	 */
	protected boolean hasMore() {
		return false;
	}

	/**
	 * 数据视图进行了上拉刷新操作
	 */
	protected void loadMore() {

	}

	/**
	 * 数据视图进行了下拉刷新操作
	 */
	protected void onListRefresh() {

	}

	/**
	 * 设置listView item监听
	 */
	protected boolean setListViewListener() {
		return true;
	};

	/**
	 * 自定义listView item监听
	 */
	protected boolean customListViewListener() {
		return false;
	};

	/**
	 * 自定义listView 定义项监听
	 */
	protected void onCustomItemClick(AdapterView<?> parent, View view,
			int position, long id) {
	}

	/**
	 * 加载到最后一项
	 */
	protected void onListViewLastItemVisible() {
		if (hasMore()) {
			loadMore();
		}
	}

	/**
	 * add footer view
	 */
	protected void addFooterView(int mode) {
		if (mXListView != null && mFooterView != null) {
			if (mode == LOADING) {
				mFooterLoadingView.setVisibility(View.VISIBLE);
				mFooterLoadedView.setVisibility(View.GONE);
				showViewLoading(mFooterLoadingView
						.findViewById(R.id.footer_loading_img));
			} else if (mode == LOADED) {
				mFooterLoadingView.setVisibility(View.GONE);
				mFooterLoadedView.setVisibility(View.VISIBLE);
				hidenViewLoading(mFooterLoadingView
						.findViewById(R.id.footer_loading_img));
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (customListViewListener()) {
			onCustomItemClick(parent, view, position, id);
			return;
		}

	}

	class CustomIXListViewListener implements IXListViewListener {

		@Override
		public void onRefresh() {
			onListRefresh();
		}

		@Override
		public void onLoadMore() {
			loadMore();
		}
	}

	/**
	 * 加载列表页数据
	 * 
	 * @param refresh
	 * @param data
	 * @param hasMore
	 * @param replace
	 */
	protected void requestListFinish(int refresh, List<E> data,
			boolean hasMore, boolean replace, boolean cache) {
		if (mListAdapter == null) {
			return;
		}
		if (mListAdapter != null) {
			if (refresh == PULL_TO_REFRESH) {
				if (replace && mListAdapter.getDataSource() != null) {
					mListAdapter.getDataSource().clear();
				}
				mListAdapter.setDataSource(data);
			} else {
				mListAdapter.addDataSource(data);
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
