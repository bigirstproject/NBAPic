package com.sunsun.nbapic.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.duowan.android.dwyx.base.adapter.BaseImageAdapter;
import com.duowan.android.dwyx.model.LunboListItem;
import com.duowan.android.dwyx.news.ArticleDetailActivity;
import com.duowan.android.dwyx.news.NewsGalleryActivity;
import com.duowan.android.dwyx.news.NewsGalleryFragment;
import com.duowan.android.dwyx.news.SpecialActivity;
import com.duowan.android.dwyx.news.WebActivity;
import com.duowan.android.dwyx.util.StatisticsUtil;
import com.duowan.android.dwyx.view.infinitebanner.BaseBannerView.OnBannerItemClickListener;

public abstract class BaseBannerFragment<E, W> extends BaseListFragment<E>
		implements OnBannerItemClickListener {

	protected BaseImageAdapter<W> mBannerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected void addBanner() {
		if (AddBanner()) {
			mBannerAdapter = initBannerAdapter();
			View viewHeader = addListViewHeader();
			if (viewHeader != null) {
				listView.addHeaderView(viewHeader);
			}
		}
	}

	/**
	 * 是否需要添加banner（标示）
	 */
	protected boolean AddBanner() {
		return true;
	}

	/**
	 * banner的标示
	 */
	protected boolean bannerLogo() {
		return true;
	}

	/**
	 * listView头部
	 */
	protected View addListViewHeader() {
		return null;
	};

	/**
	 * 初始化Banner数据视图的adapter
	 * 
	 * @return
	 */
	protected BaseImageAdapter<W> initBannerAdapter() {
		return null;
	};

	/**
	 * banner数据
	 * 
	 * @param refresh
	 * @param data
	 * @param hasMore
	 * @param replace
	 */
	protected void requestBannerFinish(int refresh, List<W> data,
			boolean hasMore, boolean replace) {
		BaseAdapter adapter = mBannerAdapter;
		if (adapter == null) {
			return;
		}
		if (adapter != null) {
			if (refresh == PULL_TO_REFRESH) {
				if (replace && mBannerAdapter.getDataSource() != null) {
					mBannerAdapter.getDataSource().clear();
				}
				mBannerAdapter.setDataSource(data);
			} else {
				mBannerAdapter.addDataSource(data);
			}
		}
	}

	protected class TopPageChangedListener implements OnPageChangeListener {

		public TopPageChangedListener() {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			setBannerIndexs(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	protected void addBannerView(LinearLayout mBannerLogo, int tabs) {
		mBannerLogo.removeAllViews();
		for (int i = 0; i < tabs; i++) {
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			ImageView imageView = new ImageView(getActivity());
			imageView.setPadding(0, 0, 10, 0);
			mBannerLogo.addView(imageView, params);
		}
		setBannerIndexs(0);
	}

	protected void setBannerIndexs(int index) {

	};

	@Override
	public void onBannerItemClick(View view, ListAdapter adapter, int position) {
		LunboListItem item = (LunboListItem) adapter.getItem(position);
		if (item == null) {
			return;
		}
		switch (item.getType()) {
		case 1:
			ArticleDetailActivity.startArticleDetailActivity(getActivity(),
					String.valueOf(item.getNews_id()));
			StatisticsUtil.statsReportAllData(getActivity(),
					"news_list_onclick", "onclick", "article_detail");
			break;
		case 2:
			WebActivity.startWebActivity(getActivity(), item.getLink(),"");
			break;
		case 3:
			SpecialActivity.startSpecialActivity(getActivity(),
					String.valueOf(item.getNews_id()),item.getTitle());
			break;
		case 4:
			NewsGalleryActivity.startNewsGalleryActivity(getActivity(),
					String.valueOf(item.getNews_id()),
					NewsGalleryFragment.FROM_NEWS_LIST);
			break;
		case 5:
			ArticleDetailActivity.startArticleDetailActivity(getActivity(),
					String.valueOf(item.getNews_id()));
			break;
		default:
			break;
		}
		statistics(item, position);
	}

	private void statistics(LunboListItem item, int position) {
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
		StatisticsUtil.statsReportAllData(getActivity(), "news_banner_onclick",
				"onclick", String.valueOf(position));
	}

}
