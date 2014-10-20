package com.networks999.android.app.vspip.main;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.networks999.android.app.vspip.util.CustomHttpClient;

public class PushFile extends Activity {
TextView txtFile;
private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_file);
		txtFile=(TextView)this.findViewById(R.id.txtFIleName);
	}
public void onSelectClicked(View v)
{
Intent ringIntent=new Intent();
ringIntent.setType("file/*");
ringIntent.setAction(Intent.ACTION_GET_CONTENT);
ringIntent.addCategory(Intent.CATEGORY_OPENABLE);
startActivityForResult(Intent.createChooser(ringIntent, "Select File"),1);
	
}
public void onUploadClick(View v)
{
	String filepath=txtFile.getText().toString().trim();
	File file=new File(filepath);
	ContentBody upFIle=new FileBody(file);
	final HashMap<String, ContentBody> postparameters=new HashMap<String, ContentBody>();
	postparameters.put("uploadFIle", upFIle);
	try {
		postparameters.put("fileName", new StringBody("file"));
	} catch (UnsupportedEncodingException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	dialog=ProgressDialog.show(PushFile.this, "FIle Upload", "Uploading...Please Wait",true);
new Thread(new Runnable() {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			CustomHttpClient.fileUpload("FileReceiveServlet", postparameters);
		} catch (Exception e) {
			// TODO: handle exception
			dialog.cancel();
			e.printStackTrace();
		}
		dialog.cancel();
		
	}
}).start();

}


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push_file, menu);
		return true;
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
		case 1:
			if(resultCode==RESULT_OK)
			{
				Uri fileUri=data.getData();
				if(fileUri!=null){
					try {
						Cursor cursor=getContentResolver().query(fileUri, null, null, null, null);
						cursor.moveToFirst();
						int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
						String path=cursor.getString(idx);
						txtFile.setText(path);
						break;
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		
		}
	}

}
