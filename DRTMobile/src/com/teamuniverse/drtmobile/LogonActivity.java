package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;

public class LogonActivity extends Activity {
	// create the reference variables for the Layout views
	private static EditText		attuidEditText;
	private static EditText		passEditText;
	private static Button		goButton;
	private static CheckBox		rememberATTUID;
	
	public final static String	LAUNCH_TO	= "com.teamuniverse.drtmobile.LAUNCH_TO";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon);
		
		attuidEditText = (EditText) findViewById(R.id.logon_attuid);
		passEditText = (EditText) findViewById(R.id.logon_password);
		goButton = (Button) findViewById(R.id.logon_go);
		rememberATTUID = (CheckBox) findViewById(R.id.logon_remember_me);
		
		DatabaseManager db = new DatabaseManager(this);
		if (db.checkSetting("remember_attuid")) {
			rememberATTUID.setChecked(true);
			attuidEditText.setText(db.getATTUID());
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
	
	private void logon() {
		
		// Get contents of the EditTexts
		String name = attuidEditText.getText().toString();
		String pass = passEditText.getText().toString();
		String[] loginResults = login(name, pass);
		
		if (loginResults[0] == null) {
			if (loginResults[2].equals("not_found")) {
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				// 2. Chain together various setter methods to set the dialog
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
			// Hide virtual keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passEditText.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(attuidEditText.getWindowToken(), 0);
			if (loginResults[0] != null) {
				// Start next activity
				Intent detailIntent = new Intent(this, SectionListActivity.class);
				
				if (loginResults[1].equals("adm")) {
					detailIntent.putExtra(LAUNCH_TO, "1");
				} else if (loginResults[1].equals("rpt")) {
					detailIntent.putExtra(LAUNCH_TO, "");
				}
				
				startActivity(detailIntent);
			}
		}
	}
	
	private String[] login(String name, String pass) {
		if (!name.equals("ef183v")) return new String[] { null, null, "not_found" };
		if (pass.equals("123")) return new String[] { "token123", "adm", "Login was successful!" };
		else return new String[] { null, null, "Incorrect password!" };
	}
	
	@Override
	protected void onResume() {
		super.onResume();
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
	protected void onDestroy() {
		super.onDestroy();
		
		DatabaseManager db = new DatabaseManager(this);
		if (db.checkSetting("erase_form_data")) db.clearTable(DatabaseManager.SAVED_STATES_TABLE);
		db.unsetSession();
		db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logon, menu);
		return true;
	}
}