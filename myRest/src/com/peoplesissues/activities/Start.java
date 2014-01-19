package com.peoplesissues.activities;



import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.becker666.resty.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.JsonWriter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity which displays the intial screen.
 */


public class Start extends Activity implements OnClickListener {

	private Button create;
	private Button view;

	@Override
	public void onCreate(Bundle savedInstanceState) {   
		Context context = Start.this;
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		 Editor editor = sharedPreferences.edit();
		 editor.putBoolean("loggedIn", true);
		 editor.commit();
/*		SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		if(sharedPref.getBoolean("skipLogin", false ))
		{
			Intent intent = new Intent(Start.this, Sale.class);
			startActivity(intent);
		}
		if(sharedPref.getBoolean("skipReg", false ))
		{
			Intent intent = new Intent(Start.this, Login.class);
			startActivity(intent);
		}*/

		super.onCreate(savedInstanceState);    
		setContentView(R.layout.activity_start);
		enableControls();
		addViewListeners();
		//rest of the code
	}

	private void enableControls() {
		create = (Button) findViewById(R.id.createIssue);
		view = (Button) findViewById(R.id.viewIssues);
	}


	private void addViewListeners() {

		create.setOnClickListener(this);
		view.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {

		Context context = Start.this;
		/*SharedPreferences sharedPref = context.getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();*/

		if(v == create){
			Intent intent = new Intent(Start.this, MyCameraActivity.class);
			startActivity(intent);

		}
		else if(v == view){
			Intent intent = new Intent(Start.this, Waiting.class);
			startActivity(intent);

		}

	}
}