package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.medhelp.hapi.alpha.account.MHApiClient;
import org.medhelp.hapi.alpha.account.MHHealthData;
import org.medhelp.hapi.alpha.http.MHNetworkException;
import org.medhelp.hapi.alpha.model.MHResult;
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

	private class LoadWeightsTask extends AsyncTask<String, Integer, MHResult> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected MHResult doInBackground(String... params) {
			MHResult result = null;
			try {
				result = MHApiClient.read(getApplicationContext(),
						"2013-01-01", "2013-02-07", new JSONArray().put("Weight"));
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				Log.v(tag, "MHNetworkException ");
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(MHResult result) {
			super.onPostExecute(result);
			handleResult(result);
			mProgress.setVisibility(View.GONE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mProgress.setVisibility(View.GONE);
		}
		
		private void handleResult(MHResult result) {
			Log.v(tag, "\n\n\n handleResult");
			if (result == null) {
				Log.v(tag, "MHResult is null");
				Toast.makeText(HCWeightListActivity.this, "MHResult is null", Toast.LENGTH_LONG).show();
				return;
			}
			if (result.getDataList() == null) {
				Log.v(tag, "DataList in MHResult is null");
				Toast.makeText(HCWeightListActivity.this, "DataList in MHResult is null", Toast.LENGTH_LONG).show();
				return;
			}
			if (result.getDataList().size() == 0) {
				Log.v(tag, "DataList in MHResult has no HealthData items");
				Toast.makeText(HCWeightListActivity.this, "DataList in MHResult has no HealthData items", Toast.LENGTH_LONG).show();
				return;
			}
			loadWeights(result);
		}
	}

	private void loadWeights(MHResult result) {
		List<MHHealthData> weights = result.getDataList();
		
		String weightsDisplay = "";
		for (MHHealthData data : weights) {
			weightsDisplay += "\n\n" + MHDateUtil.formatDate(data.getDate(), MHDateUtil.format.YYYY_MM_DD) + "  :  " + data.getValue();
		}
		mTVWeights.setText(weightsDisplay);
	}

}
