package com.qian.seven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	protected static final int FIND_MUSIC_FINISHED = 0;
	private FindFileBySuffix finder = new FindFileBySuffix();
	private ListView list;
	List<File> file;
	List<String> fileName = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 file= finder.getFile(Environment.getExternalStorageDirectory(), new String[]{"mp3"});
		 for(File f: file){
			 fileName.add(f.getName());
		 }
		 
		list  = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, fileName);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new  OnItemClickListener(){
		MediaPlayer mediaPlayer = new MediaPlayer();

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (mediaPlayer.isPlaying()) {
				   mediaPlayer.reset();//重置为初始状态
				}
				try {
					mediaPlayer.setDataSource(file.get(position).getAbsolutePath());
					mediaPlayer.prepare();				
					mediaPlayer.start();//开始或恢复播放
					mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//播出完毕事件
					        @Override public void onCompletion(MediaPlayer arg0) {
						    mediaPlayer.release();
					        }
					});
					mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
					         @Override public boolean onError(MediaPlayer player, int arg1, int arg2) {
						mediaPlayer.release();
						return false;
					         }
					});
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		});
			
	}

	
}
