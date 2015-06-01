package com.sunsun.nbapic.fragment;

import android.support.v4.app.Fragment;

import com.sunsun.nbapic.adapter.BaseImageAdapter;
import com.sunsun.nbapic.base.BaseListFragment;
import com.sunsun.nbapic.entity.ChannelEntity;

public class VideoFragment extends BaseListFragment<ChannelEntity> {

	public static Fragment newInstance() {
		VideoFragment fragment = new VideoFragment();
		return fragment;
	}

	@Override
	protected void requestDataImpl(int refreshStatus, int method, String url,
			Class<?> clazz) {
		// TODO Auto-generated method stub

	}

	@Override
	protected BaseImageAdapter<ChannelEntity> initListAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

}
