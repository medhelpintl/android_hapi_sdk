package org.medhelp.hapiclient.alpha.activity;

import org.medhelp.hapi.alpha.MHC;
import org.medhelp.hapiclient.alpha.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class HCLoginLandingActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_landing);
		
		int loginResponseCode = savedInstanceState.getInt(MHC.extras.RESPONSE_CODE);
		String responseMessage = savedInstanceState.getString(MHC.extras.RESPONSE_MESSAGE);
		
		/*
		 * Check the response code and response message here.
		 * A response code MHC.statusCode.LOGIN_SUCCESS means a login success
		 */
		if (loginResponseCode == MHC.statusCode.LOGIN_SUCCESS) {
			Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Login Failure: " + responseMessage, Toast.LENGTH_LONG).show();
		}
		
		findViewById(R.id.btn_weight).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_weight:
			startActivity(new Intent(this, HCEditWeightActivity.class));
			break;
		}
	}

}
