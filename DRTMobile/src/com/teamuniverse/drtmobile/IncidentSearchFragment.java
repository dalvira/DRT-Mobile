package com.teamuniverse.drtmobile;

import java.util.Enumeration;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class IncidentSearchFragment extends Fragment {
	private static Button		search;
	private static EditText		zipBox;
	private static String		zip;
	private Activity			m;
	
	private static ProgressBar	progress;
	private static boolean		querying;
	private Handler				handler;
	private Button				addSample;
	private DatabaseManager		db;
	
	public static int			num	= 1;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentSearchFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_search, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		addSample = (Button) view.findViewById(R.id.add_sample);
		
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
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
					return true;
				}
				return false;
			}
		});
		
		search = (Button) view.findViewById(R.id.zip_button);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		
		addSample.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sample();
			}
		});
		
		return view;
	}
	
	protected void search() {
		zip = zipBox.getText().toString();
		// Hide virtual keyboard
		InputMethodManager imm = (InputMethodManager) m.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(zipBox.getWindowToken(), 0);
		imm.hideSoftInputFromWindow(zipBox.getWindowToken(), 0);
		
		if (!zip.equals("") && zip.length() == 5 && zip.matches("[0-9]{5}")) {
			DatabaseManager db = new DatabaseManager(m);
			db.sessionSet("zip", zip);
			db.close();
			
			SectionListActivity.m.putSection(SectionAdder.INCIDENT_SEARCH_RESULTS);
		} else {
			// 1. Instantiate an AlertDialog.Builder with its
			// constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(m);
			// 2. Chain together various setter methods to set the
			// dialog
			// characteristics
			builder.setMessage(R.string.zip_invalid).setTitle(R.string.zip_invalid_title);
			// 3. Add an okay
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			// 4. Get the AlertDialog from create() and show it
			builder.create().show();
		}
	}
	
	String						error		= "";
	String						addResult	= "";
	Hashtable<Integer, String>	validGLCs;
	
	protected void sample() {
		if (!querying) {
			querying = true;
			progress.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				public void run() {
					db = new DatabaseManager(m);
					String token = db.sessionGet("token");
					db.close();
					
					Incident incident = new Incident();
					incident.setGeoLoc(27685);
					incident.setEventName("Catastrophie #" + num + "!");
					
					Webservice ws = new Webservice(m);
					try {
						validGLCs = ws.getGLCInfo();
						addResult = ws.addIncident(token, incident);
					} catch (TokenInvalidException e) {
						SetterUpper.timedOut(m);
					} catch (Exception e) {
						error = e.getMessage();
						e.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							querying = false;
							progress.setVisibility(View.GONE);
							Toast.makeText(m, addResult, Toast.LENGTH_SHORT).show();
							if (error.equals("")) Toast.makeText(m, "Made incident #" + num++ + " in 27685", Toast.LENGTH_SHORT).show();
							else {
								Enumeration<Integer> nums = validGLCs.keys();
								while (nums.hasMoreElements())
									Toast.makeText(m, "" + nums.nextElement(), Toast.LENGTH_SHORT).show();
							}
						}
					}, 0);
				}
			}).start();
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