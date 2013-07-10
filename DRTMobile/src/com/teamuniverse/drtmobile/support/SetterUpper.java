package com.teamuniverse.drtmobile.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Webservice;
import com.teamuniverse.drtmobile.LogonActivity;
import com.teamuniverse.drtmobile.R;

/**
 * This class contains methods that help with various parts of the app. The
 * methods allow the developer to meet particular needs multiple times without
 * much repeated code
 * 
 * @author ef183v
 * 
 */
public class SetterUpper {
	private SetterUpper() {
	}
	
	/**
	 * This method is to be used in the fragments that need to display the
	 * ATTUID of the current user and the authorization level of the current
	 * user. It fills it the TextViews by searching for the IDs that should have
	 * been used in the XML.
	 * 
	 * @param activity
	 *            The activity filled in which the TextViews need to be.
	 * @param parent
	 *            The view that contains the TextViews.
	 */
	public static void setup(Activity activity, View parent) {
		// Set up the attuid and authorization displayer
		DatabaseManager db = new DatabaseManager(activity);
		((TextView) parent.findViewById(R.id.attuid)).setText(db.sessionGet("attuid"));
		((TextView) parent.findViewById(R.id.authorization)).setText(db.sessionGet("authorization"));
		db.close();
	}
	
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar	progress;
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean		querying;
	/** The handler that will allow the multi-threading */
	private static Handler		handler;
	/** The String[] that will hold the results of the call to Webservice */
	
	/** The EditText that contains the user's ATTUID */
	private static EditText		attuidEditText;
	/** The EditText that contains the user's password */
	private static EditText		passEditText;
	/** The remember my ATTUID CheckBox */
	private static CheckBox		rememberATTUID;
	/** The String[] that will hold the results of the call to Webservice */
	private static String[]		loginResults;
	/** The user's name, taken from attuidEditText */
	private static String		name;
	/** The user's password, taken from passEditText */
	private static String		pass;
	
	/**
	 * Call this method in the InvalidTokenException catch clause of the
	 * try-catches surrounding all instances of Webservice access. This will
	 * display a dialog in the case of token time out that will prompt the user
	 * to sign in again.
	 * 
	 * @param activity
	 *            The activity from which the method is called.
	 */
	public static void timedOut(Activity activity) {
		final Activity m = activity;
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(m);
		// 2. Chain together various methods to set the dialog characteristics
		// TODO add a login in the pop-up with a custom dialog layout
		LayoutInflater inflater = m.getLayoutInflater();
		View view = inflater.inflate(R.layout.timed_out_dialog, null);
		
		progress = (ProgressBar) view.findViewById(R.id.logon_progress);
		attuidEditText = (EditText) view.findViewById(R.id.logon_attuid);
		passEditText = (EditText) view.findViewById(R.id.logon_password);
		rememberATTUID = (CheckBox) view.findViewById(R.id.logon_remember_me);
		handler = new Handler();
		
		DatabaseManager db = new DatabaseManager(m);
		if (db.checkSetting("remember_attuid")) {
			rememberATTUID.setChecked(true);
			attuidEditText.setText(db.getFirstInfoOfType("attuid"));
			passEditText.setText("password");
			passEditText.requestFocus();
		}
		db.close();
		
		builder.setView(view);
		builder.setTitle(R.string.timed_out_title);
		// 3. Add an okay
		builder.setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(m.getApplicationContext(), LogonActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				m.startActivity(intent);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Button theButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		SetterUpper me = new SetterUpper();
		theButton.setOnClickListener(me.new CustomListener(dialog, m));
	}
	
	class CustomListener implements View.OnClickListener {
		private final Dialog	dialog;
		private Activity		m;
		
		public CustomListener(Dialog dialog, Activity m) {
			this.dialog = dialog;
			this.m = m;
		}
		
		@Override
		public void onClick(View v) {
			
			if (!querying) {
				// Hide virtual keyboard
				InputMethodManager imm = (InputMethodManager) m.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(passEditText.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(attuidEditText.getWindowToken(), 0);
				querying = true;
				progress.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					public void run() {
						name = attuidEditText.getText().toString();
						pass = passEditText.getText().toString();
						
						Webservice ws = new Webservice(m.getApplicationContext());
						loginResults = ws.login(name, pass);
						// Use the handler to execute a Runnable on the
						// m thread in order to have access to the
						// UI elements.
						handler.postDelayed(new Runnable() {
							public void run() {
								// Hide the progress bar
								try {
									progress.setVisibility(View.INVISIBLE);
									querying = false;
									
									if (loginResults[0] == null) {
										if (loginResults[2].equals("rcFailure")) {
											// 1. Instantiate an
											// AlertDialog.Builder
											// with its constructor
											AlertDialog.Builder builder = new AlertDialog.Builder(m);
											// 2. Chain together various
											// setter
											// methods
											// to set the dialog
											// characteristics
											builder.setMessage(R.string.logon_does_not_exist).setTitle(R.string.logon_does_not_exist_title);
											// 3. Add a yes button and a no
											// button
											builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int id) {
													dialog.cancel();
												}
											});
											// 4. Get the AlertDialog from
											// create()
											AlertDialog dialog = builder.create();
											// 5. Show the dialog
											dialog.show();
										} else Toast.makeText(m.getApplicationContext(), loginResults[2], Toast.LENGTH_SHORT).show();
									} else {
										dialog.dismiss();
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
	}
}
