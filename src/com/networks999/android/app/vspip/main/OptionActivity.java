package com.networks999.android.app.vspip.main;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.networks999.android.app.vspip.util.CustomHttpClient;

public class OptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
	}
public void onViewAndX(View v)
{
	final ArrayList<NameValuePair> postparameter=new ArrayList<NameValuePair>();
	postparameter.add(new BasicNameValuePair("intoServlet", "HaiServlet"));

	
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			 final String response;
			try {
				response=CustomHttpClient.executeHttpPost("X86StartServlet",postparameter);
				Log.d("RESPONSE", response+"  test");
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Toast.makeText(getApplicationContext(), "Start Capturing", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(OptionActivity.this,ScreenCapture.class);
							startActivity(intent);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}
	}).start();
	
}
public void onPushFile(View v)
{
Intent intent=new Intent(OptionActivity.this,PushFile.class);
startActivity(intent);
}
public void onPullbtnClick(View V)
{
	Intent intent=new Intent(OptionActivity.this,PullFile.class);
	startActivity(intent);
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

}
