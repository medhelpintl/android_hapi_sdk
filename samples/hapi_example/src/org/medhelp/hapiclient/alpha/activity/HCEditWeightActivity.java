package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;
import java.util.Date;

import org.medhelp.hapi.alpha.account.MHApiClient;
import org.medhelp.hapi.alpha.account.MHHealthData;
import org.medhelp.hapi.alpha.account.MHQuery;
import org.medhelp.hapi.alpha.http.MHNetworkException;
import org.medhelp.hapi.alpha.model.MHResponse;
import org.medhelp.hapiclient.alpha.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class HCEditWeightActivity extends Activity implements OnClickListener {
	private static final String tag = "HAPI HCEditWeightActivity : ";

	TextView mTVLastWeight;
	TextView mTVDeleteStatus;

	EditText mETCreateWeight;
	EditText mETUpdateWeight;

	MHHealthData healthData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_weight);

		mTVLastWeight = (TextView) findViewById(R.id.tv_last_weight);
		mTVDeleteStatus = (TextView) findViewById(R.id.tv_delete_status);

		mETCreateWeight = (EditText) findViewById(R.id.et_create_weight);
		mETUpdateWeight = (EditText) findViewById(R.id.et_update_weight);

		findViewById(R.id.btn_create_weight).setOnClickListener(this);
		findViewById(R.id.btn_update_weight).setOnClickListener(this);
		findViewById(R.id.btn_delete_last_weight).setOnClickListener(this);
		findViewById(R.id.btn_weight_list).setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Save the health data object.
		new LatestWeightTask().execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_weight:
			onClickCreate();
			break;

		case R.id.btn_update_weight:
			onClickUpdate();
			break;

		case R.id.btn_delete_last_weight:
			onClickDelete();
			break;

		case R.id.btn_weight_list:
			startActivity(new Intent(this, HCWeightListActivity.class));
			break;
		}
	}

	private void onClickCreate() {
		float weight = 0;
		weight = Float.parseFloat("0"
				+ mETCreateWeight.getText().toString().trim());

		long currentTime = System.currentTimeMillis() / 1000L;

		// Create a new healthdata object
		healthData = new MHHealthData(getApplicationContext());

		// update the fields
		healthData.setCreatedAt(currentTime);
		healthData.setDate(new Date());
		healthData.setFieldName("Weight");
		healthData.setValue(String.valueOf(weight));

		// Save the health data object.
		new SaveHealthDataTask().execute(healthData);
	}

	private void onClickUpdate() {
		if (healthData == null) {
			return;
		}
		
		float weight = 0;
		weight = Float.parseFloat("0" + mETUpdateWeight.getText().toString().trim());

		Log.v(tag, "New Weight " + weight);
		
		healthData.setValue(String.valueOf(weight));

		// Save the health data object.
		new SaveHealthDataTask().execute(healthData);
	}

	/**
	 * Deletes the healthData on server
	 */
	private void onClickDelete() {
		new DeleteTask().execute(healthData);
	}

	private class SaveHealthDataTask extends
			AsyncTask<MHHealthData, Integer, MHResponse> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected MHResponse doInBackground(MHHealthData... params) {
			MHResponse response = null;
			try {
				response = params[0].save(getApplicationContext());
				Log.v(tag, "response " + response);
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(MHResponse result) {
			super.onPostExecute(result);
			Log.v(tag, "result : " + result);
			
			// Save the health data object.
			new LatestWeightTask().execute();
		}
	}

	private class DeleteTask extends AsyncTask<MHHealthData, Integer, MHResponse> {

		@Override
		protected MHResponse doInBackground(MHHealthData... params) {
			MHResponse response = null;
			try {
				ArrayList<MHHealthData> dataList = new ArrayList<MHHealthData>();
				dataList.add(params[0]);
				response = MHApiClient.delete(getApplicationContext(), dataList);
				Log.v(tag, "response " + response);
			} catch (MHNetworkException e) {
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(MHResponse result) {
			//TODO Handle the result
			super.onPostExecute(result);

			// Save the health data object.
			new LatestWeightTask().execute();
		}

	}
	
	private class LatestWeightTask extends AsyncTask<MHHealthData, Integer, MHHealthData> {
		
		@Override
		protected MHHealthData doInBackground(MHHealthData... params) {
			try {
				return MHQuery.getLatestUserData(getApplicationContext(), "Weight");
			} catch (MHNetworkException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(MHHealthData result) {
			super.onPostExecute(result);
			
			healthData = result;
			mTVLastWeight.setText("" + result.getValue());
		}
		
	}

}
