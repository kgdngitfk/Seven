package com.qian.seven.service;

import android.os.IBinder;

public class MusicManager {
	private IBinder mBinder;
	private PlayBackService pbService;
	private static MusicManager m;

	private MusicManager() {

	}

	public static MusicManager getSingle() {
		if (m == null){
			m = new MusicManager();
		}
		return m;
	}

	public void setmBinder(IBinder mBinder) {
		this.mBinder = mBinder;
	}

	public void pause() {
		// TODO Auto-generated method stub
	}

	public PlayBackService getPbService() {
		return pbService;
	}

	public void setPbService(PlayBackService pbService) {
		this.pbService = pbService;
	}

	public IBinder getmBinder() {
		return mBinder;
	}

}
