package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.medhelp.hapi.alpha.account.MHApiHandler;
import org.medhelp.hapi.alpha.http.MHNetworkException;
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

	private class LoadWeightsTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			String response1 = null;
			try {
				response1 = MHApiHandler.read(getApplicationContext(),
						"2013-01-01", "2013-01-31", new JSONArray().put("Weight"));
//				String response2 = MHApiHandler.read(getApplicationContext(),
//						new JSONArray().put("Weight"));
//				Log.v(tag, "response2 : " + response2);
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response1;
		}

		@Override
		protected void onPostExecute(String result) {
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
