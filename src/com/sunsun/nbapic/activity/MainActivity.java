package com.sunsun.nbapic.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.sunsun.nbapic.R;
import com.sunsun.nbapic.adapter.ViewPagerAdapter;
import com.sunsun.nbapic.ui.widget.ScrollableViewPager;
import com.sunsun.nbapic.ui.widget.TitleBarView;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
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

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
