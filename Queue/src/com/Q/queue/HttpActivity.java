package com.Q.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

    @SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#840000")));
    	
		setContentView(R.layout.activity_http);
		lView = (ListView) findViewById(R.id.listView1);
		ArrayList<String> dunderlist = new ArrayList<String>();
		dunderlist.add("YOU AND ME CAN RULE THIS CITY SPIDER MAN");
		dunderlist.add("OR WE CAN JUST FIGHT TO THE DEATH");
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this,
				R.layout.mylist,
				dunderlist);
		
		lView.setAdapter(arrayAdapter);
		
		Log.d(DEBUG_TAG, "Hi there!");
		
		clickHandler(textView);
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
        int len = 500;
            
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
