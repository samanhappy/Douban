package com.saman.douban;

import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
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
import com.coosam.bean.BookCollection;
import com.coosam.http.JsonDataGetApi;
import com.coosam.thread.HttpClientHelper;
import com.coosam.thread.ThreadPoolUtils;
import com.coosam.util.DataUtil;

public class GvActivity extends Activity {
	
	private static final int START_DOWNLOAD_MESSAGE = 0x01;
    private static final int FINISH_DOWNLOAD_MESSAGE = 0x02;
    private static final int ERROR_DOWNLOAD_MESSAGE = 0x03;
    
    private Handler myHandler;
	
	
	private String texts[] = null;
	private int images[] = null;
	// private BookAdapter bookAdapter = null;

	private MyAdapter myAdapter = null;
	
	GridView gridview = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);

		/*
		 * images = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,
		 * R.drawable.ic_launcher, R.drawable.ic_launcher,
		 * R.drawable.ic_launcher, R.drawable.ic_launcher,
		 * R.drawable.ic_launcher, R.drawable.ic_launcher }; texts = new
		 * String[] { "��ʽ����1", "��ʽ����2", "��ʽ����3", "��ʽ����4",
		 * "��ʽ����5", "��ʽ����6", "��ʽ����7", "��ʽ����8" };
		 */

		gridview = (GridView) findViewById(R.id.gridview);

		/*
		 * ArrayList<Map<String, Object>> lstImageItem = new
		 * ArrayList<Map<String, Object>>(); for (int i = 0; i < 8; i++) {
		 * HashMap<String, Object> map = new HashMap<String, Object>();
		 * map.put("itemImage", images[i]); map.put("itemText", texts[i]);
		 * lstImageItem.add(map); }
		 */

		/*
		 * SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,//
		 * ���Դ R.layout.griditem,// ��ʾ���� new String[] { "itemImage",
		 * "itemText" }, new int[] { R.id.itemImage, R.id.itemText });
		 * gridview.setAdapter(saImageItems);
		 */

		// bookAdapter = new BookAdapter(this, lstImageItem, R.layout.griditem,
		// new String[] {}, new int[] {});
		// gridview.setAdapter(bookAdapter);

		myHandler = new MyHandler();
        ThreadPoolUtils.execute(new MyRunnable());
		
		
		

//		gridview.setOnItemClickListener(new ItemClickListener());
	}

	public List<BookCollection> getData() {
		/*JSONObject json = null;
		try {
			json = new JSONObject(
					"{\"count\":20,\"start\":0,\"total\":117,\"collections\":[{\"status\":\"wish\",\"updated\":\"2013-07-12 16:25:47\",\"user_id\":\"43963667\",\"book\":{\"rating\":{\"max\":10,\"numRaters\":646,\"average\":\"8.7\",\"min\":0},\"image\":\"http://img3.douban.com/mpic/s6089770.jpg\",\"title\":\"冬吴相对论\",\"url\":\"http://api.douban.com/v2/book/4124834\"},\"book_id\":\"4124834\",\"id\":703342186}]}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		APIResponse response = (APIResponse) DataUtil.jsonObject2Object(json,
				APIResponse.class);
		return response.getCollections();*/
		
		JSONObject json = null;
   		try
   		{
   			json = new JSONObject(new JsonDataGetApi().getRequest("https://api.douban.com/v2/book/user/samanhappy/collections"));
   		}
   		catch (JSONException e)
   		{
   			e.printStackTrace();
   		}
   		catch (Exception e)
   		{
   			e.printStackTrace();
   		}
   		APIResponse response = (APIResponse) DataUtil.jsonObject2Object(json, APIResponse.class);
   		return response.getCollections();

	}

	private class MyRunnable implements Runnable{
        @Override
        public void run() {
        	
        	String str = "";
			try {
				str = new JsonDataGetApi().getRequest("https://api.douban.com/v2/book/user/samanhappy/collections");
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
    
    private class MyHandler extends Handler{
    	
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
       		try
       		{
				json = new JSONObject(bookList);
       		}
       		catch (JSONException e)
       		{
       			e.printStackTrace();
       		}
       		catch (Exception e)
       		{
       			e.printStackTrace();
       		}
       		APIResponse response = (APIResponse) DataUtil.jsonObject2Object(json, APIResponse.class);

       		
       		myAdapter = new MyAdapter(GvActivity.this, response.getCollections());
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