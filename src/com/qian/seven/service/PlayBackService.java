package com.qian.seven.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qian.seven.utils.FindFileBySuffix;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;

public class PlayBackService extends Service {
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private  IBinder mBinder = new LocalBinder();
	private int currentIndex = 0;
	private static final int LIST_RECYCLE = 1;
	private static final int RANDOM = 2;
	private static final int SINGLE_RECYCLE = 3;
	private static int CURRENT_RECYCLE = LIST_RECYCLE;
	private List<String> musics = new ArrayList<>();

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
		musics = this.contentResolver();
		this.playMusic(intent.getIntExtra("index", 0));
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

	public void playMusic(int position) {
		currentIndex = position;
		if (mediaPlayer.isPlaying())
			mediaPlayer.reset();
		try {

			mediaPlayer.setDataSource(musics.get(position));
			mediaPlayer.prepare();
		} catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}

	// a better solution is use recycle queue
	public void next() {
		++currentIndex;
		int next = currentIndex % (musics.size());
		playMusic(next);
	}

	public void previous() {
		int previous = 0;
		if (currentIndex == 0) {
			previous = musics.size() - 1;// 计算上一曲的正确索引位置
			currentIndex = previous;// 更新当前索引位置

		} else {
			--currentIndex;// 更新当前索引
			previous = currentIndex % (musics.size());// 计算上一曲的索引
		}
		playMusic(previous);
	}

	public void randomPlay() {
		CURRENT_RECYCLE = RANDOM;
		currentIndex = new Random().nextInt(musics.size());
		playMusic(currentIndex);
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void resume() {
		mediaPlayer.start();
	}

	public List<String> music() {
		return this.musics;
	}

	// 通过安卓系统的MediaStore获取音乐文件
	public List<String> contentResolver() {
		ArrayList<String> list = new ArrayList<>();
		ContentResolver contentResolver = this.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(uri, new String[] { "_data", }, null, null, null);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		cursor.close();
		return list;
	}

	// 调用自己的api获得所有音乐文件的路径
	@SuppressWarnings("unused")
	private List<File> findMusic() {
		File derictory = Environment.getExternalStorageDirectory();
		return new FindFileBySuffix().getFile(derictory, new String[] { "mp3" });
	}

}
