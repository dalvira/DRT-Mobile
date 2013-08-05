package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.teamuniverse.drtmobile.AddIncidentFragment;
import com.teamuniverse.drtmobile.AdminConditionCannedReportFragment;
import com.teamuniverse.drtmobile.CREBuildingClosureDelayedOpenCannedReportFragment;
import com.teamuniverse.drtmobile.IncidentRecNumResultsFragment;
import com.teamuniverse.drtmobile.IncidentZIPResultsFragment;
import com.teamuniverse.drtmobile.LogonActivity;
import com.teamuniverse.drtmobile.R;

/**
 * This class contains methods that help with various parts of the app. The
 * methods allow the developer to meet particular needs multiple times without
 * much repeated code
 * 
 * @author ef183v
 */
public class SetterUpper {
	
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
	
	public static void setSelected(Activity m, View v) {
		v.setBackgroundColor(m.getResources().getColor(R.color.selected));
	}
	
	public static void setUnSelected(Activity m, View v, boolean isTransparent) {
		if (isTransparent) {
			v.setBackgroundColor(m.getResources().getColor(R.color.fake_transparent));
		} else v.setBackgroundColor(m.getResources().getColor(R.color.unselected_gray));
	}
	
	public static void fillIn(Activity m, LinearLayout container, ArrayList<Incident> results, int[] order) {
		if (results.size() == 0) {
			TextView temp = new TextView(m);
			temp.setPadding(0, 4, 0, 4);
			temp.setText(R.string.no_results);
			temp.setGravity(Gravity.CENTER_HORIZONTAL);
			container.addView(temp);
		} else {
			TextView temp;
			LinearLayout eachRecord, eachField;
			for (int i = 0; i < results.size(); i++) {
				if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
				eachRecord = new LinearLayout(m);
				if (results.size() > 1) eachRecord.setBackground(m.getResources().getDrawable(R.drawable.bordered_layout));
				eachRecord.setPadding(3, 6, 3, 6);
				eachRecord.setTag(R.string.record_number, results.get(i).getRecNumber() + "");
				eachRecord.setOrientation(LinearLayout.VERTICAL);
				eachRecord.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
				IncidentInfo[] fields = IncidentHelper.getInfos(results.get(i), order);
				for (int k = 0; k < fields.length; k++) {
					eachField = new LinearLayout(m);
					if (k % 2 == 1) eachField.setBackgroundColor(m.getResources().getColor(R.color.unselected_gray));
					else eachField.setBackgroundColor(m.getResources().getColor(R.color.fake_transparent));
					eachField.setOrientation(LinearLayout.HORIZONTAL);
					eachField.setPadding(3, 3, 3, 3);
					
					temp = new TextView(m);
					temp.setGravity(Gravity.CENTER);
					temp.setMaxLines(1);
					temp.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
					temp.setGravity(Gravity.LEFT);
					temp.setText(fields[k].getDescriptor());
					eachField.addView(temp);
					
					temp = new TextView(m);
					if (fields[k].getId() == IncidentInfo.STATE) {
						String currentState = fields[k].getValue() + "";
						for (int l = 0; l < IncidentInfo.STATE_POSTALS.length; l++) {
							if (currentState.equals(IncidentInfo.STATE_POSTALS[l])) {
								temp.setText(IncidentInfo.STATE_NAMES[l]);
								break;
							}
						}
					} else temp.setText(fields[k].getValue() + "");
					temp.setMaxEms(10);
					eachField.addView(temp);
					
					eachRecord.addView(eachField);
				}
				container.addView(eachRecord);
			}
		}
	}
	
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean	timedOutQuerying;
	/** The String[] that will hold the results of the call to Webservice */
	private static String[]	loginResults;
	/** The flag that tells the OnDismissListener if success was reached */
	private static boolean	loggedBackIn;
	
	/**
	 * Call this method in the InvalidTokenException catch clause of the
	 * try-catches surrounding all instances of Webservice access. This will
	 * display a dialog in the case of token time out that will prompt the user
	 * to sign in again.
	 * 
	 * @param activity
	 *            The activity from which the method is called.
	 */
	public static void timedOut(final Activity m, final int whichSection) {
		loggedBackIn = false;
		// 1. Instantiate an AlertDialog.Builder with its constructor
		// 2. Chain together various methods to set the dialog characteristics
		LayoutInflater inflater = m.getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_timed_out, null);
		
