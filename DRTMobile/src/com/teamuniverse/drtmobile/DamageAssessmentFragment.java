package com.teamuniverse.drtmobile;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.LayoutSetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class DamageAssessmentFragment extends Fragment {
	private static ProgressBar	getProgress;
	// private static ProgressBar addProgress;
	private static boolean		querying;
	private Handler				handler;
	private Activity			m;
	
	private EditText[]			getEditTexts;
	// private EditText[] addEditTexts;
	private Button				getButton;
	
	// private Button addButton;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public DamageAssessmentFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_damage_assessment, container, false);
		LayoutSetterUpper.setup(m, view);
		getEditTexts = new EditText[] { (EditText) view.findViewById(R.id.damage_get_zip), (EditText) view.findViewById(R.id.damage_get_street_address), (EditText) view.findViewById(R.id.damage_get_city_name), (EditText) view.findViewById(R.id.damage_get_state_name), (EditText) view.findViewById(R.id.damage_get_report_date) };
		// addEditTexts = new EditText[] { (EditText)
		// view.findViewById(R.id.damage_add_zip), (EditText)
		// view.findViewById(R.id.damage_get_street_address), (EditText)
		// view.findViewById(R.id.damage_get_city_name), (EditText)
		// view.findViewById(R.id.damage_get_state_name), (EditText)
		// view.findViewById(R.id.damage_get_report_date) };
		
		getButton = (Button) view.findViewById(R.id.damage_assessment_get);
		getButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				get();
			}
		});
		
		int[] whichGets = new int[] { 0, 3, 4 };
		for (int indexGets = 0; indexGets < whichGets.length; indexGets++) {
			getEditTexts[whichGets[indexGets]].setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
						v.performClick();
						getButton.performClick();
						return true;
					}
					return false;
				}
			});
		}
		
		TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
		tabHost.setup();
		// Get tab!
		TabSpec tspec = tabHost.newTabSpec("Tab1");
		tspec.setContent(R.id.damage_get);
		tspec.setIndicator("Get");
		tabHost.addTab(tspec);
		// Add tab!
		tspec = tabHost.newTabSpec("Tab2");
		tspec.setContent(R.id.damage_add);
		tspec.setIndicator("Add");
		tabHost.addTab(tspec);
		
		// DatabaseManager db = new DatabaseManager(m);
		// ((EditText)
		// view.findViewById(R.id.damage_add_attuid)).setText(db.sessionGet("attuid"));
		// db.close();
		
		getProgress = (ProgressBar) view.findViewById(R.id.damage_assessment_get_progress);
		// addProgress = null;
		querying = false;
		handler = new Handler();
		
		// Incident incident = new Incident();
		// incident.setAssessNotes(null);
		// incident.setBuildingAddress(null);
		// incident.setBuildingName(null);
		// incident.setBuildingStatus(null);
		// incident.setBuildingType(null);
		// incident.setCompltnDate(null);
		// incident.setComPowerIndicator(null);
		// incident.setContactPhone(null);
		// incident.setCreLead(null);
		// incident.setDamageIndicator(null);
		// incident.setElecIssueClsdIndicator(null);
		// incident.setElecIssueIndicator(null);
		// incident.setEnvIssueClsdIndicator(null);
		// incident.setEnvIssueIndicator(null);
		// incident.setEstCapCost(0);
		// incident.setEstExpenseCost(0);
		// incident.setEventName(null);
		// incident.setFenceGateIssueClsdIndicator(null);
		// incident.setFenceGateIssueIndicator(null);
		// incident.setGenIssueClsdIndicator(null);
		// incident.setGenIssueIndicator(null);
		// incident.setGeoLoc(0);
		// incident.setGroundsIssueClsdIndicator(null);
		// incident.setGroundsIssueIndicator(null);
		// incident.setIncidentCompltnDate(null);
		// incident.setIncidentNotes(null);
		// incident.setIncidentStatus(null);
		// incident.setIncidentYear(0);
		// incident.setInitialRptDate(null);
		// incident.setMechIssueClsdIndicator(null);
		// incident.setMechIssueIndicator(null);
		// incident.setMobCOIndicator(null);
		// incident.setOnGeneratorIndicator(null);
		// incident.setOtherIssueClsdIndicator(null);
		// incident.setOtherIssueIndicator(null);
		// incident.setPlumbIssueClsdIndicator(null);
		// incident.setPlumbIssueIndicator(null);
		// incident.setPMAttuid(null);
		// incident.setRecNumber(0);
		// incident.setRecNumber(0);
		// incident.setReqATTUID(null);
		// incident.setRoofsIssueClsdIndicator(null);
		// incident.setRoofsIssueIndicator(null);
		// incident.setSafetyIssueClsdIndicator(null);
		// incident.setSafetyIssueIndicator(null);
		// incident.setState(null);
		// incident.setStatusNotes(null);
		// incident.setStructIssueClsdIndicator(null);
		// incident.setStructIssueIndicator(null);
		// incident.setUnOccupiableIndicator(null);
		// incident.setWaterIssueClsdIndicator(null);
		// incident.setWaterIssueIndicator(null);
		// incident.setWorkReqNumber(null);
		
		return view;
	}
	
	private void get() {
		if (!querying) {
			// Hide virtual keyboard
			InputMethodManager imm = (InputMethodManager) m.getSystemService(Context.INPUT_METHOD_SERVICE);
			for (int i = 0; i < getEditTexts.length; i++)
				imm.hideSoftInputFromWindow(getEditTexts[i].getWindowToken(), 0);
			querying = true;
			getProgress.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				public void run() {
					try {
						// TODO set up search here!!!
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							try {
								getProgress.setVisibility(View.GONE);
								querying = false;
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
