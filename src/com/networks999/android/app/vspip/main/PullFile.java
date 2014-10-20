package com.networks999.android.app.vspip.main;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.networks999.android.app.vspip.util.CustomHttpClient;
import com.networks999.android.app.vspip.util.UrlConnections;

public class PullFile extends Activity {
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_file);
	}

	public void onDownloadClick(View v) {
		final EditText edtFilename = (EditText) findViewById(R.id.edtFileNamePull);
		final ArrayList<NameValuePair> postparameter = new ArrayList<NameValuePair>();
		postparameter.add(new BasicNameValuePair("fileName", edtFilename
				.getText().toString()));
		dialog = ProgressDialog.show(PullFile.this, "FIle Download",
				"Downloading...Please Wait", true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final String response;
				try {
					response = CustomHttpClient.executeHttpPost(
							"PullFilleServlet", postparameter);
					Log.d("RESPONSE", response + "  test");
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

						}
					});
				} catch (Exception e) {

					// TODO: handle exception
					e.printStackTrace();
				}
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {

					downloadAndWriteToDisk(UrlConnections.PullFilePath
							+ edtFilename.getText().toString(),
							Environment.getExternalStorageDirectory() + "/"
									+ edtFilename.getText().toString());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				dialog.cancel();

			}
		}).start();

		/*
		 * String coverFilePath = Environment .getExternalStorageDirectory() +
		 * "/" + edtFilename.getText().toString();
		 */

	}

	public void downloadAndWriteToDisk(String fileUrl, String path) {
		try {
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();
			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(path);
			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				Bundle resultData = new Bundle();
				resultData.putInt("progress", (int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}
			output.flush();
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pull_file, menu);
		return true;
	}

}
