package com.coosam.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TabHost;

public class MyTabActivity extends FragmentActivity {

	TabHost tabHost = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();

		tabHost.addTab(tabHost.newTabSpec("推荐").setIndicator("推荐")
				.setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("我读").setIndicator("我读")
				.setContent(R.id.tab2));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private GestureDetector detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
		 
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if ((e2.getRawX() - e1.getRawX()) > 80) {
                        showNext();
                        return true;
                }

                if ((e1.getRawX() - e2.getRawX()) > 80) {
                        showPre();
                        return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
        }

});

@Override
public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
}

/**
 * 当前页面索引
 */
int i = 0;

/**
 * 显示下一个页面
 */
protected void showNext() {
        // 三元表达式控制3个页面的循环.
        tabHost.setCurrentTab(i = i == 2 ? i = 0 : ++i);
        Log.i("kennet", i + "");

}

/**
 * 显示前一个页面
 */
protected void showPre() {
        // 三元表达式控制3个页面的循环.
        tabHost.setCurrentTab(i = i == 0 ? i = 2 : --i);

}

}
