package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Webservice;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.SectionAdder;

public class LogonActivity extends Activity {
	/** The shortcut to the current activity */
	private static Activity		m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar	progress;
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean		querying;
	/** The handler that will allow the multi-threading */
	private Handler				handler;
	
	// create the reference variables for the Layout views and contents
	/** The EditText that contains the user's ATTUID */
	private static EditText		attuidEditText;
	/** The EditText that contains the user's password */
	private static EditText		passEditText;
	/** The go Button */
	private static Button		goButton;
	/** The remember my ATTUID CheckBox */
	private static CheckBox		rememberATTUID;
	/** The String[] that will hold the results of the call to Webservice */
	private static String[]		loginResults;
	/** The user's name, taken from attuidEditText */
	private static String		name;
	/** The user's password, taken from passEditText */
	private static String		pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon);
		
		// // Reset data when corruption arises
		// Webservice ws = new Webservice(this);
		// ws.resetData();
		
		attuidEditText = (EditText) findViewById(R.id.attuid);
		passEditText = (EditText) findViewById(R.id.password);
		goButton = (Button) findViewById(R.id.go_button);
		rememberATTUID = (CheckBox) findViewById(R.id.remember_me);
		
		progress = (ProgressBar) findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		m = this;
		
		DatabaseManager db = new DatabaseManager(this);
		db.sessionUnset();
		if (db.checkSetting("remember_attuid")) {
			rememberATTUID.setChecked(true);
			attuidEditText.setText(db.getFirstInfoOfType("attuid"));
			passEditText.setText("password");
			passEditText.requestFocus();
		}
		db.close();
		
		/* This sets the listener for the go button, which calls logon() */
		goButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logon();
			}
		});
		
		/*
		 * This sets the editor listener for passEditText, which clicks the go
		 * button when enter is pressed
		 */
		passEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					goButton.performClick();
					return true;
				} else return false;
			}
		});
	}
	
	/**
	 * This method accesses the web services to login with the passed username
	 * and password. It does the query in a separate thread, so that the m
	 * thread is not bogged down.
	 */
	private void logon() {
		// Get contents of the EditTexts
		if (!querying) {
			// Hide virtual keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passEditText.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(attuidEditText.getWindowToken(), 0);
			querying = true;
			progress.setVisibility(View.VISIBLE);
			attuidEditText.setEnabled(false);
			passEditText.setEnabled(false);
			goButton.setEnabled(false);
			new Thread(new Runnable() {
				public void run() {
					name = attuidEditText.getText().toString();
					pass = passEditText.getText().toString();
					
					Webservice ws = new Webservice(getApplicationContext());
					loginResults = ws.login(name, pass);
					// Use the handler to execute a Runnable on the
					// m thread in order to have access to the
					// UI elements.
					handler.postDelayed(new Runnable() {
						public void run() {
							querying = false;
							try {
								// Hide the progress bar
								progress.setVisibility(View.INVISIBLE);
								attuidEditText.setEnabled(true);
								passEditText.setEnabled(true);
								goButton.setEnabled(true);
								
								if (loginResults[0] == null) {
									
									if (loginResults[2].equals("rcFailure")) {
										(new AlertDialog.Builder(m)).setMessage(R.string.logon_does_not_exist).setTitle(R.string.logon_does_not_exist_title).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
											}
										}).create().show();
									} else Toast.makeText(getApplicationContext(), loginResults[2], Toast.LENGTH_SHORT).show();
								} else {
									
									if (loginResults[0] != null) {
										// Store session variables
										DatabaseManager db = new DatabaseManager(m);
										db.sessionSet("token", loginResults[0]);
										db.sessionSet("authorization", loginResults[1]);
										db.sessionSet("attuid", name);
										db.close();
										
										SectionAdder.start(loginResults[1]);
										
										Intent detailIntent;
										detailIntent = new Intent(m, SectionListActivity.class);
										startActivity(detailIntent);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 0);
				}
			}).start();
		}
	}
	
	/**
	 * Unset all of the session variables every time the Logon screen is shown.
	 * This ensures that the user will have to sign in again.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
		DatabaseManager db = new DatabaseManager(this);
		db.sessionUnset();
		db.close();
	}
	
	/**
	 * Put the ATTUID in the field into the MySQLite database if the remember m
	 * CheckBox is checked, else remove any saved ATTUID
	 */
	@Override
	protected void onPause() {
		super.onPause();
		
		DatabaseManager db = new DatabaseManager(this);
		db.setSetting("remember_attuid", rememberATTUID.isChecked());
		if (rememberATTUID.isChecked()) {
			// Add to database
			db.addInfo("attuid", attuidEditText.getText().toString());
		} else {
			// make sure no name is saved
			db.removeInfo("attuid", null);
		}
		// Close the database
		db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logon, menu);
		return true;
	}
}