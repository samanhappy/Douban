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
		texts = new String[] { "��ʽ����1", "��ʽ����2", "��ʽ����3", "��ʽ����4", "��ʽ����5",
				"��ʽ����6", "��ʽ����7", "��ʽ����8" };

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
		 * ����Դ R.layout.griditem,// ��ʾ���� new String[] { "itemImage", "itemText"
		 * }, new int[] { R.id.itemImage, R.id.itemText });
		 * gridview.setAdapter(saImageItems);
		 */

		bookAdapter = new BookAdapter(this, lstImageItem, R.layout.griditem, new String[] {}, new int[] {});
		gridview.setAdapter(bookAdapter);

		gridview.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {
		/**
		 * �����ʱ�����¼�
		 * 
		 * @param parent
		 *            �������������AdapterView
		 * @param view
		 *            ��AdapterView�б��������ͼ(������adapter�ṩ��һ����ͼ)��
		 * @param position
		 *            ��ͼ��adapter�е�λ�á�
		 * @param rowid
		 *            �����Ԫ�ص���id��
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long rowid) {
			HashMap<String, Object> item = (HashMap<String, Object>) parent
					.getItemAtPosition(position);
			// ��ȡ����Դ������ֵ
			String itemText = (String) item.get("itemText");
			Object object = item.get("itemImage");
			Toast.makeText(GvActivity.this, itemText, Toast.LENGTH_LONG).show();

			// ����ͼƬ������Ӧ����ת
			switch (images[position]) {
			case R.drawable.ic_launcher:
				startActivity(new Intent(GvActivity.this, TestActivity1.class));// ������һ��Activity
				finish();// ������Activity���ɻ���
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