package com.coosam.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerTabStrip;
import android.view.Menu;

public class MyTabActivity extends FragmentActivity {

	/** 页面list **/
	List<Fragment> fragmentList = new ArrayList<Fragment>();
	/** 页面title list **/
	List<String> titleList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpaper_layout);

		ViewPager vp = (ViewPager) findViewById(R.id.viewPager);
		fragmentList.add(new ViewPagerFragment1());
		fragmentList.add(new ViewPagerFragment1());
		fragmentList.add(new ViewPagerFragment1());
		titleList.add("推荐");
		titleList.add("热门");
		titleList.add("我读");
		vp.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragmentList, titleList));
		
		/*PagerTabStrip pts = (PagerTabStrip) findViewById(R.id.paterTabStrip);
		pts.setTextSpacing(100);
		pts.set*/
	}

	/**
	 * 定义适配器
	 * 
	 * @author gxwu@lewatek.com 2012-11-15
	 */
	class myPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList;
		private List<String> titleList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,
				List<String> titleList) {
			super(fm);
			this.fragmentList = fragmentList;
			this.titleList = titleList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (titleList.size() > position) ? titleList.get(position) : "";
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
