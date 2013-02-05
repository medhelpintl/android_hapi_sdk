package org.medhelp.hapiclient.alpha.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import org.medhelp.hapiclient.alpha.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HealthDataAdapter extends ArrayAdapter<String> {
	 private static final String tag = "HAPI HealthDataAdapter :: ";

	private ViewHolder holder;
	private List<String> mValues;
	private LayoutInflater mInflater;

	public HealthDataAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<String> objects) {
		super(context, resource, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mValues = objects;
	}

	public int getCount() {
		return mValues.size();
	}

	public String getItem(int position) {
		return mValues.get(position);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final String dataValue = (String) getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();
			holder.tvHealthDataItem = (TextView) convertView
					.findViewById(R.id.tv_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (dataValue != null) {
			holder.tvHealthDataItem.setText(dataValue);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tvHealthDataItem;
	}
}