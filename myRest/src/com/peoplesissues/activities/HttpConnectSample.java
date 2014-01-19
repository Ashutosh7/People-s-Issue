package com.peoplesissues.activities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.becker666.resty.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HttpConnectSample extends Activity {

	private Button getImageButton;
	private Button getTextButton;
	private ProgressDialog progressDialog;	
	private Bitmap bitmap = null;
	private String text = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoview);

		//  getImageButton = (Button)findViewById(R.id.Button01);
		// getTextButton = (Button)findViewById(R.id.Button02);
		downloadImage("http://web.iiit.ac.in/~himanshu.kela/ex/images/2.png");
	}


	private void downloadImage(String urlStr) {
		progressDialog = ProgressDialog.show(this, "", "Fetching Details...");
		final String url = urlStr;

		new Thread() {
			public void run() {
				InputStream in = null;
				Message msg = Message.obtain();
				msg.what = 1;
				try {
					in = openHttpConnection(url);
					bitmap = BitmapFactory.decodeStream(in);
					Bundle b = new Bundle();
					b.putParcelable("bitmap", bitmap);
					msg.setData(b);
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				messageHandler.sendMessage(msg);	

			}
		}.start();

	}

	private InputStream openHttpConnection(String urlStr) {
		InputStream in = null;
		int resCode = -1;

		try {
			URL url = new URL(urlStr);
			URLConnection urlConn = url.openConnection();

			if (!(urlConn instanceof HttpURLConnection)) {
				throw new IOException ("URL is not an Http URL");
			}

			HttpURLConnection httpConn = (HttpURLConnection)urlConn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect(); 

			resCode = httpConn.getResponseCode();                 
			if (resCode == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();                                 
			}         
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	private Handler messageHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			ImageView img = (ImageView) findViewById(R.id.imageview);
			img.setImageBitmap((Bitmap)(msg.getData().getParcelable("bitmap")));
			progressDialog.dismiss();
		}
	};
}