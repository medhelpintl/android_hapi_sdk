package org.medhelp.weight;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.medhelp.hapi.alpha.MHHapiException;
import org.medhelp.hapi.alpha.hd.MHHealthData;
import org.medhelp.hapi.alpha.util.MHDateUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WeightActivity extends Activity implements OnClickListener {
	
	private static final String tag = "HAPISAMPLE WeightActivity ";
	
	Button mDateBtn;
	Button mSaveBtn;
	Button mDeleteBtn;
	Button mHistoryBtn;
	EditText mWeightField;
	TextView mSaveStatus;
	
	MHHealthData mHealthData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weight);

		mDateBtn = (Button) findViewById(R.id.btn_select_date);
		mSaveBtn = (Button) findViewById(R.id.btn_save);
		mDeleteBtn = (Button) findViewById(R.id.btn_delete);
		mHistoryBtn = (Button) findViewById(R.id.btn_history);
		mWeightField = (EditText) findViewById(R.id.et_weight);
		mSaveStatus = (TextView) findViewById(R.id.tv_save_status);

		mDateBtn.setOnClickListener(this);
		mSaveBtn.setOnClickListener(this);
		mDeleteBtn.setOnClickListener(this);
		mHistoryBtn.setOnClickListener(this);
		
		mDateBtn.setText(MHDateUtil.formatDateToUTC(new Date(), MHDateUtil.format.YYYY_MM_DD));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select_date:
			onDateClick();
			break;
			
		case R.id.btn_save:
			onSaveClick();
			break;
			
		case R.id.btn_delete:
			onDeleteClick();
			break;
			
		case R.id.btn_history:
			startActivity(new Intent(this, WeightHistoryActivity.class));
			break;
			
			default:
				break;
		}
	}
	
	private void onDateClick() {
		String dateString = mDateBtn.getText().toString();
		Calendar c = MHDateUtil.getUTCMidnight(MHDateUtil.getUTCDateFromString(dateString, MHDateUtil.format.YYYY_MM_DD));
		new DatePickerDialog(this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}

	OnDateSetListener dateListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String dateString = MHDateUtil.formatDateToUTC(
					new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime(), MHDateUtil.format.YYYY_MM_DD);
			mDateBtn.setText(dateString);
		}
	};
	
	private void onSaveClick() {
		float weight = 0;
		weight = Float.parseFloat("0"+ mWeightField.getText().toString().trim());
		if (mHealthData == null) {
			Date date = MHDateUtil.getUTCDateFromString(mDateBtn.getText().toString(), MHDateUtil.format.YYYY_MM_DD);
			mHealthData = new MHHealthData(date, "Weight", String.valueOf(weight));
		} else {
			mHealthData.setValue(String.valueOf(weight));
		}
		
		new SaveHealthDataTask().execute(mHealthData);
	}
	
	private void onDeleteClick() {
		if (mHealthData == null) {
			Toast.makeText(this, "No data to delete.", Toast.LENGTH_LONG).show();
		} else {
			new DeleteHealthDataTask().execute(mHealthData);
		}
	}
	
	private class SaveHealthDataTask extends AsyncTask<MHHealthData, Integer, Void> {
	
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mSaveStatus.setText("Saving healthdata...");
		}
		
		@Override
		protected Void doInBackground(MHHealthData... params) {
			try {
				params[0].save();
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
			Log.v(tag, "healthData id " + mHealthData.getMedhelpId());
			mSaveStatus.setText("healthdata medhelpId " + mHealthData.getMedhelpId());
		}
	}
		
	private class DeleteHealthDataTask extends AsyncTask<MHHealthData, Integer, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mSaveStatus.setText("Deleting healthdata...");
		}
		
		@Override
		protected Void doInBackground(MHHealthData... params) {
			try {
				params[0].delete();
			} catch (MHHapiException e) {
				e.printStackTrace();
			}
			
			/* Alternative approach when you have a list of values to delete.
			try {
				List<MHHealthData> dataList = new ArrayList<MHHealthData>();
				dataList.add(params[0]);
				new MHBatch().delete(dataList);
			} catch (MHHapiException e) {
				e.printStackTrace();
			}
			 */
		
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (mHealthData.isDeleted()) {
				mHealthData = null;
				mSaveStatus.setText("healthdata deleted");
			} else {
				mSaveStatus.setText("healthdata has not been deleted");
			}
		}
	
	}

}
