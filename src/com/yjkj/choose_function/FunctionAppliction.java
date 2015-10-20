package com.yjkj.choose_function;

import com.activeandroid.ActiveAndroid;

import android.app.Application;

public class FunctionAppliction extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//初始化ORM框架
		ActiveAndroid.initialize(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
