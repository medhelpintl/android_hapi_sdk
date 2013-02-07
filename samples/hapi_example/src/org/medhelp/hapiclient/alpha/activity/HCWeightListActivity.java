package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.medhelp.hapi.alpha.account.MHApiClient;
import org.medhelp.hapi.alpha.http.MHNetworkException;
import org.medhelp.hapi.alpha.model.MHResult;
import org.medhelp.hapiclient.alpha.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HCWeightListActivity extends Activity implements OnClickListener {
	private static final String tag = "HAPI HCEditWeightActivity : ";

	ArrayList<String> values = null;

	TextView mTVLastWeight;
	ListView mLVWeights;
	ProgressBar mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weight_list);

		findViewById(R.id.btn_get_weights).setOnClickListener(this);

		mLVWeights = (ListView) findViewById(android.R.id.list);
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

				Log.v(tag, "result = null ? " + (result == null));
				Log.v(tag, "getDataList() = null ? " + (result.getDataList() == null) );
				Log.v(tag, "result.getDataList().size : " + result.getDataList().size());
				Log.v(tag, "result.getDataList().get(0).toJSONObject() " + result.getDataList().get(0).toJSONObject());
				Log.v(tag, "result " + result.getDataList().size());
				Log.v(tag, "result " + result.getDataList().get(0).toJSONObject().toString());
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				Log.v(tag, "MHNetworkException ");
//				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(MHResult result) {
			super.onPostExecute(result);
			Log.v(tag, "result : " + result);
			mProgress.setVisibility(View.GONE);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			mProgress.setVisibility(View.GONE);
		}
	}

	private ArrayList<String> loadExercises() {
		return null;
	}

}
