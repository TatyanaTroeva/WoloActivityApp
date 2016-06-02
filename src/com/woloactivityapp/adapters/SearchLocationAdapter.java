package com.woloactivityapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.woloactivityapp.common.CustomTextView;
import com.woloactivityapp.constants.GooglePlaceInfo;
import com.woloactivityapp.main.R;
import com.woloactivityapp.views.SearchActivity;
public class SearchLocationAdapter extends BaseAdapter {

	private LayoutInflater 					mInflater;
	ArrayList<GooglePlaceInfo> 		detailList;
	SearchActivity 							searchActivity;
	OnClickListener 						peopleOnClickListener;
	Typeface 								regularTypeface_;
	private final int						REQUEST_WHERE = 0;
	public SearchLocationAdapter(Context c, ArrayList<GooglePlaceInfo> detailList) {
		this.detailList = detailList;
		searchActivity = (SearchActivity)c;
		mInflater = LayoutInflater.from(c);
		regularTypeface_ = Typeface.createFromAsset(searchActivity.getAssets(), "fonts/Roboto_Regular.ttf");
	}

	@Override
	public int getCount() {
		return detailList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return detailList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.placeitemlayout, null);
			holder = new ViewHolder();
			holder.title = (CustomTextView) convertView.findViewById(R.id.itemTitleCustomTextView);
			holder.address = (CustomTextView) convertView.findViewById(R.id.itemAddressCustomTextView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final GooglePlaceInfo placeDetailInfo = detailList.get(position);
		holder.title.setText(placeDetailInfo.getname());
		holder.address.setText(placeDetailInfo.getformatted_address());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("name", placeDetailInfo.getname());
				data.putExtra("lat", placeDetailInfo.getlat());
				data.putExtra("lng", placeDetailInfo.getlng());
				searchActivity.setResult(REQUEST_WHERE, data);
				searchActivity.finish();
			}
		});
		return convertView;
	}

	static class ViewHolder {
		CustomTextView title;
		CustomTextView address;
	}
	
}