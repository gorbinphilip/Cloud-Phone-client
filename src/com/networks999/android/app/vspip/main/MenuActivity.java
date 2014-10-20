package com.networks999.android.app.vspip.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}
	public void onLoginClick(View v)
	{
		Intent intent=new Intent(this,LoginActivity.class);
		startActivity(intent);
		
	}
	
	public void onRegisterClick(View v)
	{
		Intent intent =new Intent(this,UserRegistration.class);
		startActivity(intent);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
