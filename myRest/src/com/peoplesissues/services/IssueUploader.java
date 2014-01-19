package com.peoplesissues.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;
import com.peoplesissues.activities.*;

public class IssueUploader extends IntentService {

	public IssueUploader() {
		super("IssueUpload");
	}

	SharedPreferences sharedPref;
	private String jsonStr;
	PrintReceiptTask mAuthTask;
	private StringEntity se;
	private DefaultHttpClient mHttpClient;

	private void writeToFile(String data) {
		FileOutputStream fop = null;
		File file;
		String content = data;

		try {

			file = new File("/sdcard/uploadIssue.json");
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
		JSONObject termInfo = new JSONObject();
		JSONObject payload = new JSONObject();
		Time timestamp = new Time();
		timestamp.setToNow();
		/*SharedPreferences sharedPref = getApplicationContext()
				.getSharedPreferences(
						getString(R.string.preference_file_key),
						Context.MODE_PRIVATE);*/
		try {
			object.put("title", Globals.title);
			object.put("comments", Globals.comments);
			object.put("location", Globals.location);
			object.put("tag", Globals.tags);
			object.put("time", timestamp.format("%k:%M:%S"));
			/*termInfo.put("deviceId",Globals.deviceId);
			termInfo.put("timeStamp", timestamp.format("%k:%M:%S"));
			object.put("payload", payload);
			payload.put("clientId", Long.valueOf(sharedPref.getString("clientId", null)));
			payload.put("sessionToken", sharedPref.getString("sessionKey",null));
			payload.put("transId", sharedPref.getString("transId",null));
			payload.put("transType", sharedPref.getInt("transType",0));
			payload.put("email",Globals.customerEmail);
			payload.put("sms",Globals.customerMobile);*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println(object);
		jsonStr = object.toString();


		try {
			se = new StringEntity(object.toString());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		System.out.println(jsonStr);

	} 


	@Override
	protected void onHandleIntent(Intent intent) {

		System.out.println("received intent");
		//String filename = intent.getStringExtra("filename");
		//String uploadRequest = intent.getStringExtra("postRequest");
		try {
			Class.forName("android.os.AsyncTask");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String filename = "/sdcard/1.png"; 

		if(filename == null)
			return;

		//Check if the signature file is present.. if present proceed.. else return;

		//File signaturePath = new File((new StringBuilder(getFilesDir().getAbsolutePath())).append(Globals.signDir).toString());
		File signature = new File("/sdcard/1.png");
		if(!signature.exists()){
			//TODO Send error report to developers email
			Log.e("signature", "failure in generating the file");
			return;
		}

		mAuthTask = new PrintReceiptTask(IssueUploader.this);
		mAuthTask.execute((Void) null);
	}



	public class PrintReceiptTask extends AsyncTask<Void, Void, String> {

		Context context = null;
		public PrintReceiptTask(Context applicationContext) {
			context = applicationContext;
		}


		@Override
		protected String doInBackground(Void... params1) {

			Log.d("Status","started writing");
			writeJSON();
			HttpParams params = new BasicHttpParams();
			params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			mHttpClient = new DefaultHttpClient(params);
			Object respond = null;
			String responseNew = null;
			try {

				HttpPost httppost = new HttpPost("http://web.iiit.ac.in/~himanshu.kela/ex/event.php");
				MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				File f = new File("uploadSignature.json");
				writeToFile(jsonStr);

				File json = new File("/sdcard/uploadIssue.json");
				//json.renameTo(new File("/sdcard/uploadIssue.json1"));
				Log.d("Status","started writing1");
				multipartEntity.addPart("name", new FileBody(json));
				/*File path = new File(
						(new StringBuilder(getFilesDir().getAbsolutePath())).append(
								Globals.signDir).toString());*/
				//String fileName = "/data/data/com.aasaanpay/files/signatures/R_SALE_" + Globals.rrn + ".png";
				String fileName = "/sdcard/1.png";
				File image = new File(fileName);
				//image.renameTo(new File("/sdcard/1asdsd.png"));
				//image.delete();
				multipartEntity.addPart("img", new FileBody(image));
				Log.d("Status","started writing2");
				Toast.makeText(getBaseContext(), "Complaint Sent", Toast.LENGTH_LONG).show();
				httppost.setEntity(multipartEntity);
				HttpResponse responsePOST = null;

				try {
					responsePOST = mHttpClient.execute(httppost);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generatedy); catch block
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
				Log.d("Status","started writing3");
				HttpEntity resEntity = responsePOST.getEntity();
				/*try {
					responseNew = EntityUtils.toString(resEntity);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				System.out.println("POST STARTS");
				System.out.println(responseNew);
				System.out.println("POST ENDS");
				JSONObject check = null;
				try {
					check = new JSONObject(responseNew);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Context context = IssueUploader.this;
				/*SharedPreferences sharedPref = context.getSharedPreferences(
						getString(R.string.preference_file_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();*/

				try {
					if(responseNew.contains("error"))
					{
						System.out.println("ERROR");
						if(responseNew.contains("errorCode"))
							return(check.getString("errorCode"));
						else
							return("fail");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (Exception e) {
				//Log.e(Receipt.class.getName(), e.getLocalizedMessage(), e);
			}

			return "Success";

		}


		@Override
		protected void onPostExecute(final String status) {
			mAuthTask = null;
				Toast.makeText(getBaseContext(), "Complaint Registered", Toast.LENGTH_LONG).show();

		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
		}
	}

}