package com.peoplesissues.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.becker666.resty.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.peoplesissues.activities.*;

public class DetailFragment extends Fragment {

	public void init(){
		//pass info
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bitmap bitmap = DownloadImage(
				"http://web.iiit.ac.in/~himanshu.kela/ex/images/9.png");
		ImageView img = (ImageView) getView().findViewById(R.id.img);
		img.setImageBitmap(bitmap);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_detail,container, false);
		return v;
	}

	private InputStream OpenHttpConnection(String urlString) 
			throws IOException
			{
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString); 
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))                     
			throw new IOException("Not an HTTP connection");

		try{
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect(); 

			response = httpConn.getResponseCode();                 
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();                                 
			}                     
		}
		catch (Exception ex)
		{
			throw new IOException("Error connecting");            
		}
		return in;     
			}
	private Bitmap DownloadImage(String URL)
	{        
		Bitmap bitmap = null;
		InputStream in = null;        
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return bitmap;                
	}

}
