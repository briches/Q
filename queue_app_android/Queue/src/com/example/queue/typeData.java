package com.example.queue;

import android.view.View;

public class typeData extends HomeSCreen {
	private String loc = "0";
	private String time = "0";
	private String count = "0";
	private String delta = "0";
	private String response = "0";
	private View dumbview = null;
	
	public void setDumbview(View dumb) {
		this.dumbview = dumb;
	}
	public View getDumbview() {
		return this.dumbview;
	}
	
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getLoc() {
		return this.loc;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime() {
		return this.time;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCount() {
		return this.count;
	}
	public void setDelta(String delta) {
		this.delta = delta;
	}
	public String getDelta() {
		return this.delta;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return this.response;
	}
	
}
