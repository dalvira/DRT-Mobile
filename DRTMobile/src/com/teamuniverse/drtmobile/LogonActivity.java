package com.teamuniverse.drtmobile;

import java.util.Calendar;

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
	// create the reference variables for the Layout views
	private static EditText		attuidEditText;
	private static EditText		passEditText;
	private static Button		goButton;
	private static CheckBox		rememberATTUID;
	private static String[]		loginResults;
	
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private Activity			me;
	
	public static String		authorization;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon);
		
		attuidEditText = (EditText) findViewById(R.id.logon_attuid);
		passEditText = (EditText) findViewById(R.id.logon_password);
		goButton = (Button) findViewById(R.id.logon_go);
		rememberATTUID = (CheckBox) findViewById(R.id.logon_remember_me);
		
		progress = (ProgressBar) findViewById(R.id.logon_progress);
		querying = false;
		handler = new Handler();
		me = this;
		
		DatabaseManager db = new DatabaseManager(this);
		db.sessionUnset();
		if (db.checkSetting("remember_attuid")) {
			rememberATTUID.setChecked(true);
			attuidEditText.setText(db.getATTUID());
			passEditText.requestFocus();
		}
		db.close();
		
		passEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					logon();
					return true;
				}
				return false;
			}
		});
		
		goButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logon();
			}
		});
		
	}
	
	/**
	 * This method accesses the web services to login with the passed username
	 * and password. It does the query in a separate thread, so that the main
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
			new Thread(new Runnable() {
				public void run() {
					String name = attuidEditText.getText().toString();
					String pass = passEditText.getText().toString();
					Webservice ws = new Webservice(getApplicationContext());
					loginResults = ws.login(name, pass);
					// Use the handler to execute a Runnable on the
					// main thread in order to have access to the
					// UI elements.
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							try {
								progress.setVisibility(View.INVISIBLE);
								querying = false;
								
								if (loginResults[0] == null) {
									
									if (loginResults[2].equals("rcFailure")) {
										// 1. Instantiate an AlertDialog.Builder
										// with its constructor
										AlertDialog.Builder builder = new AlertDialog.Builder(me);
										// 2. Chain together various setter
										// methods
										// to set the dialog
										// characteristics
										builder.setMessage(R.string.logon_does_not_exist).setTitle(R.string.logon_does_not_exist_title);
										// 3. Add a yes button and a no button
										builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int id) {
												dialog.cancel();
											}
										});
										// 4. Get the AlertDialog from create()
										AlertDialog dialog = builder.create();
										// 5. Show the dialog
										dialog.show();
									} else Toast.makeText(getApplicationContext(), loginResults[2], Toast.LENGTH_SHORT).show();
								} else {
									
									if (loginResults[0] != null) {
										
										// Store session variables
										DatabaseManager db = new DatabaseManager(me);
										
										db.sessionSet("token", loginResults[0]);
										db.sessionSet("authorization", loginResults[1]);
										
										authorization = loginResults[1];
										SectionAdder.start(authorization);
										
										Calendar cal = Calendar.getInstance();
										db.sessionSet("timestamp", cal.getTimeInMillis() + "");
										
										db.close();
										
										Intent detailIntent;
										detailIntent = new Intent(me, SectionListActivity.class);
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
	
	@Override
	protected void onResume() {
		super.onResume();
		
		DatabaseManager db = new DatabaseManager(this);
		db.sessionUnset();
		db.close();
	}
	
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