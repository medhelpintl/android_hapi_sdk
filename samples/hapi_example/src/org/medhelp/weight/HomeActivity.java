package org.medhelp.weight;

import org.medhelp.hapi.alpha.MHHapiException;
import org.medhelp.hapi.alpha.account.MHApiInitializer;
import org.medhelp.hapi.alpha.account.MHOAuthManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity implements OnClickListener {

	private static final String REDIRECT_URI = "intent://org.medhelp.testapp";
	private static final String CLIENT_ID = "GBJpfxr47tGRK75uatYHTAUd1o3Xlq7i";
	private static final String CLIENT_SECRET = "xJ8ri7K6TGz04lps";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_skip).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			try {
				MHApiInitializer.initialize(getApplicationContext());
				MHOAuthManager.login(this, REDIRECT_URI, CLIENT_ID, CLIENT_SECRET);
			} catch (MHHapiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btn_skip:
			startActivity(new Intent(this, WeightActivity.class));
			break;
		}
	}
	
}
