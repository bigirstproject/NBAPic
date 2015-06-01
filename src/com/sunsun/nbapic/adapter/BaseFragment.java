package com.sunsun.nbapic.adapter;

import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {


	//是否不预加载 true:不预加载     	 false:预加载（默认）
	public boolean isLazyMode;
	
	@Override
	public void onPause() {
		super.onPause();
//		StatService.trackEndPage(getActivity(), getClass().getName()); // 统计页面
	}

	@Override
	public void onResume() {
		super.onResume();
//		StatService.trackBeginPage(getActivity(), getClass().getName()); // 统计页面
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		RefWatcher refWatcher = DwyxApplication.getInstance().getRefWatcher(getActivity());
//	    refWatcher.watch(this);
	}
	
	/**	
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isLazyMode){
        	 if(getUserVisibleHint()) {
                 onVisible();
             } else {
                 onInvisible();
             }
        }
    }
    
    protected void onVisible(){
        lazyLoad();
    }
    
    protected void lazyLoad(){}
    
    protected void onInvisible(){}
    
}
