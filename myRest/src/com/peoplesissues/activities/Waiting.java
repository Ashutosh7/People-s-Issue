package com.peoplesissues.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.becker666.resty.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;

//import com.example.friendsfinder.R;

public class Waiting extends FragmentActivity {
	private FragmentTabHost tabHost;
	private String jsonStr;
	private StringEntity jsonEntity;
	private JSONObject check;
	private ActivateTask activateTask = null;
	private ActivateTask mAuthTask = null;
	private ProgressDialog progressDialog;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		mAuthTask = new ActivateTask();
		mAuthTask.execute((Void) null);
		progressDialog = ProgressDialog.show(this, "", "Fetching Image...");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTabContent();

	}

	private void setTabContent() {
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tabFrame);
		tabHost.addTab(tabHost.newTabSpec("tabSolved").setIndicator("Solved"),SolvedActivity.class, null);
		tabHost.addTab(tabHost.newTabSpec("tabUnsolved").setIndicator("Unsolved"),UnsolvedActivity.class, null);
	}

	private void writeToFile(String data) {
		FileOutputStream fop = null;
		File file;
		String content = data;

		try {

			file = new File("/sdcard/view.json");
			fop = new FileOutputStream(file);
			Log.d("ashutosh",content);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} 

	public void writeJSON() {
		JSONObject object = new JSONObject();
		Time timestamp = new Time();
		timestamp.setToNow();
		try {
			object.put("filter", "");
			object.put("location", "");
			object.put("tag", "");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println(object);
		jsonStr = object.toString();


		try {
			jsonEntity = new StringEntity(object.toString());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(jsonStr);

	} 

	public class ActivateTask extends AsyncTask<Void, Void, String> {
		String ourresponseStatus;
		String path = null;
		protected String doInBackground(Void... params) {

			writeJSON();
			String response = null;
			HttpPost httppost = new HttpPost("http://web.iiit.ac.in/~himanshu.kela/ex/view.php");
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			File f = new File("view.json");
			writeToFile(jsonStr);
			File json = new File("/sdcard/view.json");
			Log.d("Status","started writing1");
			multipartEntity.addPart("name", new FileBody(json));
			path = "http://web.iiit.ac.in/~himanshu.kela/ex/view.php";
			//instantiates httpclient to make request
			DefaultHttpClient httpclient = new DefaultHttpClient();
			//url with the post data
			HttpPost httpost = new HttpPost(path);
			System.out.println(jsonEntity);
			httppost.setEntity(multipartEntity);
			HttpResponse responsePOST = null;

			try {
				responsePOST = httpclient.execute(httpost);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch(HttpHostConnectException e1)
			{
				Log.d("Status","Unable to connect to the server");
				e1.printStackTrace();
				return "unable";

			} 
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpEntity resEntity = responsePOST.getEntity();
			try {
				response = EntityUtils.toString(resEntity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.d("postResponse", response);
			System.out.println(response);
			check = null;
			try {
				check = new JSONObject(response);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(check);
			/*Context context = Register.this;
			SharedPreferences sharedPref = context.getSharedPreferences(
					getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();*/

			try {
				Globals.Nunsolved = Integer.parseInt(check.getString("unsolved"));
				Globals.Nsolved = Integer.parseInt(check.getString("solved"));
				JSONObject unsol = check.getJSONObject("us");
				JSONObject sol = check.getJSONObject("ss");
				System.out.println(unsol);
				System.out.println(Globals.Nunsolved);
				for(int i=0;i<Globals.Nunsolved;i++)
				{
					System.out.println(i);
					String index = Integer.toString(i);
					JSONObject num = unsol.getJSONObject(index);
					System.out.println(num);
					Listdata ld = new Listdata(num.getString("title"), num.getString("comments"), num.getString("location"), Integer.parseInt(num.getString("upvotes")), Integer.parseInt(num.getString("downvotes")), num.getString("image_name"));
					System.out.println(ld);
					Globals.unsolved[i] = ld;
				}

				for(int i=0;i<Globals.Nsolved;i++)
				{
					JSONObject num = sol.getJSONObject(Integer.toString(i));
					Listdata ld = new Listdata(num.getString("title"), num.getString("comments"), num.getString("location"), Integer.parseInt(num.getString("upvotes")), Integer.parseInt(num.getString("downvotes")), num.getString("image_name"));
					Globals.solved[i] = ld;
				}
				for(int i=0;i<Globals.Nunsolved;i++)
				{
					System.out.println(Globals.unsolved[i]);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			if(response.contains("unsolved"))
			{

				return "Success";
			}
			else {
				return "error";
			}
		}

		@Override
		protected void onPostExecute(final String response1) {
			System.out.println("response :" + response1);
			progressDialog.dismiss();
			Intent i = new Intent(Waiting.this, MainActivity.class);
			startActivity(i);
		}
	}
}