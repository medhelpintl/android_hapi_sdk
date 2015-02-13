package org.medhelp.weight.adapter;

import java.util.ArrayList;
import java.util.List;

import org.medhelp.hapi.alpha.hd.MHHealthData;
import org.medhelp.hapi.alpha.util.MHDateUtil;
import org.medhelp.weight.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeightsListAdapter extends ArrayAdapter<MHHealthData> {

	private ViewHolder holder;
	private List<MHHealthData> mDataList;

	private LayoutInflater mInflater;
	
//	private static final String tag = "HAPISAMPLE WeightsListAdapter :: ";
	
	public WeightsListAdapter(Context context, int resource, int textViewResourceId, ArrayList<MHHealthData> objects) {
		super(context, resource, textViewResourceId, objects);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mDataList = objects;
	}
	
    public int getCount() {
        return mDataList.size();
    }

    public MHHealthData getItem(int position) {
        return mDataList.get(position);
    }
    
    /**
     * Returns the position of the specified item in the array.
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(MHHealthData item) {
        return mDataList.indexOf(item);
    }

	public long getItemId(int position){
//		return position;
//        return mDataList.indexOf(item);
		return Long.parseLong(mDataList.get(position).getClientId());
	}

	public View getView(int position, View convertView, ViewGroup parent){
		
		final MHHealthData weightData = (MHHealthData) getItem(position);
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.weight_list_item, parent, false);
			holder = new ViewHolder();
			holder.tvDate = (TextView)convertView.findViewById(R.id.tv_date_time);
			holder.tvWeight = (TextView)convertView.findViewById(R.id.tv_weight);
			holder.tvMedhelpId = (TextView)convertView.findViewById(R.id.tv_medhelpId);
			holder.ivDeletedState = (ImageView)convertView.findViewById(R.id.iv_delete_state);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(weightData != null){
			if (weightData.isDeleted()) {
				holder.ivDeletedState.setImageResource(R.drawable.bg_deleted_state);
			} else {
				holder.ivDeletedState.setImageResource(R.drawable.bg_undeleted_state);
			}
			
			String dateString = MHDateUtil.formatDateToUTC(weightData.getDate(), MHDateUtil.format.YYYY_MM_DD);
			holder.tvDate.setText(dateString);
			holder.tvWeight.setText(weightData.getValue());
			holder.tvMedhelpId.setText(weightData.getMedhelpId());
		}
		return convertView;
	}
	
	static class ViewHolder{
		TextView tvDate;
		TextView tvWeight;
		TextView tvMedhelpId;
		ImageView ivDeletedState;
	}
	
	@Override
	public void add(MHHealthData healthData) {
		super.add(healthData);
	}
}