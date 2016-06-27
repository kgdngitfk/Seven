package com.qian.seven.ui;

import java.util.ArrayList;
import java.util.List;

import com.qian.seven.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	List<String> fileName = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// init ListView
		listView = (ListView) findViewById(R.id.listView);
		ContentResolver contentResolver = this.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(uri, new String[] { "_display_name" }, null, null, null);
		while (cursor.moveToNext()) {
			fileName.add(cursor.getString(0));
		}
		if(fileName.isEmpty())
			fileName.add("先去下载一些歌曲吧");
		cursor.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,
				fileName);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				MainActivity.this.play(position);
			}
		});
	}

	/**
	 * start the play activity
	 */
	protected void play(int position) {
		Intent intent = new Intent(this, PlayActivity.class);
		intent.putExtra("index", position);
		intent.putExtra("songName", fileName.get(position));
		startActivity(intent);
	}

}