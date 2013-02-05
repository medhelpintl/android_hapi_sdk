package org.medhelp.hapiclient.alpha;

import org.medhelp.hapi.alpha.account.MHLoginClient;
import org.medhelp.hapiclient.alpha.activity.HCEditWeightActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HCHomeActivity extends Activity implements OnClickListener {

	private static final String REDIRECT_URI = "intent://org.medhelp.testapp";
	private static final String CLIENT_ID = "GBJpfxr47tGRK75uatYHTAUd1o3Xlq7i";
	private static final String CLIENT_SECRET = "xJ8ri7K6TGz04lps";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_app).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			MHLoginClient.login(this, REDIRECT_URI, CLIENT_ID, CLIENT_SECRET);
			break;
		case R.id.btn_app:
			startActivity(new Intent(this, HCEditWeightActivity.class));
			break;
		}
	}
}
