package com.Q.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HttpActivity extends Activity {
	private static final String DEBUG_TAG = "HTTPGetActivity";
	private TextView textView;
	private ListView lView;
	
	String foo;
	
    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http);
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#840000")));
		clickHandler(textView);
		JSONObject bar;
		JSONArray jArray = null;
		
		//Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		//	    Uri.parse("http://maps.google.com/maps?saddr=53.545811,-113.498908&daddr=10200 102 Ave. NW Second Cup"));

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=10200 102 Ave. NW Second Cup"));
		startActivity(intent);
		
		startActivity(intent);
    	/*
		try {
			bar = new JSONObject(foo);
			jArray = bar.getJSONArray("devices");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		lView = (ListView) findViewById(R.id.listView1);
		ArrayList<String> dunderlist = new ArrayList<String>();
		
        String[] dunderstrings = new String[jArray.length()];
        for (int i = 0; i < jArray.length(); i++)
        {
        	try {
        		JSONObject iHateThis = jArray.getJSONObject(i);
        		//JSONObject iHateThisalso = iHateThis.getJSONObject("loc");
        		//String iStillHateThis = iHateThisalso.getString("name");
				//dunderstrings[i] = jArray.getJSONObject(i).getJSONObject("loc").getString("name");
				//dunderstrings[i] = iStillHateThis;
				//dunderlist.add(dunderstrings[i]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this,
				R.layout.mylist,
				dunderlist);
		
		lView.setAdapter(arrayAdapter);
		
		//Log.d(DEBUG_TAG, "Hi there!"); */
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.http, menu);
		return true;
	}

	public void clickHandler(View view){
		String urlString = "http://192.168.1.190/pull.php?&type=coffee";
		ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connMngr.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnected()) {
			new DownloadWebpageTask().execute(urlString);
		} else {
			Log.d(DEBUG_TAG, "Crashed on click handler (1).");
			// No connection!
		}
	}
	
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
              
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
            	Log.d(DEBUG_TAG, "Crashed on page downloader (2).");
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
           Log.d(DEBUG_TAG, result);
        	//textView.setText("hey man what");
       }
    }
    
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 1024;
            
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            foo = contentAsString;
            Log.d(DEBUG_TAG, contentAsString);
            return contentAsString;
            
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
        } finally {
            if (is != null) {
                is.close();
            } 
        }
    }
    
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");        
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
