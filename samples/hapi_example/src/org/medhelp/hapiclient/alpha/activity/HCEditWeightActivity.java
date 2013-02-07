package org.medhelp.hapiclient.alpha.activity;

import java.util.ArrayList;
import java.util.Date;

import org.medhelp.hapi.alpha.account.MHApiClient;
import org.medhelp.hapi.alpha.account.MHHealthData;
import org.medhelp.hapi.alpha.http.MHNetworkException;
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
		healthData.setClientId(1);
		healthData.setCreatedAt(currentTime);
		healthData.setDate(new Date());
		healthData.setFieldName("Weight");
		healthData.setUpdatedAt(currentTime);
		healthData.setValue(String.valueOf(weight));

		// Save the health data object.
		new SaveHealthDataTask().execute(healthData);
	}

	private void onClickUpdate() {
		float weight = 0;
		weight = Float.parseFloat("0"
				+ mETCreateWeight.getText().toString().trim());

		long currentTime = System.currentTimeMillis() / 1000L;

		// Create a new healthdata object
		MHHealthData weightData = new MHHealthData(getApplicationContext());

		// update the fields
		weightData.setMedhelpId("8a2b9b9052d501309b0e1231392274ec");
		weightData.setClientId(1);
		weightData.setCreatedAt(currentTime);
		weightData.setDate(new Date());
		weightData.setFieldName("Weight");
		weightData.setUpdatedAt(currentTime);
		weightData.setValue(String.valueOf(weight));

		// Save the health data object.
		new SaveHealthDataTask().execute(weightData);
	}

	private void onClickDelete() {
		float weight = 0;
		weight = Float.parseFloat("0"
				+ mETCreateWeight.getText().toString().trim());

		long currentTime = System.currentTimeMillis() / 1000L;

		// Create a new healthdata object
		MHHealthData weightData = new MHHealthData(getApplicationContext());

		// update the fields
		weightData.setMedhelpId("8a2b9b9052d501309b0e1231392274ec");
		weightData.setClientId(1);
		weightData.setCreatedAt(currentTime);
		weightData.setDate(new Date());
		weightData.setFieldName("Weight");
		weightData.setUpdatedAt(currentTime);
		weightData.setValue(String.valueOf(weight));
		
		new DeleteTask().execute(weightData);
	}
	
	private class SaveHealthDataTask extends AsyncTask<MHHealthData, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(MHHealthData... params) {
			String response = null;
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
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.v(tag, "result : " + result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}

	private class DeleteTask extends
			AsyncTask<MHHealthData, Integer, String> {

		@Override
		protected String doInBackground(MHHealthData... params) {
			String response = null;
			try {
				ArrayList<MHHealthData> dataList = new ArrayList<MHHealthData>();
				dataList.add(params[0]);
				MHApiClient.delete(getApplicationContext(), dataList);
				Log.v(tag, "response " + response);
			} catch (MHNetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

	}

}
