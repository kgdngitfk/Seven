package com.qian.seven;

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

public class PlayActivity extends Activity implements OnClickListener{
	LocalBinder lbinder;
	private PlayBackService playService;
	private Button buttons [] = new Button[5];
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

		}
	};
	private Button random;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		Intent intent = new Intent(this, PlayBackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
			initButtons();
		
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
		switch(v.getId()){
		case R.id.stop:{
			playService.pause();
			v.setClickable(false);
			buttons[4].setClickable(true);
		}
		case R.id.resume:{
			playService.resume();
			v.setClickable(false);
			buttons[3].setClickable(true);
		}
		case R.id.next:
			playService.next();
		case R.id.previous:
			playService.previous();;
		case R.id.random:
			playService.randomPlay();;
		}
	}

}