		ProgressBar progress = (ProgressBar) view.findViewById(R.id.progress);
		EditText attuidEditText = (EditText) view.findViewById(R.id.attuid);
		EditText passEditText = (EditText) view.findViewById(R.id.password);
		
		DatabaseManager db = new DatabaseManager(m);
		attuidEditText.setText(db.sessionGet("attuid"));
		// passEditText.requestFocus();
		db.close();
		
		attuidEditText.setEnabled(false);
		
		final AlertDialog dialog = new AlertDialog.Builder(m).setView(view).setTitle(R.string.timed_out_title).setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
			}
		}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(m.getApplicationContext(), LogonActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				m.startActivity(intent);
			}
		}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface arg0) {
				if (!loggedBackIn) (dialog.getButton(DialogInterface.BUTTON_NEGATIVE)).performClick();
			}
		});
		dialog.show();
		
		/**
		 * This sets the editor listener for passEditText, which clicks the go
		 * button when enter is pressed
		 */
		passEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
					return true;
				} else return false;
			}
		});
		
		(dialog.getButton(DialogInterface.BUTTON_POSITIVE)).setOnClickListener(new SetterUpper().new TimedOutDialogListener(dialog, m, progress, attuidEditText, passEditText, (Button) dialog.getButton(DialogInterface.BUTTON_NEGATIVE), whichSection));
	}
	
	class TimedOutDialogListener implements View.OnClickListener {
		private final Dialog		dialog;
		private final Activity		m;
		private final ProgressBar	progress;
		private final EditText		attuidEditText;
		private final EditText		passEditText;
		private final Handler		handler;
		private final Button		negButton;
		private final int			whichSection;
		
		public TimedOutDialogListener(Dialog dialog, Activity m, ProgressBar progress, EditText attuidEditText, EditText passEditText, Button negButton, int whichSection) {
			this.dialog = dialog;
			this.m = m;
			this.progress = progress;
			this.attuidEditText = attuidEditText;
			this.passEditText = passEditText;
			this.handler = new Handler();
			this.negButton = negButton;
			this.whichSection = whichSection;
		}
		
		@Override
		public void onClick(final View v) {
			if (!timedOutQuerying) {
				
				timedOutQuerying = true;
				progress.setVisibility(View.VISIBLE);
				passEditText.setEnabled(false);
				negButton.setEnabled(false);
				v.setEnabled(false);
				
				new Thread(new Runnable() {
					public void run() {
						String name = attuidEditText.getText().toString();
						String pass = passEditText.getText().toString();
						
						Webservice ws = new Webservice(m.getApplicationContext());
						loginResults = ws.login(name, pass);
						handler.postDelayed(new Runnable() {
							public void run() {
								timedOutQuerying = false;
								try {
									// Hide the progress bar
									progress.setVisibility(View.INVISIBLE);
									passEditText.setEnabled(true);
									negButton.setEnabled(true);
									v.setEnabled(true);
									
									if (loginResults[0] == null) {
										if (loginResults[2].equals("rcFailure")) {
											(new AlertDialog.Builder(m)).setMessage(R.string.logon_does_not_exist).setTitle(R.string.logon_does_not_exist_title).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int id) {
												}
											}).create().show();
										} else Toast.makeText(m.getApplicationContext(), loginResults[2], Toast.LENGTH_SHORT).show();
									} else {
										DatabaseManager db = new DatabaseManager(m);
										db.sessionSet("token", loginResults[0]);
										db.close();
										loggedBackIn = true;
										dialog.dismiss();
										switch (whichSection) {
											case SectionAdder.INCIDENT_REC_NUM_RESULTS:
												IncidentRecNumResultsFragment.search(IncidentRecNumResultsFragment.list);
												break;
											case SectionAdder.INCIDENT_ZIP_RESULTS:
												IncidentZIPResultsFragment.search(IncidentZIPResultsFragment.list);
												break;
											case SectionAdder.ADD_INCIDENT:
												AddIncidentFragment.addButton.performClick();
												break;
											case SectionAdder.ADMIN_CONDITION_CANNED_REPORT:
												AdminConditionCannedReportFragment.search(AdminConditionCannedReportFragment.list);
												break;
											case SectionAdder.CRE_BUILDING_CLOSURE_DELAYED_OPEN_CANNED_REPORT:
												CREBuildingClosureDelayedOpenCannedReportFragment.search(CREBuildingClosureDelayedOpenCannedReportFragment.list);
												break;
											default:
												break;
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
	}
}
