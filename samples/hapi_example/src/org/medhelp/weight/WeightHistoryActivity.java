package org.medhelp.weight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.medhelp.hapi.alpha.MHHapiException;
import org.medhelp.hapi.alpha.hd.MHHealthData;
import org.medhelp.hapi.alpha.hd.MHQuery;
import org.medhelp.hapi.alpha.util.MHDateUtil;
import org.medhelp.weight.adapter.WeightsListAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class WeightHistoryActivity extends Activity implements OnClickListener {

	private static final String tag = "HAPISAMPLE WeightHistoryActivity : ";
	
	private ListView mWeightsLV;
	private WeightsListAdapter mWeightsAdapter;
	private TextView mLoadStatus;
	private ArrayList<MHHealthData> mDataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weights_list);
		
		mWeightsLV = (ListView) findViewById(R.id.lv_weights);
		mLoadStatus = (TextView) findViewById(R.id.tv_load_status);
		
		mDataList = new ArrayList<MHHealthData>();
		mWeightsAdapter = new WeightsListAdapter(
				WeightHistoryActivity.this, 
				R.layout.weight_list_item, 
				R.id.tv_date_time, 
				mDataList);
		
		mWeightsLV.setAdapter(mWeightsAdapter);
		
		new LoadWeightstask().execute();
	}
	
	@Override
	public void onClick(View v) {
	}
	
	private class LoadWeightstask extends AsyncTask<Void, Integer, Void> {
		
		List<MHHealthData> _dataList = new ArrayList<MHHealthData>();
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mLoadStatus.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			List<String> fields = new ArrayList<String>();
			fields.add("Weight");
			
			
			MHQuery query = new MHQuery();
			try {
				_dataList = query.queryForAllPages(MHDateUtil.formatDateToUTC(new Date(0), MHDateUtil.format.YYYY_MM_DD), 
						MHDateUtil.formatDateToUTC(new Date(), MHDateUtil.format.YYYY_MM_DD), fields);
			} catch (MHHapiException e) {
				e.printStackTrace();
			}

			/* Alternative approach when you have a list of values to save.
			try {
				List<MHHealthData> dataList = new ArrayList<MHHealthData>();
				dataList.add(params[0]);
				new MHBatch().save(dataList);
			} catch (MHHapiException e) {
				e.printStackTrace();
			}
			 */
		
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mLoadStatus.setVisibility(View.GONE);
			
			if (_dataList != null) {
//				mDataList = new ArrayList<MHHealthData>();
//				mWeightsAdapter.notifyDataSetChanged();
				
				Log.v(tag, "dataList size " + _dataList.size());
				int size = _dataList.size();
				for (int i=0; i<size; i++) {
					MHHealthData hd = _dataList.get(i);
					hd.setClientIdWithoutFlags(i+"");
					mDataList.add(hd);
					mWeightsAdapter.notifyDataSetChanged();
				}
			}
		}
	}

}
