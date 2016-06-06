package com.qian.seven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.qian.seven.service.PlayBackService;
import com.qian.seven.service.PlayBackService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView list;
	List<String> fileName = new ArrayList<String>();
	private PlayBackService playService;
	private LocalBinder lbinder;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			lbinder = (LocalBinder) service;
			playService = lbinder.getService();
			List<File> file = playService.music();
			for (File f : file) {
				fileName.add(f.getName());
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//bind and start service
		Intent intent = new Intent(this, PlayBackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		//init ListView
		list = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
				fileName);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				MainActivity.this.play();
				playService.changeMusic(position);
				
			}
		});
	}
	/**
	 * start the play activity
	 */
	protected void play(){
		Intent intent = new Intent(this,PlayActivity.class);
		startActivity(intent);
	}

}
