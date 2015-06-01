package com.sunsun.nbapic.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunsun.nbapic.R;

public class DWToast  extends Toast {

	public DWToast(Context context) {
		super(context);
	}

	public static DWToast makeText(Context context, CharSequence text) {
		return makeText(context, text, LENGTH_SHORT);
	}

	@SuppressLint("InflateParams")
	public static DWToast makeText(Context context, CharSequence text,
			int duration) {
		DWToast result = new DWToast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_view, null);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

	public static DWToast makeText(Context context, int resId) {
		return makeText(context, resId, LENGTH_SHORT);
	}

	public static DWToast makeText(Context context, int resId, int duration)
			throws Resources.NotFoundException {
		return makeText(context, context.getResources().getText(resId),
				duration);
	}

	public void setIcon(int iconResId) {
		if (getView() == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}
		ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
		if (iv == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}
		iv.setImageResource(iconResId);
	}

	@Override
	public void setText(CharSequence s) {
		if (getView() == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}
		TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
		if (tv == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}
		tv.setText(s);
	}

	public void show() {
		super.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				DWToast.this.cancel();
			}
		}, 600);
	};
}

