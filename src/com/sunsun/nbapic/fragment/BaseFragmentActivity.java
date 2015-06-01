package com.duowan.android.dwyx.base;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.duowan.android.dwyx.DwyxApplication;
import com.duowan.android.dwyx.helper.ProgressDialogHelper;
import com.duowan.webapp.R;
import com.tencent.stat.StatService;
import com.yy.hiidostatis.api.HiidoSDK;

/**
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 * 
 * @author yellow
 * @version 2015年3月16日 上午11:40:36
 */
public class BaseFragmentActivity extends FragmentActivity {

	public ProgressDialogHelper progressDialogHelper;

	protected static final HiidoSDK.PageActionReportOption REPORT = HiidoSDK.PageActionReportOption.REPORT_ON_FUTURE_RESUME;

	// protected SwipeBackLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DwyxApplication.getInstance().addActivity(this);
		progressDialogHelper = new ProgressDialogHelper(this);
		progressDialogHelper.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				onProgressDialogCancel();
			}
		});
		// layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
		// R.layout.base_activity, null);
		// if (needSlide()) {
		// layout.attachToActivity(this);
		// }
	}

	protected boolean needSlide() {
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
		HiidoSDK.instance().onResume(DwyxApplication.UID, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
		HiidoSDK.instance().onPause(this, REPORT);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissProgressDialog();
		DwyxApplication.getInstance().removeActivity(this);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	// Press the back button in mobile phone
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	public void finishWithAnimation() {
		finish();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	protected void showSoftInput(final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {
				InputMethodManager inputManager = (InputMethodManager) editText
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(editText, 0);
			}

		}, 500);
	}

	protected void hideSoftInput(View view) {
		InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	@SuppressLint("NewApi")
	public void dismissDialogIfNotDestroyed() {
		boolean isDestroyed = isActivityDestroyed();
		if (!isDestroyed) {
			dismissProgressDialog();
		}
	}

	public void dismissProgressDialog() {
		if (progressDialogHelper != null && progressDialogHelper.isShowing()) {
			progressDialogHelper.dismiss();
		}
	}
	
	public void onProgressDialogCancel(){
		
	}
	
	@SuppressLint("NewApi")
	public boolean isActivityDestroyed(){
		boolean isDestroyed = BaseFragmentActivity.this.isFinishing();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			isDestroyed = BaseFragmentActivity.this.isDestroyed();
		}
		return isDestroyed;
	}
}
