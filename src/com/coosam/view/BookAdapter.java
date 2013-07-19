package com.coosam.view;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BookAdapter extends SimpleAdapter {

	private Context context;

	private LayoutInflater layoutInflater;

	private List<Map<String, Object>> bookList;

	private AsyncImageLoader loader = new AsyncImageLoader();

	public BookAdapter(Context context, List<Map<String, Object>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.bookList = data;
	}
	
	
	@Override
	public int getCount() {
		return bookList != null ? bookList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return bookList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		 * // �Ż�ǰ ViewHolder holder = new ViewHolder(); convertView =
		 * layoutInflater.inflate(R.layout.main, null); holder.id =
		 * (TextView)convertView.findViewById(R.id.id); holder.name =
		 * (TextView)convertView.findViewById(R.id.name); holder.age =
		 * (TextView)convertView.findViewById(R.id.age);
		 * convertView.setTag(holder);
		 * 
		 * holder.id.setText(persons.get(position).getId());
		 * holder.name.setText(persons.get(position).getName());
		 * holder.age.setText(persons.get(position).getAge()); return
		 * convertView;
		 */

		// �Ż���
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.griditem, null);
			holder = new ViewHolder();
			holder.bookPic = (ImageView) convertView.findViewById(R.id.bookPic);
			holder.bookName = (TextView) convertView
					.findViewById(R.id.bookName);
			holder.bookRate = (RatingBar) convertView
					.findViewById(R.id.ratingBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// holder.bookPic
		// .setImageBitmap(PicUtil
		// .returnBitMap("http://img3.douban.com/view/ark_article_cover/cut/public/884781.jpg?v=1367983006.0"));

		CallbackImpl callbackImpl = new CallbackImpl(holder.bookPic);
		Drawable cacheImage = loader
				.loadDrawable(
						"http://img3.douban.com/view/ark_article_cover/cut/public/884781.jpg?v=1367983006.0",
						callbackImpl);
		if (cacheImage != null) {
			holder.bookPic.setImageDrawable(cacheImage);
		}

		holder.bookName.setText("ʧ��");
		holder.bookRate.setMax(100); // �������̶�
		holder.bookRate.setProgress(20); // ���õ�ǰ�̶�
		return convertView;
	}

	/**
	 * �����ϵ���ʾ�ؼ�
	 * 
	 * @author jiqinlin
	 * 
	 */
	private static class ViewHolder {
		private ImageView bookPic;
		private TextView bookName;
		private RatingBar bookRate;
	}

}
