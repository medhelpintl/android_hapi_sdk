package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.medhelp.hapi.alpha.account.MHHealthData;
import org.medhelp.hapi.alpha.account.MHQuery;
import org.medhelp.hapi.alpha.http.MHNetworkException;
import org.medhelp.hapi.alpha.util.MHDateUtil;
import org.medhelp.hapiclient.alpha.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HCWeightListActivity extends Activity implements OnClickListener {
	private static final String tag = "HAPI HCEditWeightActivity : ";

	ArrayList<String> values = null;

	TextView mTVWeights;
	ProgressBar mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weights);

		findViewById(R.id.btn_get_weights).setOnClickListener(this);
		mTVWeights = (TextView) findViewById(R.id.tv_weights);
		mProgress = (ProgressBar) findViewById(R.id.pb_progress);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_weights:
			onClickGetWeights();
			break;
		}
	}

	private void onClickGetWeights() {
		new LoadWeightsTask().execute("");
	}

	private class LoadWeightsTask extends AsyncTask<String, Integer, List<MHHealthData>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setVisibility(View.VISIBLE);
		}

		@Override	
		protected List<MHHealthData> doInBackground(String... params) {
			List<MHHealthData> user_data = new ArrayList<MHHealthData>();
			try {
				Date now = new Date();
				Date startDate = new Date(now.getTime() + (30 * 24 * 60 * 60 * 1000));
				Date endDate = now;
				List<String> fieldNames = new ArrayList<String>();
				fieldNames.add("Weight");
				
				Log.v(tag, "Start: " + startDate + " End: " + endDate);
				
				user_data = MHQuery.queryUserDataForFields(getApplicationContext(), startDate, endDate, fieldNames);
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				Log.v(tag, "MHNetworkException ");
				e.printStackTrace();
			}

			return user_data;
		}

		@Override
		protected void onPostExecute(List<MHHealthData> user_data) {
			super.onPostExecute(user_data);
			handleResult(user_data);
			mProgress.setVisibility(View.GONE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mProgress.setVisibility(View.GONE);
		}
		
		private void handleResult(List<MHHealthData> result) {
			Log.v(tag, "\n\n\n handleResult");
			if (result == null) {
				Log.v(tag, "MHResult is null");
				Toast.makeText(HCWeightListActivity.this, "MHResult is null", Toast.LENGTH_LONG).show();
				return;
			}
			if (result == null) {
				Log.v(tag, "DataList in MHResult is null");
				Toast.makeText(HCWeightListActivity.this, "DataList in MHResult is null", Toast.LENGTH_LONG).show();
				return;
			}
			if (result.size() == 0) {
				Log.v(tag, "DataList in MHResult has no HealthData items");
				Toast.makeText(HCWeightListActivity.this, "DataList in MHResult has no HealthData items", Toast.LENGTH_LONG).show();
				return;
			}
			loadWeights(result);
		}
	}

	private void loadWeights(List<MHHealthData> result) {
		List<MHHealthData> weights = result;
		
		String weightsDisplay = "";
		for (MHHealthData data : weights) {
			weightsDisplay += "\n\n" + MHDateUtil.formatDate(data.getDate(), MHDateUtil.format.YYYY_MM_DD) + "  :  " + data.getValue();
		}
		mTVWeights.setText(weightsDisplay);
	}

}
