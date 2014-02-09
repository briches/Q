package com.example.queue;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeSCreen extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    typeData coffeedata;
    
    public boolean attemptHttpRequest(String lat, String lon, String type) {
    		try {
	    		HttpClient Client = new DefaultHttpClient();
	    		String URL = "192.168.1.190/pull.php?lat="+lat+"&lon="+lon+"&type="+type;
				HttpGet httpget = new HttpGet(URL);
				HttpResponse resp = Client.execute(httpget);
				HttpEntity httpEntity = resp.getEntity();
				coffeedata.setResponse(EntityUtils.toString(httpEntity));
			}
			catch(Exception e) {
				return false;
			}
    	return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#840000")));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
/* This code does not work.
    public void onConnected(Bundle dataBundle) {
    	Toast.makeText(this,  "Connected", Toast.LENGTH_SHORT).show();
    }
    public void onDisconnected() {
    	Toast.makeText(this,  "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }
    public void onConnectionFailed(ConnectionResult connectionresult) {
    	if (connectionResult.hasResolution()) {
    		connectionResult.startResolutionForResult(
    				this,
    				CONNECTION_FAILURE_RESOLUTION_REQUEST);
    	}
    } */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }
    
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.action_search:
	    		attemptHttpRequest("1", "1", "coffee");
	    		return true;
	    	case R.id.action_refresh:
	    		return true;
	    	case R.id.action_get:
	    			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
	    			    Uri.parse("http://maps.google.com/maps?saddr="+"10359 104 St NW\"&daddr=\"10200 102 Ave NW\""));
	    			startActivity(intent);
	    		return true;
	    	case R.id.action_settings:
	    		return true;
    	}
		return super.onOptionsItemSelected(item);
    	
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            
        	Fragment fragment = null;
        	Bundle args = new Bundle();
        	switch (position) {
        		case 0:
	        		fragment = new coffeeSectionFragment();
	        		//args.putInt(coffeeSectionFragment.ARG_SECTION_NUMBER, position+3);
	        		fragment.setArguments(args);
	        		return fragment;
        		case 1:
            		fragment = new foodSectionFragment();
            		//args.putInt(foodSectionFragment.ARG_SECTION_NUMBER, position+2);
            		fragment.setArguments(args);
            		return fragment;
        		case 2:
            		fragment = new grocerySectionFragment();
            		//args.putInt(grocerySectionFragment.ARG_SECTION_NUMBER, position+1);
            		fragment.setArguments(args);
            		return fragment;
        	}
        	return(fragment);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class coffeeSectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public coffeeSectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_coffee, container, false);
        //    coffeedata.setDumbview(rootView);
            TextView txt = (TextView) rootView.findViewById(R.id.textView1);
         //   TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
         //   dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            txt.setText(R.string.Datums);
            return rootView;
        }
    }
    public static class foodSectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public foodSectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_food, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
    public static class grocerySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public grocerySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_grocery, container, false);
            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

}
