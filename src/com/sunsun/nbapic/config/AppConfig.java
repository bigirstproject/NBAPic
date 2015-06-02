package com.sunsun.nbapic.config;

import com.sunsun.nbapic.NbaPicApplication;

public class AppConfig {

	public static final boolean HANDLE_CRASH = false;
	public static final boolean DEBUG = false;
	
	public static final float DENSITY = NbaPicApplication.getInstance()
			.getResources().getDisplayMetrics().density;
	public static final float SCALESITY = NbaPicApplication.getInstance()
			.getResources().getDisplayMetrics().scaledDensity;
	public static final int widthPx = NbaPicApplication.getInstance()
			.getResources().getDisplayMetrics().widthPixels;
	public static final int heightPx = NbaPicApplication.getInstance()
			.getResources().getDisplayMetrics().heightPixels;

}
