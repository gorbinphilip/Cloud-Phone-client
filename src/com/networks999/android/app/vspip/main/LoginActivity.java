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
import android.widget.EditText;
import android.widget.Toast;

import com.networks999.android.app.vspip.util.CustomHttpClient;

public class LoginActivity extends Activity {
public static int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    
    
public void onLoginClick(View v)
{
	EditText edtUserName=(EditText)findViewById(R.id.edtlgUserName);
	EditText edtPassword=(EditText)findViewById(R.id.edtlgPassword);
final ArrayList<NameValuePair> postparameter=new ArrayList<NameValuePair>();
postparameter.add(new BasicNameValuePair("userName", edtUserName.getText().toString()));
postparameter.add(new BasicNameValuePair("password",edtPassword.getText().toString()));

new Thread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		final String response;
		try {
			Log.e("msg", "onClick");
			response=CustomHttpClient.executeHttpPost("LoginServlet",postparameter);
			Log.d("RESPONSE", response+"  test");
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int usId=Integer.parseInt(response.trim());
				//	LoginActivity.userID=Integer.parseInt(response.trim());
					if(usId!=0)
					{
						Log.e("", response);
					Intent intent=new Intent(LoginActivity.this,OptionActivity.class);
					startActivity(intent);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Invalid UserName/Password", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}).start();

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
