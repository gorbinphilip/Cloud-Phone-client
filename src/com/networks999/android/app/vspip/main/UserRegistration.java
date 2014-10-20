package com.networks999.android.app.vspip.main;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.networks999.android.app.vspip.util.CustomHttpClient;

public class UserRegistration extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_registration);

	}

	public void onRegisterClick(View v) {
		EditText edtUserName = (EditText) findViewById(R.id.edtUserName);
		EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
		EditText edtEmail = (EditText) findViewById(R.id.edtEmail);
		final ArrayList<NameValuePair> postparameters = new ArrayList<NameValuePair>();
		postparameters.add(new BasicNameValuePair("userName", edtUserName
				.getText().toString()));
		postparameters.add(new BasicNameValuePair("password", edtPassword
				.getText().toString()));
		postparameters.add(new BasicNameValuePair("email", edtEmail.getText()
				.toString()));
		try {

			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						Log.e("", "onClick");
						CustomHttpClient.executeHttpPost(
								"UserRegistrationServlet", postparameters);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Sucessfully Registred",
										Toast.LENGTH_SHORT).show();

							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// TODO Auto-generated method stub

				}
			}).start();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_registration, menu);
		return true;
	}

}
