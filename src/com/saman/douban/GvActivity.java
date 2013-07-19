package com.saman.douban;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.coosam.bean.APIResponse;
import com.coosam.http.JsonDataGetApi;
import com.coosam.thread.ThreadPoolUtils;
import com.coosam.util.DataUtil;

public class GvActivity extends Activity {

	private Handler myHandler;

	private int images[] = null;

	private MyAdapter myAdapter = null;

	GridView gridview = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		gridview = (GridView) findViewById(R.id.gridview);
		myHandler = new MyHandler();
		ThreadPoolUtils.execute(new MyRunnable());
	}

	private class MyRunnable implements Runnable {
		@Override
		public void run() {

			String str = "";
			try {
				str = new JsonDataGetApi()
						.getRequest("https://api.douban.com/v2/book/user/samanhappy/collections");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("bookList", str);
			msg.setData(data);
			myHandler.sendMessage(msg);
		}
	}

	private class MyHandler extends Handler {

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Log.d("MyHandler", "handleMessage......");
			super.handleMessage(msg);
			// 此处可以更新UI
			Bundle b = msg.getData();
			String bookList = b.getString("bookList");

			JSONObject json = null;
			try {
				json = new JSONObject(bookList);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			APIResponse response = DataUtil.jsonObject2Object(json,
					APIResponse.class);

			myAdapter = new MyAdapter(GvActivity.this,
					response.getCollections());
			gridview.setAdapter(myAdapter);
		}

	}

	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long rowid) {
			HashMap<String, Object> item = (HashMap<String, Object>) parent
					.getItemAtPosition(position);
			String itemText = (String) item.get("itemText");
			Object object = item.get("itemImage");
			Toast.makeText(GvActivity.this, itemText, Toast.LENGTH_LONG).show();

			switch (images[position]) {
			case R.drawable.ic_launcher:
				startActivity(new Intent(GvActivity.this, TestActivity1.class));
				finish();
				break;
			}
		}
	}
}