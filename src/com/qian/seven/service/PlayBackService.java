package com.qian.seven.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.qian.seven.FindFileBySuffix;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class PlayBackService extends Service {
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private final IBinder mBinder = new LocalBinder();
	private static List<File> music;
	private int currentIndex = 0;
	private static final int LIST_RECYCLE = 1;
	private static final int RANDOM = 2;
	private static final int SINGLE_RECYCLE = 3;
	private static int CURRENT_RECYCLE = LIST_RECYCLE;

	private List<File> findMusic() {
		File derictory = Environment.getExternalStorageDirectory();
		return new FindFileBySuffix().getFile(derictory, new String[] { "mp3" });
	}

	public class LocalBinder extends Binder {
		public PlayBackService getService() {
			// Return this instance of PlayBackService so clients can call
			// public methods
			return PlayBackService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// init resouce and set listener;
		music = this.findMusic();
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				switch (CURRENT_RECYCLE) {
				case RANDOM: {
					randomPlay();
				}
				case SINGLE_RECYCLE: {
					previous();
				}
				case LIST_RECYCLE: {
					next();
				}
				}

				mediaPlayer.release();
			}
		});
		mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				mediaPlayer.release();
				return false;
			}
		});
		return mBinder;
	}

	public void changeMusic(int position) {
		if (mediaPlayer.isPlaying())
			mediaPlayer.reset();
		try {

			mediaPlayer.setDataSource(music.get(position).getAbsolutePath());
			mediaPlayer.prepare();
		} catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}

	public void next() {
		int next = ++currentIndex % (music.size());
		changeMusic(next);
	}

	public void previous() {
		int previous = --currentIndex % (music.size());// 0 bug?
		changeMusic(previous);
	}

	public void randomPlay() {
		CURRENT_RECYCLE = RANDOM;
		currentIndex = new Random().nextInt(music.size());
		changeMusic(currentIndex);
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void resume() {
		mediaPlayer.start();
	}

	public List<File> music() {
		return PlayBackService.music;
	}

}
