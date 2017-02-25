package com.qian.seven.ui;

import com.qian.seven.R;
import com.qian.seven.service.MusicManager;
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
	private PlayBackService playBackService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		initButtons();
		songName = (TextView) findViewById(R.id.songName);
		String string = getIntent().getStringExtra("songName");
		songName.setText(string);
		Log.i("seven", Thread.currentThread().getName());
		//被intent激活的activity只能通过静态方法拿到绑定生成的Service
		playBackService = MusicManager.getSingle().getPbService();
		playBackService.playMusic(getIntent().getIntExtra("index", 0));
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
			playBackService.pause();
			v.setClickable(false);
			buttons[4].setClickable(true);
			break;
		}
		case R.id.resume: {
			playBackService.resume();
			v.setClickable(false);
			buttons[3].setClickable(true);
			break;
		}
		case R.id.next: {
			playBackService.next();
			break;
		}
		case R.id.previous: {
			playBackService.previous();
			break;
		}
		case R.id.random: {
			playBackService.randomPlay();
			break;
		}
		}
	}

}