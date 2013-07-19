package com.coosam.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coosam.bean.BookCollection;
import com.coosam.bean.BookRating;

public class MyAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;

	private AsyncImageLoader loader = new AsyncImageLoader();

	private List<BookCollection> bookList;

	public MyAdapter(Context context, List<BookCollection> data) {
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.griditem, null);
			holder = new ViewHolder();
			holder.bookPic = (ImageView) convertView.findViewById(R.id.bookPic);
			holder.bookName = (TextView) convertView
					.findViewById(R.id.bookName);
			holder.bookRate = (RatingBar) convertView
					.findViewById(R.id.ratingBar);
			holder.ratingScore = (TextView) convertView
					.findViewById(R.id.ratingScore);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CallbackImpl callbackImpl = new CallbackImpl(holder.bookPic);
		Drawable cacheImage = loader.loadDrawable(bookList.get(position)
				.getBook().getImage(), callbackImpl);
		if (cacheImage != null) {
			holder.bookPic.setImageDrawable(cacheImage);
		}

		holder.bookName.setText(bookList.get(position).getBook().getTitle());
		holder.bookRate.setNumStars(5);
		holder.bookRate.setStepSize(0.1f);
		BookRating rating = bookList.get(position).getBook().getRating();
		holder.bookRate.setRating(Float.valueOf(rating.getAverage())
				/ rating.getMax() * 5);
		holder.ratingScore.setText(rating.getAverage());
		return convertView;
	}

	private static class ViewHolder {
		private ImageView bookPic;
		private TextView bookName;
		private RatingBar bookRate;
		private TextView ratingScore;
	}

}
