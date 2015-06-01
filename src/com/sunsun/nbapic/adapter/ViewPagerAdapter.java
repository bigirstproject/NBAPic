package com.sunsun.nbapic.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}
	
	public void updateFragments(List<Fragment> fragments) {
		this.fragments = fragments;
		notifyDataSetChanged();
	}

	public void repace(int index, Fragment fragment, Fragment newfragment) {
		this.fragments.remove(index);
		this.fragments.add(index, newfragment);
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		if (fragments.contains(object)) {
			return fragments.indexOf(object);
		}
		return POSITION_NONE;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	/**
	 * 重写此方法，不做实现，可防止子fragment被回收
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}
	

	
}
