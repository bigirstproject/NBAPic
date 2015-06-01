package com.sunsun.nbapic.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.duowan.android.dwyx.base.adapter.BaseImageAdapter;
import com.duowan.android.dwyx.model.TopListItem;
import com.duowan.android.dwyx.news.ArticleDetailActivity;
import com.duowan.android.dwyx.news.NewsGalleryActivity;
import com.duowan.android.dwyx.news.NewsGalleryFragment;
import com.duowan.android.dwyx.news.SpecialActivity;
import com.duowan.android.dwyx.news.WebActivity;
import com.duowan.android.dwyx.util.StatisticsUtil;
import com.duowan.webapp.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public abstract class BaseListFragment<E> extends BaseEmptyFragment implements
		OnItemClickListener {

	protected final int PULL_TO_REFRESH = 0;
	protected final int LOAD_MORE = 1;

	protected final int LOADING = 10;
	protected final int LOADED = 11;
	protected PullToRefreshListView mPullRefreshListView;
	protected ListView listView;
	protected View mFooterView;
	protected LinearLayout mFooterLoadedView;
	protected LinearLayout mFooterLoadingView;
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
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		if (needEnptyView()) {
			setContainer(mPullRefreshListView);
		}
		mPullRefreshListView.setMode(setRefreshMode());
		mPullRefreshListView
				.setOnRefreshListener(new CustomOnRefreshListener());
		mPullRefreshListView
				.setOnLastItemVisibleListener(new customOnLastItemVisibleListener());
		listView = mPullRefreshListView.getRefreshableView();
		addBanner();
		mListAdapter = initListAdapter();
		listView.addFooterView(mFooterView);
		listView.setAdapter(mListAdapter);
		if (setListViewListener()) {
			listView.setOnItemClickListener(this);
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
	 * listView刷新模式(上下、上、下、无四种模式)
	 * 
	 * @return
	 */
	protected Mode setRefreshMode() {
		return Mode.PULL_FROM_START;
	}

	/**
	 * listView刷新模式(上下、上、下、无四种模式)
	 * 
	 * @return
	 */
	protected void setRefreshMode(Mode mode) {
		if (mPullRefreshListView != null && mode != null) {
			mPullRefreshListView.setMode(mode);
		}
	}

	/**
	 * 初始化List数据视图的adapter
	 * 
	 * @return
	 */
	protected abstract BaseImageAdapter<E> initListAdapter();

	/**
	 * 数据视图进行了上拉刷新操作
	 */
	protected void loadMore() {

	}

	/**
	 * 是否有加载更多
	 */
	protected boolean hasMore() {
		return false;
	}

	/**
	 * 数据视图进行了下拉刷新操作
	 */
	protected void onRefresh() {

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
		if (listView != null && mFooterView != null) {
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
		TopListItem item = (TopListItem) parent.getAdapter().getItem(position);
		if (item == null) {
			return;
		}
		switch (item.getType()) {
		case 1:
			ArticleDetailActivity.startArticleDetailActivity(getActivity(),
					String.valueOf(item.getId()));
			break;
		case 2:
			WebActivity.startWebActivity(getActivity(), item.getLink(), "");
			break;
		case 3:
			SpecialActivity.startSpecialActivity(getActivity(),
					String.valueOf(item.getId()), item.getTitle());
			break;
		case 4:
			NewsGalleryActivity.startNewsGalleryActivity(getActivity(),
					String.valueOf(item.getId()),
					NewsGalleryFragment.FROM_NEWS_LIST);
			break;
		case 5:
			ArticleDetailActivity.startArticleDetailActivity(getActivity(),
					String.valueOf(item.getId()));
			break;
		default:
			break;
		}
		// // 标记已看文章
		// item.setHasRead(HASREAD);
		// view.setBackgroundColor(getActivity().getResources().getColor(
		// R.color.listview_item_pressed));
		statistics(item);
	}

	private void statistics(TopListItem item) {
		switch (item.getType()) {
		case 1:
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "article_detail");
			break;
		case 2:
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "web_page");
			break;
		case 3:
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "special");
			break;
		case 4:
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "gallery");
			break;
		case 5:
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "video_article_detail");
			break;
		default:
			break;
		}
		StatisticsUtil.statsReportAllData(getActivity(), "news_article",
				"article", "(" + item.getId() + ") " + item.getTitle());
	}

	private class CustomOnRefreshListener implements
			OnRefreshListener2<ListView> {

		@Override
		public void onPullDownToRefresh(
				PullToRefreshBase<ListView> paramPullToRefreshBase) {
			onRefresh();
		}

		@Override
		public void onPullUpToLoadMore(
				PullToRefreshBase<ListView> paramPullToRefreshBase) {
			// loadMore();
		}
	}

	private class customOnLastItemVisibleListener implements
			OnLastItemVisibleListener {

		@Override
		public void onLastItemVisible() {
			onListViewLastItemVisible();
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
