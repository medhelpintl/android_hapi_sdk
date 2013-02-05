package org.medhelp.hapiclient.alpha.activity;

import org.medhelp.hapiclient.alpha.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HCLoginLandingActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_landing);
		
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
