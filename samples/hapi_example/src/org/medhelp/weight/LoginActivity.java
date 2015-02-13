package org.medhelp.weight;

import org.medhelp.hapi.alpha.MHC;
import org.medhelp.hapi.alpha.MHHapiException;
import org.medhelp.hapi.alpha.account.MHLoginActivity;
import org.medhelp.hapi.alpha.account.MHLoginResponse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends MHLoginActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
	
	@Override
	public void handleException(MHHapiException exception) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoginComplete(MHLoginResponse response) {
		handleResponse(response);
	}
	
	public void handleResponse(MHLoginResponse response) {
		if (response != null && response.getStatusCode() == MHC.statusCode.SUCCESS) {
			startActivity(new Intent(this, WeightActivity.class));
		} else {
			TextView tv = (TextView) findViewById(R.id.login_status);
			tv.setText("Login failed. Try again.");
		}
	}
}
