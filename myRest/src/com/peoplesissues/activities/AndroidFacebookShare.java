package com.peoplesissues.activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.becker666.resty.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class AndroidFacebookShare extends Activity {

	// Your Facebook APP ID
	private static String APP_ID = "236653713164863"; // Replace with your App ID

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

	// Buttons
	Button btnFbLogin;
	Button btnFbGetProfile;
	Button btnPostToWall;
	Button btnShowAccessTokens;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		
		btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
		
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		/**
		 * Posting to Facebook Wall
		 * */
		btnPostToWall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				postToWall();
			}
		});

		
	}

	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		facebook.dialog(this, "feed", new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}

	
	
}