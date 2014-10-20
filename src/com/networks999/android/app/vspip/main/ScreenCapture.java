package com.networks999.android.app.vspip.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.networks999.android.app.vspip.util.CustomHttpClient;
import com.networks999.android.app.vspip.util.UrlConnections;

public class ScreenCapture extends Activity {
	OutputStream outputStream;
	static BufferedReader din, dinfile;
	InputStream inputStream;
	Socket client, client1;
	Bitmap bmp, bmp1;
	String s;
	int x, y;
	static OutputStream out;
	static PrintStream ps;
	static Socket soc;
	byte[] bytes;
	static InputStream in, inputstream;
	Socket socket = null;
	DataOutputStream dataOutputStream = null;
	DataInputStream dataInputStream = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_screen_capture);

		final ImageView imgScreen = (ImageView) findViewById(R.id.imgScreenView);

		new Thread(new Runnable() {

			@Override
			public void run() {
				connectSocket();
				while (true) {
					try {

						bytes = receiveBytes();
						bmp = BitmapFactory.decodeByteArray(bytes, 0,
								bytes.length);
						Thread.sleep(50);
					} catch (Exception e) {
						// TODO: handle exception
					}
					ScreenCapture.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								Log.e("", "Bitmap");
								if (bmp != null) {
									bmp1 = bmp;
									Log.e("", "inside bitmap");
									Bitmap.createScaledBitmap(bmp1, 800, 552,
											false);
									imgScreen.setImageBitmap(bmp1);
									Thread.sleep(50);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					});
				}
			}
		}).start();
		try {

			imgScreen.setOnTouchListener(new OnTouchListener() {

				private int x2;
				private int y2;
				private int x3;
				private int y3;
				private int x1;
				private int y1;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					x = (int) event.getX();
					y = (int) event.getY();

					if (event.getAction() == MotionEvent.ACTION_UP) {
						x2 = (int) event.getX();
						y2 = (int) event.getY();
						Log.d("up", "X:" + x2 + "Y:" + y2);
					}
					if (event.getAction() == MotionEvent.ACTION_CANCEL) {
						x3 = (int) event.getX();
						y3 = (int) event.getY();
						Log.d("cancel", "X:" + x3 + "Y:" + y3);
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						x1 = (int) event.getX();
						y1 = (int) event.getY();
						Log.d("down", "X:" + x1 + "Y:" + y1);
					}
					final ArrayList<NameValuePair> postparameters = new ArrayList<NameValuePair>();

					postparameters.add(new BasicNameValuePair("x1", Integer
							.toString(x1)));
					postparameters.add(new BasicNameValuePair("x2", Integer
							.toString(x2)));
					postparameters.add(new BasicNameValuePair("y1", Integer
							.toString(y1)));
					postparameters.add(new BasicNameValuePair("y2", Integer
							.toString(y2)));

					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								CustomHttpClient.executeHttpPost(
										"SwipeListener", postparameters);
							} catch (Exception e)

							{
								e.printStackTrace();
							}
						}
					}).start();
					return false;
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
		}
		imgScreen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "heloooo",
						Toast.LENGTH_SHORT).show();
				final ArrayList<NameValuePair> postparameter = new ArrayList<NameValuePair>();
				postparameter.add(new BasicNameValuePair("xaxis", Integer
						.toString(x)));
				postparameter.add(new BasicNameValuePair("yaxis", Integer
						.toString(y)));
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							CustomHttpClient.executeHttpPost("TouchListen",
									postparameter);
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

					}
				}).start();
				// TODO Auto-generated method stub

			}
		});
		/*
		 * imgScreen.setOnLongClickListener(new OnLongClickListener() {
		 * 
		 * @Override public boolean onLongClick(View v) {
		 * Toast.makeText(getApplicationContext(), "heloooo",
		 * Toast.LENGTH_SHORT).show(); final ArrayList<NameValuePair>
		 * postparameter=new ArrayList<NameValuePair>(); postparameter.add(new
		 * BasicNameValuePair("xaxis",Integer.toString(x)));
		 * postparameter.add(new
		 * BasicNameValuePair("yaxis",Integer.toString(y))); new Thread(new
		 * Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * final String response; try {
		 * response=CustomHttpClient.executeHttpPost
		 * ("TouchListen",postparameter); runOnUiThread(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * // TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * } catch (Exception e) { // TODO: handle exception
		 * e.printStackTrace(); }
		 * 
		 * 
		 * } }).start(); // TODO Auto-generated method stub return false; } });
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.screen_capture, menu);
		return true;
	}

	public void connectSocket() {

		try {
			soc = new Socket(UrlConnections.serverIP, UrlConnections.portNo);
			Log.i("conn", "SUCCESS");
			in = soc.getInputStream();
			Log.e("", "In Scoket creation");
			din = new BufferedReader(new InputStreamReader(in));
			out = soc.getOutputStream();
			ps = new PrintStream(out);

			System.out.println("Connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] receiveBytes() throws IOException {

		System.out.println("Recievebytes");
		try {

			inputstream = soc.getInputStream();
			System.out.println("Test3");
		} catch (IOException e) {
			// TODO Auto-generated catch block

			throw e;
		}

		DataInputStream dis = new DataInputStream(inputstream);
		int len = dis.readInt();
		System.out.println(len);
		byte[] data = new byte[len];
		if (len > 0) {
			dis.readFully(data);
		}
		System.out.println("reading finish");
		return data;

	}

}
