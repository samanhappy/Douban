package com.saman.douban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class GvActivity extends Activity {
	private String texts[] = null;
	private int images[] = null;
	private BookAdapter bookAdapter = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		

		images = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher,
				R.drawable.ic_launcher, R.drawable.ic_launcher };
		texts = new String[] { "宫式布局1", "宫式布局2", "宫式布局3", "宫式布局4", "宫式布局5",
				"宫式布局6", "宫式布局7", "宫式布局8" };

		GridView gridview = (GridView) findViewById(R.id.gridview);
		ArrayList<Map<String, Object>> lstImageItem = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 8; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", images[i]);
			map.put("itemText", texts[i]);
			lstImageItem.add(map);
		}

		/*
		 * SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,//
		 * 数据源 R.layout.griditem,// 显示布局 new String[] { "itemImage", "itemText"
		 * }, new int[] { R.id.itemImage, R.id.itemText });
		 * gridview.setAdapter(saImageItems);
		 */

		bookAdapter = new BookAdapter(this, lstImageItem, R.layout.griditem, new String[] {}, new int[] {});
		gridview.setAdapter(bookAdapter);

		gridview.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {
		/**
		 * 点击项时触发事件
		 * 
		 * @param parent
		 *            发生点击动作的AdapterView
		 * @param view
		 *            在AdapterView中被点击的视图(它是由adapter提供的一个视图)。
		 * @param position
		 *            视图在adapter中的位置。
		 * @param rowid
		 *            被点击元素的行id。
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long rowid) {
			HashMap<String, Object> item = (HashMap<String, Object>) parent
					.getItemAtPosition(position);
			// 获取数据源的属性值
			String itemText = (String) item.get("itemText");
			Object object = item.get("itemImage");
			Toast.makeText(GvActivity.this, itemText, Toast.LENGTH_LONG).show();

			// 根据图片进行相应的跳转
			switch (images[position]) {
			case R.drawable.ic_launcher:
				startActivity(new Intent(GvActivity.this, TestActivity1.class));// 启动另一个Activity
				finish();// 结束此Activity，可回收
				break;
			/*
			 * case R.drawable.ic_launcher: startActivity(new
			 * Intent(GvActivity.this, TestActivity2.class)); finish(); break;
			 * case R.drawable.ic_launcher: startActivity(new
			 * Intent(GvActivity.this, TestActivity3.class)); finish(); break;
			 */
			}

		}
	}
}