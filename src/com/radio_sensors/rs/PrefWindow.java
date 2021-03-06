// Copyright (C) 2013 Olof Hagsand and Robert Olsson
//
// This file is part of Read-Sensors.
//
// Read-Sensors is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// Read-Sensors is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Read-Sensors; see the file COPYING.


package com.radio_sensors.rs;

import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Region;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.EditText;
import android.content.res.Configuration;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;
import android.util.Log;

public class PrefWindow extends Activity {
    // Default preference values. 
    // Only place where default values should appear.
    // XXX: Also set in res/layout/main.xml.
    final public static String PREF_SERVER_IP = "Radio-Sensors.com"; 
    final public static int    PREF_SERVER_PORT = 1235;
    final public static String PREF_SID  = "fcc23d000000511d";
    final public static String PREF_TAG  = "T";
    final public static int    PREF_MAX_SAMPLES = 100;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.pref);
	setTitle("Read-Sensors: Prefs");
	setup();
    }

    protected void onDestroy(){
	    super.onDestroy();
	}

    // This is code for lower right button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.layout.prefs_menu, menu);
	return true;
    }	

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
	case R.id.load:
	    setup();
	    return true;
	case R.id.save:
	    save();
	    return true;
	case R.id.reset:
	    reset();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	setContentView(R.layout.pref);
    }

    private void reset(){
	setTextVal(R.id.server_ip, "<null>");
	setIntVal(R.id.server_port, 0);
    }

    // Read values from file and into layout
    private void setup(){
	setTextVal(R.id.server_ip, Client.client.get_pref_server_ip());
	setIntVal(R.id.server_port, Client.client.get_pref_server_port());
	setTextVal(R.id.sid, Client.client.get_pref_sid());
	setTextVal(R.id.tag, Client.client.get_pref_tag());
	setIntVal(R.id.max_samples, Client.client.get_pref_max_samples());
    }

    // Read values from layout and into file
    private void save() {
	SharedPreferences sPref = getSharedPreferences("Read-Sensors", 0);
	SharedPreferences.Editor ed = sPref.edit();
	ed.putString("server_ip", (String)getTextVal(R.id.server_ip));
	ed.putInt("server_port", getIntVal(R.id.server_port));
	ed.putString("sid", (String)getTextVal(R.id.sid));
	ed.putString("tag", (String)getTextVal(R.id.tag));
	ed.putInt("max_samples", getIntVal(R.id.max_samples));
	ed.commit();
    }

    // get and set methods for GUI
    private String getTextVal(int id){
	final EditText et = (EditText) findViewById(id);
	return et.getText().toString();
    }
    private void setTextVal(int id, String s){
	final EditText et = (EditText) findViewById(id);
	et.setText(s);
    }
    private int getIntVal(int id){
	final EditText et = (EditText) findViewById(id);
	return Integer.parseInt(et.getText().toString());
    }
    private void setIntVal(int id, int i){
	final EditText et = (EditText) findViewById(id);
	et.setText(i+"");
    }

}


