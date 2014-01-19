package com.peoplesissues.activities;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.becker666.resty.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoViewer extends Activity {
	private String path = "http://peytonhamil.com/suns.jpg";
	private ImageView imageView;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.photoview);
		Drawable image = ImageOperations(this, path, "image.jpg");
		imageView = new ImageView(this);
		imageView = (ImageView)findViewById(R.id.imageview);
		imageView.setImageDrawable(image);
	}

	private Drawable ImageOperations(Context ctx, String url, String saveFilename) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
}