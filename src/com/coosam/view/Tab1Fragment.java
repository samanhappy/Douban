package com.coosam.view;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.coosam.bean.APIResponse;
import com.coosam.http.JsonDataGetApi;
import com.coosam.thread.ThreadPoolUtils;
import com.coosam.util.DataUtil;

public class Tab1Fragment extends Fragment implements OnItemClickListener {

	private Handler myHandler;

	private MyAdapter myAdapter = null;

	GridView gridview = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tab1, container, true);
		gridview = (GridView) view.findViewById(R.id.tabgridview);
		myHandler = new MyHandler();
		ThreadPoolUtils.execute(new MyRunnable());
		gridview.setOnItemClickListener(this);
		return view;

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

			myAdapter = new MyAdapter(getActivity(), response.getCollections());
			gridview.setAdapter(myAdapter);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.e("click", "1111111111");
	}

}
