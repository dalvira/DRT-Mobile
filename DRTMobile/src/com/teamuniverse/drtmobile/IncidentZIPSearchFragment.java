package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.IncidentHelper;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class IncidentZIPSearchFragment extends Fragment {
	/** The shortcut to the current activity */
	private static Activity		m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar	progress;
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean		querying;
	/** The handler that will allow the multi-threading */
	private Handler				handler;
	private DatabaseManager		db;
	
	private static EditText		zipBox;
	private static String		zip;
	public static int			num	= 1;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentZIPSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_zip_search, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		
		zipBox = (EditText) view.findViewById(R.id.zip_code);
		db = new DatabaseManager(m);
		if (db.sessionGet("back").equals("true")) {
			zipBox.setText(db.sessionGet("zip"));
			db.sessionUnset("back");
		}
		db.close();
		zipBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
					return true;
				}
				return false;
			}
		});
		
		((Button) view.findViewById(R.id.go_button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		
		((Button) view.findViewById(R.id.add_sample)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sample();
			}
		});
		
		((Button) view.findViewById(R.id.time_out)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SetterUpper.timedOut(m);
			}
		});
		
		return view;
	}
	
	protected void search() {
		zip = zipBox.getText().toString();
		
		if (!zip.equals("") && zip.length() == 5 && zip.matches("[0-9]{5}")) {
			DatabaseManager db = new DatabaseManager(m);
			db.sessionSet("zip", zip);
			db.close();
			
			SectionListActivity.m.putSection(SectionAdder.INCIDENT_ZIP_RESULTS);
		} else {
			new AlertDialog.Builder(m).setMessage(R.string.zip_invalid).setTitle(R.string.zip_invalid_title).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			}).create().show();
		}
	}
	
	String	error		= "";
	String	addResult	= "";
	
	protected void sample() {
		zip = zipBox.getText().toString();
		if (!zip.matches("[0-9]{5}") || (!zip.equals("27685") && !zip.equals("74685") && !zip.equals("88534") && !zip.equals("27689") && !zip.equals("63784") && !zip.equals("78549"))) {
			// 1. Instantiate an AlertDialog.Builder with its
			// constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(m);
			// 2. Chain together various methods to set the dialog
			// characteristics
			builder.setMessage("ZIP must be one of: 27685, 74685, 88534, 27689, 63784, or 78549 to be successfully added!").setTitle("Not an eligible ZIP!");
			// 3. Add an okay
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			// 4. Get the AlertDialog from create() and show it
			builder.create().show();
		} else {
			final int theZip = Integer.parseInt(zip);
			if (!querying) {
				querying = true;
				progress.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					public void run() {
						db = new DatabaseManager(m);
						String token = db.sessionGet("token");
						db.close();
						
						Incident incident = IncidentHelper.init(m, theZip);
						incident.setEventName("Sample!");
						incident.setIncidentNotes("Sample number " + num);
						
						Webservice ws = new Webservice(m);
						try {
							ws.addIncident(token, incident);
						} catch (TokenInvalidException e) {
							error = e.getMessage();
							e.printStackTrace();
						} catch (Exception e) {
							error = e.getMessage();
							e.printStackTrace();
						}
						handler.postDelayed(new Runnable() {
							public void run() {
								// Hide the progress bar
								querying = false;
								progress.setVisibility(View.GONE);
								if (error.equals("")) Toast.makeText(m, "Made sample incident in " + theZip, Toast.LENGTH_SHORT).show();
								else {
									SetterUpper.timedOut(m);
									sample();
								}
							}
						}, 0);
					}
				}).start();
			}
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}