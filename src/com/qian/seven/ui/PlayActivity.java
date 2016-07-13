package com.qian.seven.ui;

import com.qian.seven.R;
import com.qian.seven.service.PlayBackService;
import com.qian.seven.service.PlayBackService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends Activity implements OnClickListener {
	private Button buttons[] = new Button[5];
	private TextView songName;
	private LocalBinder lbinder;
	private PlayBackService playService;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			lbinder = null;
			conn = null;
			Log.i("seven", "unbind service");

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			lbinder = (LocalBinder) service;
			playService = lbinder.getService();
			Log.i("seven", "bind service");

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		initButtons();
		songName = (TextView) findViewById(R.id.songName);
		String string = getIntent().getStringExtra("songName");
		songName.setText(string);

		bindService();
	}

	private void bindService() {
		Intent service = new Intent(this,PlayBackService.class);
		bindService(service, conn, Context.BIND_AUTO_CREATE);
		int intExtra = getIntent().getIntExtra("index", 0);
		playService.playMusic(intExtra);
	}

	private void initButtons() {
		buttons[0] = (Button) findViewById(R.id.random);
		buttons[1] = (Button) findViewById(R.id.previous);
		buttons[2] = (Button) findViewById(R.id.next);
		buttons[3] = (Button) findViewById(R.id.stop);
		buttons[4] = (Button) findViewById(R.id.resume);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.stop: {
			playService.pause();
			v.setClickable(false);
			buttons[4].setClickable(true);
			break;
		}
		case R.id.resume: {
			playService.resume();
			v.setClickable(false);
			buttons[3].setClickable(true);
			break;
		}
		case R.id.next: {
			playService.next();
			break;
		}
		case R.id.previous: {
			playService.previous();
			break;
		}
		case R.id.random: {
			playService.randomPlay();
			break;
		}
		}
	}

}