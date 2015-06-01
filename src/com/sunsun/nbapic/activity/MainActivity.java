package com.sunsun.nbapic.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.sunsun.nbapic.R;
import com.sunsun.nbapic.adapter.ViewPagerAdapter;
import com.sunsun.nbapic.fragment.ChannelFragment;
import com.sunsun.nbapic.fragment.GalleryFragment;
import com.sunsun.nbapic.fragment.NewsFragment;
import com.sunsun.nbapic.fragment.VideoFragment;
import com.sunsun.nbapic.ui.widget.DWToast;
import com.sunsun.nbapic.ui.widget.ScrollableViewPager;
import com.sunsun.nbapic.ui.widget.TitleBarView;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {
	private String[] mTabs;

	private long exitTime = 0;
	private TitleBarView mTitleBarView;

	private LinearLayout tabsLinearLayout;

	private ScrollableViewPager mCustomViewPager;
	private ViewPagerAdapter mViewPagerAdapter;

	private Fragment mChannelFragment;
	private Fragment mNewsFragment;
	private Fragment mGalleryFragment;
	private Fragment mVideoFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTabs = getResources().getStringArray(R.array.tab_titles);
		initView();
	}

	private void initView() {
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar_view);
		mTitleBarView.setRightButtonDrawableLeft(true);
		mTitleBarView.setLeftButtonDrawable(R.drawable.user_center_icon);

		tabsLinearLayout = (LinearLayout) findViewById(R.id.ll_tabs);
		findViewById(R.id.tab_channel).setOnClickListener(this);
		findViewById(R.id.tab_news).setOnClickListener(this);
		findViewById(R.id.tab_gallery).setOnClickListener(this);
		findViewById(R.id.tab_video).setOnClickListener(this);

		mCustomViewPager = (ScrollableViewPager) findViewById(R.id.customviewpager);
		mCustomViewPager.setOffscreenPageLimit(1);// 设置viewPager不进行预加载
		mCustomViewPager.setScanScroll(false);// 设置无法滑动
		ArrayList<Fragment> fragments = initFragments();
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				fragments);
		mCustomViewPager.setAdapter(mViewPagerAdapter);
		mCustomViewPager.setOnPageChangeListener(this);
		changeTabStatus(0);
	}

	protected ArrayList<Fragment> initFragments() {
		mChannelFragment = ChannelFragment.newInstance();
		mNewsFragment = NewsFragment.newInstance();
		mGalleryFragment = GalleryFragment.newInstance();
		mVideoFragment = VideoFragment.newInstance();
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(mChannelFragment);
		fragments.add(mNewsFragment);
		fragments.add(mGalleryFragment);
		fragments.add(mVideoFragment);
		return fragments;
	}

	/**
	 * 切换底部tab的状态
	 * 
	 * @param index
	 */
	private void changeTabStatus(int index) {
		int childCount = tabsLinearLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			if (index == i) {
				tabsLinearLayout.getChildAt(i).setSelected(true);
			} else {
				tabsLinearLayout.getChildAt(i).setSelected(false);
			}
		}
		mTitleBarView.setTitle(mTabs[index]);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_channel:
			mCustomViewPager.setCurrentItem(0);
			break;
		case R.id.tab_news:
			mCustomViewPager.setCurrentItem(1);
			break;
		case R.id.tab_gallery:
			mCustomViewPager.setCurrentItem(2);
			break;
		case R.id.tab_video:
			mCustomViewPager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int index) {
		changeTabStatus(index);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				DWToast.makeText(getApplicationContext(), "再按一次退出程序").show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
