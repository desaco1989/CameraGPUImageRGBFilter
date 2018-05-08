package com.desaco.desacocarrybeauty.camera_filter2.actiivity;

import com.desaco.desacocarrybeauty.R;
import com.desaco.desacocarrybeauty.R.id;
import com.desaco.desacocarrybeauty.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * com.desaco.desacocarrybeauty.camera_filter2.TestFilterMainActivity
 * @author desaco
 *
 */
public class TestFilterMainActivity extends Activity {
	ListView mListView;
	public static String[] mListStr = { "1,冰冻", "2,熔铸", "3,连环画", "4,柔化美白", "5,照亮边缘", "6,羽化效果", "7,缩放模糊",
			"8,Lomo","9,胶片" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_filter);
		findView();
	}

	private void findView() {
		mListView = (ListView) findViewById(R.id.listview);
		// SimpleAdapter = new SimpleAdapter(context, data, resource, from, to)
		mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListStr));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent intent = new Intent(TestFilterMainActivity.this, ImageFilterActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}
}